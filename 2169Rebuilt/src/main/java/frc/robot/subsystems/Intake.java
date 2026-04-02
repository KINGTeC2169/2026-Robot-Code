package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants.Ports;
import frc.robot.Constants.IntakeConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class Intake extends SubsystemBase{

    private TalonFX topIntake;
    private TalonFX bottomIntake;

    private TalonFXConfigurator topIntakeConfig;
    private TalonFXConfigurator bottomIntakeConfig;
    private TalonFXConfiguration limitConfigs = new TalonFXConfiguration() 
                .withCurrentLimits(new CurrentLimitsConfigs()
                .withStatorCurrentLimit(Amps.of(50))
                .withStatorCurrentLimitEnable(true));

    private boolean direction;
    // Constructor for the intake subsystem
    public Intake(){
        topIntake = new TalonFX(Ports.topIntake);
        bottomIntake = new TalonFX(Ports.bottomIntake);

        topIntakeConfig = topIntake.getConfigurator();
        bottomIntakeConfig = bottomIntake.getConfigurator();

        topIntakeConfig.apply(limitConfigs);
        bottomIntakeConfig.apply(limitConfigs);
    }


    // Sets proper direction and speed for intake/outtake procedure
    public void intakeDirection(double voltage, CommandXboxController operator) {
        voltage *= voltage != 0 ? 1 + (operator.getRightTriggerAxis() * IntakeConstants.outtakeMaxBoost) : 1;
        topIntake.setVoltage(voltage * 0.5);
        bottomIntake.setVoltage(voltage * 0.25);
        if(voltage > 0){
            direction = true; //intake
        }else{
            direction = false;
        }
    }


    // Stops the intake from spinning
    public void stopIntaking() {
        topIntake.setVoltage(0);
        bottomIntake.setVoltage(0);
    }

    public void stopIntaking(double topspeed) {
        topIntake.setVoltage(topspeed);
        bottomIntake.setVoltage(0);
    }

    


    // DO NOT look down



































    






    
    // // Setters

    // // Set the voltage that the pivot motor runs at
    // // public void setVoltagePivot(double volts){
    // //     pivotMotor.setVoltage(volts);
    // // }

    // // Set the voltage that the intake motor runs at
    // public void setVoltageSpin(double volts){
    //     topIntake.setVoltage(-volts);
    //     if(volts == 0){
    //         intaking = false;
    //     }
    // }

    // // Set the position for the pivot to move to
    // // idk if this function is really needed, but it might be useful *shrug*
    // // public void setPivotPosition(double position){
    // //     position = MathUtil.clamp(position, pivotMinHeight, pivotMaxHeight);

    // //     setVoltagePivot(pivotPID.calculate(getPosition(), position));
    // // }

    // boolean for the toggle
    // public void setIntaking(boolean bool){
    //     intaking = bool;
    // }

    // Toggle the intake on and off
    // public void spinToggle(){
    //     intaking = !intaking;
    //     if(intaking){
    //         setVoltageSpin(.65 * 12);
    //     } else{
    //         setVoltageSpin(0);
    //     }
    // }

    // public void spinToggleN(){
    //     setVoltageSpin(-.65 * 12);
    // }

    // // Getters

    // public boolean isIntaking(){
    //     return intaking;
    // }

    // // Returns the velocity of the intake motor as a double

    // // Return the position of the pivot motor
    // public double getPosition(){
    //     return encoder.get();
    // }

    // public double testing(){
    //     return Math.abs(getPosition() - IntakeConstants.pivotMaxHeight * 38);
    // }

    // //  MISC functions

    // // lower the intake to the grab position
    // // public void lowerIntake(){
    // //     // if(getPosition() >= -.25){
    // //     //     setVoltagePivot(pivotPID.calculate(getPosition(), IntakeConstants.pivotMinHeight));
    // //     // } else{
    // //     //     setVoltagePivot(0);
    // //     // }
        
    // //     if(getPosition() < -.1){
    // //         setVoltagePivot(0);
    // //     }else{
    // //         setVoltagePivot(-Math.abs(getPosition() - IntakeConstants.pivotMinHeight));
    // //     }
        
    // // }
    
    // // raise the intake to the raised position
    // // public void raiseIntake(){
    // //     // if(getPosition() <= -.25){
    // //     //     setVoltagePivot(pivotPID.calculate(getPosition(), IntakeConstants.pivotMaxHeight));
    // //     // } else{
    // //     //     setVoltagePivot(0);
    // //     // }
        
    // //     if(getPosition() > -.2){
    // //         setVoltagePivot(0);
    // //     }else{
    // //         setVoltagePivot(Math.abs(getPosition() - IntakeConstants.pivotMaxHeight) * 30);
    // //     }
    // // }

    // // stop the intake from spinning
    // public void stopIntake(){
    //     setVoltageSpin(0);
    //     intaking = false;
    // }

    @Override
    public void periodic(){
        // SmartDashboard.putNumber("Pivot Velocity", getVelocityPivot());
         SmartDashboard.putBoolean("Spin Velocity", direction);
        // SmartDashboard.putNumber("Encoder Position", getPosition());
        // SmartDashboard.putData("Pivot PID", pivotPID);

        // SmartDashboard.putNumber("Pivot Input", testing());
    }
}