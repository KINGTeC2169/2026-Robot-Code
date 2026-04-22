// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ctre.phoenix6.Utils;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.net.WebServer;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.Elastic; 

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  private final PowerDistribution pdh;
  private String autoName, newAutoName;
  private List<PathPlannerPath> pathPlannerPaths = null;

  //Swerve drive sim stuff
  private static final double kSimLoopPeriod = 0.004; // 4 ms
  private Notifier m_simNotifier = null;
  private double m_lastSimTime;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
   m_robotContainer = new RobotContainer();
   WebServer.start(5800, Filesystem.getDeployDirectory().getPath());
   pdh = new PowerDistribution();
   DataLogManager.start("/u/logs");

    //Swerve Widget
    SmartDashboard.putData("Swerve Drive", new Sendable() {
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("SwerveDrive");

        builder.addDoubleProperty("Front Left Angle", () -> m_robotContainer.drivetrain.getModule(0).getCurrentState().angle.getRadians(), null);
        builder.addDoubleProperty("Front Left Velocity", () -> m_robotContainer.drivetrain.getModule(0).getCurrentState().speedMetersPerSecond, null);

        builder.addDoubleProperty("Front Right Angle", () -> m_robotContainer.drivetrain.getModule(1).getCurrentState().angle.getRadians(), null);
        builder.addDoubleProperty("Front Right Velocity", () -> m_robotContainer.drivetrain.getModule(1).getCurrentState().speedMetersPerSecond, null);

        builder.addDoubleProperty("Back Left Angle", () -> m_robotContainer.drivetrain.getModule(2).getCurrentState().angle.getRadians(), null);
        builder.addDoubleProperty("Back Left Velocity", () -> m_robotContainer.drivetrain.getModule(2).getCurrentState().speedMetersPerSecond, null);

        builder.addDoubleProperty("Back Right Angle", () -> m_robotContainer.drivetrain.getModule(3).getCurrentState().angle.getRadians(), null);
        builder.addDoubleProperty("Back Right Velocity", () -> m_robotContainer.drivetrain.getModule(3).getCurrentState().speedMetersPerSecond, null);

        builder.addDoubleProperty("Robot Angle", () -> m_robotContainer.drivetrain.getPigeon2().getRotation2d().getRadians(), null);
      }
    });
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
     CommandScheduler.getInstance().run();

     if (DriverStation.isAutonomous()){
      // Elastic.selectTab("Autonomous");
    }
    SmartDashboard.putData(m_robotContainer.autoChooser);
    SmartDashboard.putData("pdh", pdh);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    m_robotContainer.led.still();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    // Elastic.selectTab("Teleoperated");
    m_robotContainer.logger.field.getObject("path").setPoses();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
     m_robotContainer.led.initialize();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {

    newAutoName = m_robotContainer.getAutonomousCommand() != null ? m_robotContainer.getAutonomousCommand().getName() : null;

    if (autoName == null || !autoName.equals(newAutoName)) {
      autoName = newAutoName;
      if (AutoBuilder.getAllAutoNames().contains(autoName)) {
          
          try {
            pathPlannerPaths = PathPlannerAuto.getPathGroupFromAutoFile(newAutoName);
          } catch (Exception e) {
            e.printStackTrace();
            pathPlannerPaths = null;
          }
          List<Pose2d> poses = new ArrayList<>();
          if (pathPlannerPaths != null) {
          for (PathPlannerPath path : pathPlannerPaths) {
              if (DriverStation.getAlliance().get() == Alliance.Red) poses.addAll(path.flipPath().getAllPathPoints().stream().map(point -> new Pose2d(point.position.getX(), point.position.getY(), new Rotation2d())).collect(Collectors.toList()));
              else poses.addAll(path.getAllPathPoints().stream().map(point -> new Pose2d(point.position.getX(), point.position.getY(), new Rotation2d())).collect(Collectors.toList()));
            }
                      m_robotContainer.logger.field.getObject("path").setPoses(poses);
          }
      }
    }
  }
  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    m_robotContainer.led.still();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
@Override
public void simulationInit() {
    m_lastSimTime = Utils.getCurrentTimeSeconds();

    /* Run simulation at a faster rate so PID gains behave more reasonably */
    m_simNotifier = new Notifier(() -> {
        final double currentTime = Utils.getCurrentTimeSeconds();
        double deltaTime = currentTime - m_lastSimTime;
        m_lastSimTime = currentTime;

        /* Use the measured time delta, get battery voltage from WPILib */
        m_robotContainer.drivetrain.updateSimState(deltaTime, RobotController.getBatteryVoltage());
    });
    m_simNotifier.startPeriodic(kSimLoopPeriod);
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
  
}
