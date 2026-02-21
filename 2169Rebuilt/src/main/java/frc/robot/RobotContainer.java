package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

  public final Shooter shooter = new Shooter();
  public final Intake intake = new Intake();
  public final Indexer indexer = new Indexer();


  public SendableChooser<Command> autoChooser;  
  private double speed = 0.5;
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController m_driveController = new CommandXboxController(0);

    public final Joystick leftStick = new Joystick(Constants.Ports.leftStick);
  public final JoystickButton topLeftButton = new JoystickButton(leftStick, 1);
  public final JoystickButton bottomLeftButton = new JoystickButton(leftStick, 2);

  
  private final Joystick rightStick = new Joystick(Constants.Ports.rightStick);
  private final JoystickButton topRightButton = new JoystickButton(rightStick, 1);
  public final JoystickButton bottomRightButton = new JoystickButton(rightStick, 2);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {

        //drivetrain = TunerConstants.createDrivetrain();
        autoChooser = AutoBuilder.buildAutoChooser();
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
            drive.withVelocityX(-leftStick.getY() * MaxSpeed * speed) // Drive forward with negative Y (forward)
                    .withVelocityY(-leftStick.getX() * MaxSpeed * speed) // Drive left with negative X (left)
                    .withRotationalRate(rightStick.getTwist() * MaxAngularRate * speed * 2) // Drive counterclockwise with negative X (left)
                    )
        );

        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
       
        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        /* 
        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
        */

        //Reset orientation
        topRightButton.onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        // Reset the field-centric heading on left bumper press.
        //joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    //public SendableChooser<Command> autoChooser;
    //public final Telemetry logger = new Telemetry(MaxSpeed); //needs the Telemtry file

    public Command getAutonomousCommand() {
    //An example command will be run in autonomous
    return autoChooser.getSelected(); //autoChooser.getSelected();
 }
}
