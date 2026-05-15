// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.StopIntake;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import java.util.List;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;
  //   private final Vision vision;
  private final Intake intake;
  public final LED led;

  // Controller
  private final CommandXboxController operatorControl =
      new CommandXboxController(Constants.Ports.controller);

  private final Joystick leftStick = new Joystick(Constants.Ports.leftStick);
  private final JoystickButton topLeftButton = new JoystickButton(leftStick, 1);
  private final JoystickButton bottomLeftButton = new JoystickButton(leftStick, 2);

  private final Joystick rightStick = new Joystick(Constants.Ports.rightStick);
  private final JoystickButton topRightButton = new JoystickButton(rightStick, 1);
  private final JoystickButton bottomRightButton = new JoystickButton(rightStick, 2);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        // ModuleIOTalonFX is intended for modules with TalonFX drive, TalonFX turn, and
        // a CANcoder
        drive =
            new Drive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));

        // vision =
        //     new Vision(
        //         drive::addVisionMeasurement,
        //         // new VisionIOPhotonVision(
        //         //     "Front_Left_Camera", Constants.Vision.FRONT_LEFT_CAMERA_TO_ROBOT),
        //         new VisionIOPhotonVision(
        //             "Back_Right_Camera", Constants.Vision.BACK_RIGHT_CAMERA_TO_ROBOT));
        intake = new Intake();
        led = new LED();

        // The ModuleIOTalonFXS implementation provides an example implementation for
        // TalonFXS controller connected to a CANdi with a PWM encoder. The
        // implementations
        // of ModuleIOTalonFX, ModuleIOTalonFXS, and ModuleIOSpark (from the Spark
        // swerve
        // template) can be freely intermixed to support alternative hardware
        // arrangements.
        // Please see the AdvantageKit template documentation for more information:
        // https://docs.advantagekit.org/getting-started/template-projects/talonfx-swerve-template#custom-module-implementations
        //
        // drive =
        // new Drive(
        // new GyroIOPigeon2(),
        // new ModuleIOTalonFXS(TunerConstants.FrontLeft),
        // new ModuleIOTalonFXS(TunerConstants.FrontRight),
        // new ModuleIOTalonFXS(TunerConstants.BackLeft),
        // new ModuleIOTalonFXS(TunerConstants.BackRight));
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));
        // vision =
        //     new Vision(
        //         drive::addVisionMeasurement,
        //         // new VisionIOPhotonVisionSim(
        //         // "Front_Left_Camera",
        //         // Constants.Vision.FRONT_LEFT_CAMERA_TO_ROBOT,
        //         // drive::getPose),
        //         new VisionIOPhotonVisionSim(
        //             "Back_Right_Camera",
        //             Constants.Vision.BACK_RIGHT_CAMERA_TO_ROBOT,
        //             drive::getPose));
        intake = new Intake();
        led = new LED();
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        // vision = new Vision(drive::addVisionMeasurement, new VisionIO() {}, new VisionIO() {});
        intake = new Intake();
        led = new LED();
        break;
    }

    // Named commands for pathplanner
    NamedCommands.registerCommand(
        "Intake", new IntakeBall(intake, IntakeConstants.intakeVolts, led, operatorControl));
    NamedCommands.registerCommand(
        "Outtake", new IntakeBall(intake, IntakeConstants.outtakeVolts, led, operatorControl));
    NamedCommands.registerCommand("StopIntake", new StopIntake(intake, led));

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive
    switch (Constants.currentMode) {
      case SIM:
        drive.setDefaultCommand(
            DriveCommands.joystickDrive(
                drive,
                () -> -operatorControl.getLeftY(),
                () -> -operatorControl.getLeftX(),
                () -> operatorControl.getRightX()));
        break;

      default:
        drive.setDefaultCommand(
            DriveCommands.joystickDrive(
                drive,
                () -> -leftStick.getY(),
                () -> -leftStick.getX(),
                () -> rightStick.getTwist()));
        break;
    }

    // Lock to 0° when A button is held
    bottomRightButton.whileTrue(
        DriveCommands.joystickDriveAtAngle(
            drive, () -> -leftStick.getY(), () -> -leftStick.getX(), () -> Rotation2d.kZero));

    // Switch to X pattern when top left button is pressed
    topLeftButton.onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when top right button is pressed
    topRightButton.onTrue(
        Commands.runOnce(
                () -> drive.setPose(new Pose2d(drive.getPose().getTranslation(), Rotation2d.kZero)),
                drive)
            .ignoringDisable(true));

    // OPERATOR CONTROLS

    // Stops the intake when B is pressed
    operatorControl.b().debounce(.01).onTrue(new StopIntake(intake, led));
    // operatorControl.x().debounce(.01).onTrue(new StopIntake(intake, 0.05 * 12, led));

    // Intakes the ball when left bumper is pressed at a default 50% voltage
    operatorControl
        .leftBumper()
        .debounce(.01)
        .onTrue(new IntakeBall(intake, IntakeConstants.intakeVolts, led, operatorControl));

    // Outtakes the ball when right bumper is pressed at a default -50% voltage
    operatorControl
        .rightBumper()
        .debounce(.01)
        .onTrue(new IntakeBall(intake, IntakeConstants.outtakeVolts, led, operatorControl));
  }

  /**
   * Passes the trajectory to the field map in the Drive subsystem for visualization on the
   * dashboard.
   *
   * @param trajectory - An array of Pose2d representing the trajectory to be visualized on the
   *     field map
   */
  public void setFieldMapTrajectory(List<Pose2d> trajectory) {
    drive.setFieldMapTrajectory(trajectory);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
