package frc.robot.subsystems;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

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

    private PhotonPipelineResult objectResult;

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

    public record Result(Pose3d robotPose, double timestamp) {}

    public Result getRobotPose(){
        return new Result(getFrontLeftPoseEst().estimatedPose.interpolate(getBackRightPoseEst().estimatedPose, 0.5), getFrontLeftPoseEst().timestampSeconds);
    }

    public void takeFrontLeftSnapshot(boolean processed){
        if (processed) frontLeftCam.takeOutputSnapshot();
        else frontLeftCam.takeInputSnapshot();
    }

    public void takeBackRightSnapshot(boolean processed){
        if (processed) backRightCam.takeOutputSnapshot();
        else backRightCam.takeInputSnapshot();
    }

    public void takeFrontSnapshot(boolean processed){
        if (processed) frontCam.takeOutputSnapshot();
        else frontCam.takeInputSnapshot();
    }

    @Override
    public void periodic(){
    }
}
