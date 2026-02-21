package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.Ports;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Intake extends SubsystemBase{
    private TalonFX pivotMotor;
    private TalonFX spinMotor;
    private DutyCycleEncoder encoder;

    private PIDController pivotPID;

    private final double pivotMaxHeight = IntakeConstants.pivotMaxHeight;
    private final double pivotMinHeight = IntakeConstants.pivotMinHeight;

    public Intake(){
        pivotMotor = new TalonFX(Ports.pivotMotor);
        spinMotor = new TalonFX(Ports.spinMotor);

        pivotPID = new PIDController(IntakeConstants.kP, IntakeConstants.kI, IntakeConstants.kD);
        encoder = new DutyCycleEncoder(Ports.intakeEncoder, 1, IntakeConstants.encoderExpectedZero);
    }

    // Setters

    // Set the voltage that the pivot motor runs at
    public void setVoltagePivot(double volts){
        pivotMotor.setVoltage(volts);
    }

    // Set the voltage that the intake motor runs at
    public void setVoltageSpin(double volts){
        spinMotor.setVoltage(volts);
    }

    // Set the position for the pivot to move to
    // idk if this function is really needed, but it might be useful *shrug*
    public void setPivotPosition(double position){
        position = MathUtil.clamp(position, pivotMinHeight, pivotMaxHeight);

        setVoltagePivot(pivotPID.calculate(getPosition(), position));
    }

    // Getters

    // Returns the velocity of the intake motor as a double
    public double getVelocitySpin(){
        return spinMotor.getVelocity().getValueAsDouble();
    }

    // Returns the velocity of the pivot motor as a double
    public double getVelocityPivot(){
        return pivotMotor.getVelocity().getValueAsDouble();
    }

    // Return the position of the pivot motor
    public double getPosition(){
        return encoder.get();
    }

    //  MISC functions

    // lower the intake to the grab position
    public void lowerIntake(){
        setVoltagePivot(pivotPID.calculate(getPosition(), IntakeConstants.pivotMinHeight));
    }
    
    // raise the intake to the raised position
    public void raiseIntake(){
        setVoltagePivot(pivotPID.calculate(getPosition(), IntakeConstants.pivotMaxHeight));
    }

    // stop the intake from spinning
    public void stopIntake(){
        setVoltageSpin(0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Pivot Velocity", getVelocityPivot());
        SmartDashboard.putNumber("Spin Velocity", getVelocitySpin());
        SmartDashboard.putNumber("Encoder Position", getPosition());
    }
}
