package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.StopIntake;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.PhotonVision;

public class RobotContainer {
  public final Intake intake = new Intake();
  public final LED led = new LED();
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
  private final PhotonVision vision = new PhotonVision(drivetrain);


  public SendableChooser<Command> autoChooser;  
    private double MaxSpeed = 1.2 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
          .withDeadband(MaxSpeed * 0.07).withRotationalDeadband(MaxAngularRate * 0.07) // Add a 7% deadband
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
          .withDeadband(MaxSpeed * 0.07).withRotationalDeadband(MaxAngularRate * 0.07) // Add a 7% deadband
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

  final Telemetry logger = new Telemetry(MaxSpeed);

  public final CommandXboxController operatorControl = new CommandXboxController(Constants.Ports.controller);

  public final Joystick leftStick = new Joystick(Constants.Ports.leftStick);
  public final JoystickButton topLeftButton = new JoystickButton(leftStick, 1);
  public final JoystickButton bottomLeftButton = new JoystickButton(leftStick, 2);

  
  private final Joystick rightStick = new Joystick(Constants.Ports.rightStick);
  private final JoystickButton topRightButton = new JoystickButton(rightStick, 1);
  public final JoystickButton bottomRightButton = new JoystickButton(rightStick, 2);

    public RobotContainer() {

        //finish these...
        NamedCommands.registerCommand("Intake", new IntakeBall(intake, IntakeConstants.intakeVolts, led));
        NamedCommands.registerCommand("Outtake", new IntakeBall(intake, IntakeConstants.outtakeVolts, led));
        NamedCommands.registerCommand("StopIntake", new StopIntake(intake, led));

        led.initialize();

        autoChooser = AutoBuilder.buildAutoChooser();

        if (Robot.isReal()) {
          drivetrain.setDefaultCommand(
              // Drivetrain will execute this command periodically
              drivetrain.applyRequest(() ->
              drive.withVelocityX(-leftStick.getY() * MaxSpeed) // Drive forwaPPrd with negative Y (forward)
                      .withVelocityY(-leftStick.getX()* MaxSpeed) // Drive left with negative X (left)
                      .withRotationalRate(rightStick.getTwist()* MaxAngularRate * 2) // Drive counterclockwise with negative X (left)
                      )
          );
        } else {
            drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
              drivetrain.applyRequest(() ->
              drive.withVelocityX(-leftStick.getY() * MaxSpeed) // Drive forwaPPrd with negative Y (forward)
                      .withVelocityY(-leftStick.getX()* MaxSpeed) // Drive left with negative X (left)
                      .withRotationalRate(-rightStick.getX()* MaxAngularRate * 2) // Drive counterclockwise with negative X (left)
                      )
            );
        }

        // HoodTracking hoodTracking = new HoodTracking(shooter, "limelight");
        // CommandScheduler.getInstance().schedule(hoodTracking);
        configureBindings();
    }

    private void configureBindings() {

        // OPERATOR CONTROLS

        // Stops the intake when B is pressed
        operatorControl.b().debounce(.01).onTrue(new StopIntake(intake, led));
        //operatorControl.x().debounce(.01).onTrue(new StopIntake(intake, 0.05 * 12, led));

        // Intakes the ball when left bumper is pressed at a default 50% voltage
        operatorControl.leftBumper().debounce(.01).onTrue(new IntakeBall(intake, IntakeConstants.intakeVolts, led)); 

        // Outtakes the ball when right bumper is pressed at a default -50% voltage
        operatorControl.rightBumper().debounce(.01).onTrue(new IntakeBall(intake, IntakeConstants.outtakeVolts, led)); 
       
        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        // SignalLogger.setPath("/home/lvuser/logs/");
        // operatorControl.leftBumper().onTrue(Commands.runOnce(SignalLogger::start));
        // operatorControl.rightBumper().onTrue(Commands.runOnce(SignalLogger::stop));

      
        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        /**operatorControl.back().and(operatorControl.y()).whileTrue(drivetrain.sysIdDynamic(SysIdRoutine.Direction.kForward));
        operatorControl.back().and(operatorControl.x()).whileTrue(drivetrain.sysIdDynamic(SysIdRoutine.Direction.kReverse));
        operatorControl.start().and(operatorControl.y()).whileTrue(drivetrain.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
        operatorControl.start().and(operatorControl.x()).whileTrue(drivetrain.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
      **/

        //Reset orientation
        topRightButton.onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        //brake mode
        topLeftButton.whileTrue(drivetrain.applyRequest(() -> brake));

        //Robot centric mode
        if (Robot.isReal()) bottomRightButton.whileTrue(drivetrain.applyRequest(() ->
              forwardStraight.withVelocityX(-leftStick.getY() * MaxSpeed) // Drive forwaPPrd with negative Y (forward)
                      .withVelocityY(-leftStick.getX()* MaxSpeed) // Drive left with negative X (left)
                      .withRotationalRate(rightStick.getTwist()* MaxAngularRate * 2) // Drive counterclockwise with negative X (left)
                      )
          );
        else bottomRightButton.whileTrue(drivetrain.applyRequest(() ->
              forwardStraight.withVelocityX(-leftStick.getY() * MaxSpeed) // Drive forwaPPrd with negative Y (forward)
                      .withVelocityY(-leftStick.getX()* MaxSpeed) // Drive left with negative X (left)
                      .withRotationalRate(-rightStick.getX()* MaxAngularRate * 2) // Drive counterclockwise with negative X (left)
                      )
          );

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
    //An example command will be run in autonomous
    return autoChooser.getSelected();
    }
}