package frc.robot.subsystems;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Vision;

public class PhotonVision extends SubsystemBase{
    
    private PhotonCamera frontLeftCam;
    private PhotonCamera backRightCam;
    private PhotonCamera frontCam;

    private PhotonPoseEstimator frontLeftPoseEst;
    private PhotonPoseEstimator backRightPoseEst;

    public static final Transform3d kRobotToFrontLeftCam =
        new Transform3d(Vision.FRONT_LEFT_TRANSLATION, Vision.FRONT_LEFT_ROTATION);

    public static final Transform3d kRobotToBackRightCam =
        new Transform3d(Vision.BACK_RIGHT_TRANSLATION, Vision.BACK_RIGHT_ROTATION);

    public static final AprilTagFieldLayout kTagLayout =
        AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    public PhotonVision(){
        frontLeftCam = new PhotonCamera(""); //TODO: Replace with correct camera name
        backRightCam = new PhotonCamera(""); //TODO: Replace with correct camera name
        frontCam = new PhotonCamera(""); //TODO: Replace with correct camera name

        frontLeftPoseEst = new PhotonPoseEstimator(kTagLayout, kRobotToFrontLeftCam);
        backRightPoseEst = new PhotonPoseEstimator(kTagLayout, kRobotToBackRightCam);
    }

    /**
     * Returns an estimated robot pose from the Front Left Camera based on the pipeline result.
     * 
     * NOT to be used outside of photon vision class.
     * 
     * @return EstimatedRobotPose
     */
    private EstimatedRobotPose getFrontLeftPoseEst(){
        var results = frontLeftCam.getAllUnreadResults();
        if (!results.isEmpty()){
            var result = results.get(results.size() - 1);
            var visionEst = frontLeftPoseEst.estimateCoprocMultiTagPose(result);
            if (visionEst.isEmpty()) {
                visionEst = frontLeftPoseEst.estimateLowestAmbiguityPose(result);
            }
            return visionEst.get();
        } else {
            return null;
        }
    }

    /**
     * Returns an estimated robot pose from the Back Right Camera based on the pipeline result.
     * 
     * NOT to be used outside of photon vision class.
     * 
     * @return EstimatedRobotPose
     */
    private EstimatedRobotPose getBackRightPoseEst(){
        var results = backRightCam.getAllUnreadResults();
        if (!results.isEmpty()){
            var result = results.get(results.size() - 1);
            var visionEst = backRightPoseEst.estimateCoprocMultiTagPose(result);
            if (visionEst.isEmpty()) {
                visionEst = backRightPoseEst.estimateLowestAmbiguityPose(result);
            }
            return visionEst.get();
        } else {
            return null;
        }
    }

    /**
     * Used to send the robot pose and the timestamp as one object
     */
    public record Result(Pose3d robotPose, double timestamp) {}

    /**
     * Returns the estimated robot pose that is interpolated from the two cameras. 
     * 
     * @return [Pose3d robotPose, double timestamp]
     */
    public Result getRobotPose(){
        return new Result(getFrontLeftPoseEst().estimatedPose.interpolate(getBackRightPoseEst().estimatedPose, 0.5), getFrontLeftPoseEst().timestampSeconds);
    }

    public Pose3d getFuelPose(){

        var results = frontCam.getAllUnreadResults();

        if (!results.isEmpty()){
            var result = results.get(results.size() - 1);
            result.getTargets();
        }

        return null;
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
    }
}
