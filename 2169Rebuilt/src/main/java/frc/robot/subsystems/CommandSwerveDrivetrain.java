package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.config.PIDConstants;
// import com.pathplanner.lib.config.RobotConfig;
 import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.wpilibj.DriverStation;

public class CommandSwerveDrivetrain extends SubsystemBase{
   // public  CommandSwerveDrivetrain() {
            // RobotConfig config;
            // try{
            //   config = RobotConfig.fromGUISettings();
            // } catch (Exception e) {
            //     // Handle exception as needed
            //     e.printStackTrace();
            // }
            // try {
                
        //         AutoBuilder.configure(
        //         this::getPose, // Robot pose supplier
        //         this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
        //         this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
        //         (speeds, feedforwards) -> setControl(
        //                 m_pathApplyRobotSpeeds.withSpeeds(speeds)
        //                     .withWheelForceFeedforwardsX(feedforwards.robotRelativeForcesXNewtons())
        //                     .withWheelForceFeedforwardsY(feedforwards.robotRelativeForcesYNewtons())
        //             ),
        //         new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
        //                 new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
        //                 new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
        //         ),
        //         config, // The robot configuration
        //         () -> {
        //         // Boolean supplier that controls when the path will be mirrored for the red alliance
        //         // This will flip the path being followed to the red side of the field.
        //         // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

        //         var alliance = DriverStation.getAlliance();
        //         if (alliance.isPresent()) {
        //             return alliance.get() == DriverStation.Alliance.Red;
        //         }
        //         return false;
        //         },
        //         this // Reference to this subsystem to set requirements
        //     );
        //     } catch (Exception ex) {
        //         DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", ex.getStackTrace());
        //     }
        // }

        // private Object driveRobotRelative(ChassisSpeeds speeds) {
        //     // TODO Auto-generated method stub
        //     throw new UnsupportedOperationException("Unimplemented method 'driveRobotRelative'");
        
   // }    
}