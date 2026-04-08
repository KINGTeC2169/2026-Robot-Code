package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;
import frc.robot.Constants.IntakeConstants;


public class IntakeBall extends Command {
    private Intake intake;
    private LED led;

    private double volts;
    private final CommandXboxController operator = new CommandXboxController(2);
    
    // Constructor for intake command 
    public IntakeBall(Intake intake, double volts, LED led) {
        this.intake = intake;
        this.volts = volts;
        this.led = led;
        addRequirements(intake, led);
    }



    // Set the voltage at which the motors intake/outtake 
    @Override 
    public void initialize(){
        intake.intakeDirection(volts, operator);
        if(volts < 0){ //for intaking
            led.scrollYellow();
        }
        else if(volts > 0){ //for outtaking
            led.scrollRed();
        }
    }

    // Adjust speed at which outtake spits out balls by depth of trigger press up to 65% voltage
    @Override
    public void execute(){
        intake.intakeDirection(volts, operator);
    }

    @Override
    public void end(boolean interrupted){
        intake.stopIntaking();
        led.still();
    }

    // Stops the intake when the stop command runs
    @Override
    public boolean isFinished(){
        if(DriverStation.isTeleop()){
        return(operator.b().debounce(.09).getAsBoolean()) || 
        operator.a().debounce(.09).getAsBoolean() ||
        operator.leftBumper().debounce(.09).getAsBoolean();
        }else{
            return false;
        }
    }
}

