package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;



public class CommandSwerveDrivetrain extends SubsystemBase{
   public  CommandSwerveDrivetrain() {
            RobotConfig config = null;
            try{
              config = RobotConfig.fromGUISettings();
            } catch (Exception e) {
                // Handle exception as needed
                e.printStackTrace();
            }
            try {
                
                AutoBuilder.configure(
                () -> new Pose2d(), // Robot pose supplier
                this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
                this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                (speeds, feedforwards) -> driveRobotRelative(speeds),
                new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
                        new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
                        new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
                ),
                config, // The robot configuration
                () -> {
                // Boolean supplier that controls when the path will be mirrored for the red alliance
                // This will flip the path being followed to the red side of the field.
                // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                var alliance = DriverStation.getAlliance();
                if (alliance.isPresent()) {
                    return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
                },
                this // Reference to this subsystem to set requirements
            );
            } catch (Exception ex) {
                DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", ex.getStackTrace());
            }
        }

        public void resetPose(Pose2d pose) {
            // Reset odometry to the specified pose. Replace this implementation with your drivetrain odometry reset.
            // Example (if you have an odometry object): m_odometry.resetPosition(pose, pose.getRotation());
        }

        /**
         * Supply current robot-relative chassis speeds to PathPlanner's AutoBuilder.
         * Replace the body with your drivetrain's actual velocity sensing (encoders/gyro/odometry).
         */
        public ChassisSpeeds getRobotRelativeSpeeds() {
            // TODO: return actual robot-relative velocities; using zeros for placeholder to compile
            return new ChassisSpeeds(0.0, 0.0, 0.0);
        }

        /**
         * Apply robot-relative chassis speeds to the drivetrain.
         * Replace the body with your drivetrain's actual motor/module calls.
         */
        public void driveRobotRelative(ChassisSpeeds speeds) {
            // TODO: implement actual drivetrain control using speeds (vx, vy, omega).
            // Placeholder: publish commanded speeds to SmartDashboard for debugging.
            SmartDashboard.putNumber("Drive/Vx", speeds.vxMetersPerSecond);
            SmartDashboard.putNumber("Drive/Vy", speeds.vyMetersPerSecond);
            SmartDashboard.putNumber("Drive/Omega", speeds.omegaRadiansPerSecond);

            // Example: convert ChassisSpeeds to module states and send to swerve modules here.
        }

      
}    
