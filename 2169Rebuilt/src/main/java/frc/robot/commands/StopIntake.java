package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;
    //private Indexer indexer;

    private int num;
    
    public StopIntake(Intake intake) {
        this.intake = intake;
        //this.indexer = indexer;
        
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        intake.stopIntaking();
    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted) {

    }  
    

    @Override
    public boolean isFinished(){
        return true;
    }
}
