package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Velocity;

public class Shooter {

    private TalonFX turret;
    private TalonFX flywheelLeft;
    private TalonFX flywheelRight;

    private VelocityDutyCycle flywheelControl;

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

    public void setFlywheelRPM(double rpm) {
        /*flywheelControl = new VelocityDutyCycle(rpm / 6000.0); // Assuming max RPM is 6000
        flywheelLeft.setControl(flywheelControl);
        flywheelRight.setControl(flywheelControl);
        */
    }

    public boolean isReady() {
        double leftRPM = getLeftRPM();
        double rightRPM = getRightRPM();
        double tolerance = 15; // RPM tolerance

        boolean equalSpeed = Math.abs(leftRPM - targetRPM) <= tolerance && Math.abs(rightRPM - targetRPM) <= tolerance;
        boolean atTarget = Math.abs(getLeftRPM() - targetRPM) <= tolerance && Math.abs(getRightRPM() - targetRPM) <= tolerance;
        return equalSpeed && atTarget;
    }

    public void whipTurret() {
        /*
         * need to find the most inoffensive way to quickly spin
         * the turret in a nonviolent way to almost the opposite 
         * side. im very worried about the wires and im not sure
         * the safest way to do this quickly, so i will wait a 
         * little while to see some ideas for the turret model.
         */
    }
}
