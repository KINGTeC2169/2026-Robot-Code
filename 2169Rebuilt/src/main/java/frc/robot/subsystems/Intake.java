package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class Intake {
    private TalonFX pivotMotor;
    private TalonFX spinMotor;

    private final double pivotMaxHeight = IntakeConstants.pivotMaxHeight;
    private final double pivotMinHeight = IntakeConstants.pivotMinHeight;

    public Intake(){
        pivotMotor = new TalonFX(Constants.Ports.pivotMotor);
        spinMotor = new TalonFX(Constants.Ports.spinMotor);
    }

    // Setters

    public void setVoltagePivot(double volts){
        pivotMotor.setVoltage(volts);
    }

    public void setVoltageSpin(double volts){
        spinMotor.setVoltage(volts);
    }

    // Getters

    public double getVelocitySpin(){
        return spinMotor.getVelocity().getValueAsDouble();
    }

    public double getVelocityPivot(){
        return pivotMotor.getVelocity().getValueAsDouble();
    }
}
