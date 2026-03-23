package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Vision;

public class PhotonVision extends SubsystemBase{

    private CommandSwerveDrivetrain drivetrain;
    
    private PhotonCamera frontLeftCam;
    private PhotonCamera backRightCam;
    private PhotonCamera frontCam;

    private PhotonPoseEstimator frontLeftPoseEst;
    private PhotonPoseEstimator backRightPoseEst;

    private boolean originSet = false;

    public static final Transform3d kRobotToFrontLeftCam =
        new Transform3d(Vision.FRONT_LEFT_TRANSLATION, Vision.FRONT_LEFT_ROTATION);

    public static final Transform3d kRobotToBackRightCam =
        new Transform3d(Vision.BACK_RIGHT_TRANSLATION, Vision.BACK_RIGHT_ROTATION);

    public static final AprilTagFieldLayout kTagLayout =
        AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    public PhotonVision(CommandSwerveDrivetrain drivetrain){
        this.drivetrain = drivetrain;

        frontLeftCam = new PhotonCamera(""); //TODO: Replace with correct camera name
        backRightCam = new PhotonCamera(""); //TODO: Replace with correct camera name
        frontCam = new PhotonCamera(""); //TODO: Replace with correct camera name

        frontLeftPoseEst = new PhotonPoseEstimator(kTagLayout, kRobotToFrontLeftCam);
        backRightPoseEst = new PhotonPoseEstimator(kTagLayout, kRobotToBackRightCam);

        //Turn off driver mode for all cameras
        frontLeftCam.setDriverMode(false);
        backRightCam.setDriverMode(false);
        frontCam.setDriverMode(false);
    }

    public Matrix<N3, N1> getEstimationStdDevs(EstimatedRobotPose est, int numTags) {
    // Default trust: 0.1m for X/Y, 0.1 rad for Heading
        var baseStdDevs = VecBuilder.fill(0.1, 0.1, 0.1); 
    
        // Calculate average distance to all tags in the estimate
        double avgDist = 0;
        for (var target : est.targetsUsed) {
            avgDist += target.getBestCameraToTarget().getTranslation().getNorm();
        }
        if (numTags == 0) return VecBuilder.fill(1, 1, 1); //Large uncertainty
        avgDist /= numTags;

        // Scaling Factor: Increase std dev as distance increases
        // If > 1 tag, we trust it more; if 1 tag, we trust it less
        double distanceMultiplier = (numTags > 1) ? 0.5 : 1.0;
        double scale = (1.0 + (avgDist * avgDist)) * distanceMultiplier;

        return baseStdDevs.times(scale);
    }

    /**
     * Updates the drivetrain with an estimated robot pose from the Front Left Camera based on the pipeline result.
     * 
     * NOT to be used outside of photon vision class.
     * 
     */
    private void updateFrontLeftPoseEst(CommandSwerveDrivetrain drivetrain){
        var results = frontLeftCam.getAllUnreadResults();
        
        for (var result : results) {
            //Only process if there are actual targets in this frame
            if (!result.hasTargets()) continue;

            //Try the high-accuracy Multi-Tag strategy first
            var visionEst = frontLeftPoseEst.estimateCoprocMultiTagPose(result); 

            //In case multi tag fails
            if (visionEst.isEmpty()) visionEst = frontLeftPoseEst.estimateLowestAmbiguityPose(result);

            //If a pose was successfully calculated, send it to the drivetrain
            if (visionEst.isPresent()) {
                EstimatedRobotPose est = visionEst.get();
                if (est.targetsUsed.size() > 1 || (est.targetsUsed.size() == 1 && est.targetsUsed.get(0).getPoseAmbiguity() < 0.2))
                drivetrain.addVisionMeasurement(
                    est.estimatedPose.toPose2d(), 
                    est.timestampSeconds,
                    getEstimationStdDevs(est, est.targetsUsed.size())
                );
            }
        }
    }

    /**
     * Updates the drivetrain with an estimated robot pose from the Back Right Camera based on the pipeline result.
     * 
     * NOT to be used outside of photon vision class.
     * 
     * 
     */
    private void updateBackRightPoseEst(CommandSwerveDrivetrain drivetrain){
        var results = backRightCam.getAllUnreadResults();
        
        for (var result : results) {
            //Only process if there are actual targets in this frame
            if (!result.hasTargets()) continue;

            //Try the high-accuracy Multi-Tag strategy first
            var visionEst = backRightPoseEst.estimateCoprocMultiTagPose(result); 

            //In case multi tag fails
            if (visionEst.isEmpty()) visionEst = backRightPoseEst.estimateLowestAmbiguityPose(result);

            //If a pose was successfully calculated, send it to the drivetrain
            if (visionEst.isPresent()) {
                EstimatedRobotPose est = visionEst.get();
                if (est.targetsUsed.size() == 1 && est.targetsUsed.get(0).getPoseAmbiguity() < 0.2)
                drivetrain.addVisionMeasurement(
                    est.estimatedPose.toPose2d(), 
                    est.timestampSeconds,
                    getEstimationStdDevs(est, est.targetsUsed.size())
                );
            }
        }
    }

