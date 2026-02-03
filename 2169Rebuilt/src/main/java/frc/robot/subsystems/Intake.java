package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase{
    private TalonFX pivotMotor;
    private TalonFX spinMotor;

    private PIDController pivotPID;

    private final double pivotMaxHeight = IntakeConstants.pivotMaxHeight;
    private final double pivotMinHeight = IntakeConstants.pivotMinHeight;

    public Intake(){
        pivotMotor = new TalonFX(Constants.Ports.pivotMotor);
        spinMotor = new TalonFX(Constants.Ports.spinMotor);

        pivotPID = new PIDController(IntakeConstants.kP, IntakeConstants.kI, IntakeConstants.kD);
    }

    // Setters

    public void setVoltagePivot(double volts){
        pivotMotor.setVoltage(volts);
    }

    public void setVoltageSpin(double volts){
        spinMotor.setVoltage(volts);
    }

    public void setPivotPosition(double position){
        position = MathUtil.clamp(position, pivotMinHeight, pivotMaxHeight);
    }

    // Getters

    public double getVelocitySpin(){
        return spinMotor.getVelocity().getValueAsDouble();
    }

    public double getVelocityPivot(){
        return pivotMotor.getVelocity().getValueAsDouble();
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Pivot Velocity", getVelocityPivot());
        SmartDashboard.putNumber("Spin Velocity", getVelocitySpin());
    }
}
