package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;

    
    //Constructor for stop intake command
    public StopIntake(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }


    // Stops the intake upon running
    @Override
    public void initialize(){
        intake.stopIntaking();
    }

    @Override
    public void execute(){}

    @Override
    public void end(boolean interrupted) {}  
    
    @Override
    public boolean isFinished(){
        return true;
    }
}
