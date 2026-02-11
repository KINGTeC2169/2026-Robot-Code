package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.config.PIDConstants;
// import com.pathplanner.lib.config.RobotConfig;
// import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.wpilibj.DriverStation;

public class CommandSwerveDrivetrain {
    // private void configureAutoBuilder() {
    //     try{
    //       //  DriveConstants.config = RobotConfig.fromGUISettings();
    //     } catch (Exception e) {
    //         // Handle exception as needed
    //         e.printStackTrace();
    //     }
    //     try {
    //         var config = RobotConfig.fromGUISettings();
    //         AutoBuilder.configure(
    //             this::getPose, // Robot pose supplier
    //         this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
    //         this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
    //         (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
    //         new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
    //                 new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
    //                 new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
    //         ),
    //            config,
    //            //Assume the path needs to be flipped for Red vs Blue, this is normally the case
    //            () -> DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red,
    //            this // Subsystem for requirements
    //       );
    //     } catch (Exception ex) {
    //         DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", ex.getStackTrace());
    //     }
    // }
}