package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;
    private double leftover;

    
    //Constructor for stop intake command
    public StopIntake(Intake intake) {
        this.intake = intake;
        this.leftover = 0;
        addRequirements(intake);
    }

    //this one keeps top motor running for a little bit
    public StopIntake(Intake intake, double leftover) {
        this.intake = intake;
        this.leftover = leftover;
        addRequirements(intake);
    }


    // Stops the intake upon running
    @Override
    public void initialize(){
        intake.stopIntaking(leftover);
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
