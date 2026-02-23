package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TurretConstants;

public class Shooter extends SubsystemBase{

    private TalonFX turret;
    private TalonFX flywheelLeft;
    private TalonFX flywheelRight;

    //private VelocityDutyCycle flywheelControl;

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
        flywheelLeft = new TalonFX(Ports.leftFly);
        flywheelRight = new TalonFX(Ports.rightFly);
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


     public double getLeftVoltage(){
        return flywheelLeft.getSupplyVoltage().getValueAsDouble();
    }

     public double getRightVoltage(){
        return flywheelRight.getSupplyVoltage().getValueAsDouble();
    }

    //sets the shooter to 
    public void setFlywheelRPM() {
        /*flywheelControl = new VelocityDutyCycle(rpm / 6000.0); 
        flywheelLeft.setControl(flywheelControl);
        flywheelRight.setControl(flywheelControl);
        */

        //targetRPM = MathUtil.clamp(targetRPM, 0, 6000); //clamping to max RPM of the motors
         
        if(targetRPM > 0) {
            double leftOutput = flywheelPID.calculate(getLeftVoltage(), targetRPM / 6000.0 * 12);
            double rightOutput = flywheelPID.calculate(getRightVoltage(), targetRPM / 6000.0 * 12);
            flywheelLeft.setVoltage(-leftOutput);
            flywheelRight.setVoltage(rightOutput);
        } else {
            flywheelLeft.setVoltage(0);
            flywheelRight.setVoltage(0);
        }
        

        //flywheelLeft.setVoltage(-targetRPM / 6000 * 12);
        //flywheelRight.setVoltage(targetRPM / 6000 * 12);
    }

    public boolean isReady() {
        double leftRPM = Math.abs(getLeftRPM());
        double rightRPM = Math.abs(getRightRPM());
        boolean equalSpeed = Math.abs(leftRPM - rightRPM) <= ShooterConstants.flyTolerance;
        boolean atTarget = Math.abs(leftRPM - targetRPM) <= ShooterConstants.flyTolerance && Math.abs(rightRPM - targetRPM) <= ShooterConstants.flyTolerance;
        return equalSpeed && atTarget;
    }

    public void stopFlywheel(){
        //flywheelLeft.setControl(new VelocityDutyCycle(0));
        //flywheelRight.setControl(new VelocityDutyCycle(0));

        flywheelLeft.setVoltage(0);
        flywheelRight.setVoltage(0);
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
        SmartDashboard.putNumber("Left Flywheel RPM", getLeftRPM());
        SmartDashboard.putNumber("Right Flywheel RPM", getRightRPM());
    }
}