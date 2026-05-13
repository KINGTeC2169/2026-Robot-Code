package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.Ports;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {

  private TalonFX topIntake;
  private TalonFX bottomIntake;

  private TalonFXConfigurator topIntakeConfig;
  private TalonFXConfigurator bottomIntakeConfig;
  private TalonFXConfiguration limitConfigs =
      new TalonFXConfiguration()
          .withCurrentLimits(
              new CurrentLimitsConfigs()
                  .withStatorCurrentLimit(Amps.of(30))
                  .withStatorCurrentLimitEnable(true)
                  .withSupplyCurrentLimit(Amps.of(30))
                  .withSupplyCurrentLimitEnable(true));

  private boolean direction;
  private boolean intakeOn;

  // Constructor for the intake subsystem
  public Intake() {
    topIntake = new TalonFX(Ports.topIntake);
    bottomIntake = new TalonFX(Ports.bottomIntake);

    topIntakeConfig = topIntake.getConfigurator();
    bottomIntakeConfig = bottomIntake.getConfigurator();

    topIntakeConfig.apply(limitConfigs);
    bottomIntakeConfig.apply(limitConfigs);
  }

  // Sets proper direction and speed for intake/outtake procedure
  public void intakeDirection(double voltage, CommandXboxController operator) {
    voltage *=
        voltage != 0 ? 1 - (operator.getRightTriggerAxis() * IntakeConstants.outtakeMaxBoost) : 1;
    topIntake.setVoltage(voltage);
    // bottomIntake.setVoltage(-voltage * 0); // was 0.75
    intakeOn = true;
    if (voltage > 0) {
      direction = true; // intake
    } else {
      direction = false;
    }
  }

  // Stops the intake from spinning
  public void stopIntaking() {
    topIntake.setVoltage(0);
    bottomIntake.setVoltage(0);
    intakeOn = false;
  }

  public void stopIntaking(double topspeed) {
    topIntake.setVoltage(topspeed);
    bottomIntake.setVoltage(0);
    intakeOn = false;
  }

  @Override
  public void periodic() {
    Logger.recordOutput("Intake/direction", direction);
    Logger.recordOutput("Intake/intakeOn", intakeOn);
  }
}
