package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

public class Intake {
    private TalonFX pivotMotor;
    private TalonFX spinMotor;

    private final double pivotMaxHeight;
    private final double pivotMinHeight;

    public Intake(){
        pivotMotor = new TalonFX(Constants.Ports.pivotMotor);
        spinMotor = new TalonFX(Constants.Ports.spinMotor);
    }

    // Setters

    public void setVoltagePivot(double voltage){
        pivotMotor.setVoltage(voltage);
    }
}