    /**
     * Gets the position of the center of the largest fuel cluster
     * 
     * @return the position of the center of the fuel cluser in field relative format 
     */
    public Pose2d getFieldRelativeFuelClusterPose(){

        var results = frontCam.getAllUnreadResults();

        if (!results.isEmpty()){
            var result = results.get(results.size() - 1);
            if (result.hasTargets()){
                    var clusterCenter = getMaxClusterCenter(result.getTargets());
                    if (clusterCenter != null){
                    double distance = getDistanceToCluster(clusterCenter);

                    double xTranslation = distance * Math.cos(Units.degreesToRadians(clusterCenter.getYaw()));
                    double yTranslation = distance * Math.sin(Units.degreesToRadians(clusterCenter.getYaw()));

                    Pose2d robotPose = drivetrain.getState().Pose;

                    Translation2d fuelOffset = new Translation2d(xTranslation, yTranslation).rotateBy(robotPose.getRotation());

                    return new Pose2d(robotPose.getTranslation().plus(fuelOffset), robotPose.getRotation());
                }
            }
        }
        return null;
    }

    public PhotonTrackedTarget getMaxClusterCenter(List<PhotonTrackedTarget> targets) {
        if (targets.isEmpty()) return null;

        // Sort targets by Yaw to make proximity checks easier
        targets.sort((a, b) -> Double.compare(a.getYaw(), b.getYaw()));

        List<List<PhotonTrackedTarget>> clusters = new ArrayList<>();
        List<PhotonTrackedTarget> currentCluster = new ArrayList<>();
        currentCluster.add(targets.get(0));

        for (int i = 1; i < targets.size(); i++) {
            // If the next object is within 8 degrees of the current one, it's a cluster
            if (Math.abs(targets.get(i).getYaw() - targets.get(i-1).getYaw()) < 8.0) {
                currentCluster.add(targets.get(i));
            } else {
                clusters.add(new ArrayList<>(currentCluster));
                currentCluster.clear();
                currentCluster.add(targets.get(i));
            }
        }
        clusters.add(currentCluster);

        // Find the cluster with the most objects
        return clusters.stream()
            .max(Comparator.comparingInt(List::size))
            .map(c -> c.get(c.size() / 2)) // Return the middle object of the biggest cluster
            .orElse(null);
    }

    public double getDistanceToCluster(PhotonTrackedTarget clusterCenter) {
        if (clusterCenter == null) return -1.0;

        return PhotonUtils.calculateDistanceToTargetMeters(
            Vision.FRONT_CAMERA_HEIGHT_METERS,   // Fixed height of your camera
            0,     // Height of the fuel
            Vision.FRONT_CAMERA_PITCH_RADIANS,   // Fixed angle of your camera
            Units.degreesToRadians(clusterCenter.getPitch()) // Target's vertical angle
        );
    }

    /**
     * Toggles the driver mode setting on the Front Camera 
     * 
     * @param toggle - True for on, false for off
     */
    public void setDriverMode(boolean toggle){
        frontCam.setDriverMode(toggle);
    }

    /**
     * Returns the Driver Mode status of the front camera
     * 
     * @return Driver mode?
     */
    public boolean getDriverMode(){
        return frontCam.getDriverMode();
    }

    /**
     * Takes a snapshot from the Front Left Camera
     * 
     * @param processed - True gives the output from the pipeline, false gives the input to the pipeline
     */
    public void takeFrontLeftSnapshot(boolean processed){
        if (processed) frontLeftCam.takeOutputSnapshot();
        else frontLeftCam.takeInputSnapshot();
    }

    /**
     * Takes a snapshot from the Back Right Camera
     * 
     * @param processed - True gives the output from the pipeline, false gives the input to the pipeline
     */
    public void takeBackRightSnapshot(boolean processed){
        if (processed) backRightCam.takeOutputSnapshot();
        else backRightCam.takeInputSnapshot();
    }

    /**
     * Takes a snapshot from the Front Camera
     * 
     * @param processed - True gives the output from the pipeline, false gives the input to the pipeline
     */
    public void takeFrontSnapshot(boolean processed){
        if (processed) frontCam.takeOutputSnapshot();
        else frontCam.takeInputSnapshot();
    }

    /**
     * Returns the state of the 3 cameras connected to the Rubik Pi 3. Will print an error message if otherwise
     * 
     * @return are all cameras connected?
     */
    public boolean isConnected(){
        int falseCount = 0;
        if (!frontLeftCam.isConnected()){
            System.err.println("ERROR: " + frontLeftCam.getName() + " is not connected. Is it plugged in? On both ends?");
            falseCount++;
        } 
        if (!backRightCam.isConnected()){
            System.err.println("ERROR: " + backRightCam.getName() + " is not connected. Is it plugged in? On both ends?");
            falseCount++;
        }
        if (!frontCam.isConnected()){
            System.err.println("ERROR: " + frontCam.getName() + " is not connected. Is it plugged in? On both ends?");
            falseCount++;
        }
        if (falseCount > 0){
            return false;
        }
        return true;
    }

    @Override
    public void periodic(){

        //Adjust Photon Vision origin at the beginning 
        if (!originSet) {
            var alliance = edu.wpi.first.wpilibj.DriverStation.getAlliance();
            if (alliance.isPresent()) {
                kTagLayout.setOrigin(alliance.get() == edu.wpi.first.wpilibj.DriverStation.Alliance.Red 
                    ? AprilTagFieldLayout.OriginPosition.kRedAllianceWallRightSide 
                    : AprilTagFieldLayout.OriginPosition.kBlueAllianceWallRightSide);
                originSet = true;
            }
        }

        SmartDashboard.putBoolean("Cameras Connected?", isConnected());
        updateFrontLeftPoseEst(drivetrain);
        updateBackRightPoseEst(drivetrain);
    }
}