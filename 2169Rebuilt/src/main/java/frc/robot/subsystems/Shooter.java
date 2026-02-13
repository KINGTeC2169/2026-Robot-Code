package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TurretConstants;

public class Shooter extends SubsystemBase{

    private TalonFX turret;
    private TalonFX flywheelLeft;
    private TalonFX flywheelRight;

    private VelocityDutyCycle flywheelControl;

    PIDController turretPID = new PIDController(TurretConstants.kp, TurretConstants.ki, TurretConstants.kd);
    PIDController flywheelPID = new PIDController(ShooterConstants.kp, ShooterConstants.ki, ShooterConstants.kd);

    private double targetRPM;


//some needed info

    /*turret constants:
    degree variable for end of turret rotation
    */

    /*turret variables:
    degree variable for end of turret rotation
    */

    
    /*flywheel variables:
    target RPMs 
    distances from limelight
    */

    //Constructor for the three shooter motors
    public Shooter() {
        turret = new TalonFX(0);
        flywheelLeft = new TalonFX(1);
        flywheelRight = new TalonFX(2);
    }

    public void setTargetRPM(double rpm) {
        targetRPM = rpm;
        //actually need the math from vision for this
    }

    public double getLeftRPM() {
        return 60 * flywheelLeft.getRotorVelocity().getValueAsDouble();
    }

    public double getRightRPM() {
        return 60 * flywheelRight.getRotorVelocity().getValueAsDouble();
    }

     public double getLeftCurrent(){
        return flywheelLeft.getSupplyCurrent().getValueAsDouble();
    }

     public double getRightCurrent(){
        return flywheelRight.getSupplyCurrent().getValueAsDouble();
    }

    //sets the shooter to 
    public void setFlywheelRPM(double targetRPM) {
        /*flywheelControl = new VelocityDutyCycle(rpm / 6000.0); 
        flywheelLeft.setControl(flywheelControl);
        flywheelRight.setControl(flywheelControl);
        */

        targetRPM = MathUtil.clamp(targetRPM, 0, 6000); //clamping to max RPM of the motors
        if(targetRPM > 0) {
            double leftOutput = flywheelPID.calculate(getLeftRPM(), targetRPM);
            double rightOutput = flywheelPID.calculate(getRightRPM(), targetRPM);
            flywheelLeft.setControl(new VelocityDutyCycle(leftOutput / 6000.0));
            flywheelRight.setControl(new VelocityDutyCycle(rightOutput / 6000.0));
        } else {
            flywheelLeft.setControl(new VelocityDutyCycle(0));
            flywheelRight.setControl(new VelocityDutyCycle(0));
        }
    }

    public boolean isReady() {
        double leftRPM = getLeftRPM();
        double rightRPM = getRightRPM();
        boolean equalSpeed = Math.abs(leftRPM - targetRPM) <= ShooterConstants.flyTolerance && Math.abs(rightRPM - targetRPM) <= ShooterConstants.flyTolerance;
        boolean atTarget = Math.abs(getLeftRPM() - targetRPM) <= ShooterConstants.flyTolerance && Math.abs(getRightRPM() - targetRPM) <= ShooterConstants.flyTolerance;
        return equalSpeed && atTarget;
    }


    //not using this until vision phew
    public void whipTurret() {
        /*
         * need to find the most inoffensive way to quickly spin
         * the turret in a nonviolent way to almost the opposite 
         * side. im very worried about the wires and im not sure
         * the safest way to do this quickly, so i will wait a 
         * little while to see some ideas for the turret model.
         */
    }
    

    @Override
    public void periodic() {
        setFlywheelRPM(targetRPM);
    }
}