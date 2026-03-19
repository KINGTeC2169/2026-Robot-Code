package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Intake;


public class IntakeBall extends Command {
    private Intake intake;
    private double volts;
    private final CommandXboxController operator = new CommandXboxController(0);
    
    // Constructor for intake command 
    public IntakeBall(Intake intake, double volts) {
        this.intake = intake;
        this.volts = volts;
        addRequirements(intake);
    }



    // Set the voltage at which the motors intake/outtake 
    @Override 
    public void initialize(){
        intake.intakeDirection(volts, operator);
    }

    // Adjust speed at which outtake spits out balls by depth of trigger press up to 65% voltage
    @Override
    public void execute(){
        intake.intakeDirection(volts, operator);
    }

    @Override
    public void end(boolean interrupted){
        intake.stopIntaking();
    }

    // Stops the intake when the stop command runs
    @Override
    public boolean isFinished(){
        return(operator.b().debounce(.09).getAsBoolean()) || 
        operator.a().debounce(.09).getAsBoolean() ||
        operator.leftBumper().debounce(.09).getAsBoolean();
    }
}
