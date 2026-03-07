package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;
    private Indexer indexer;

    private int num;
    
    public StopIntake(Intake intake, Indexer indexer) {
        this.intake = intake;
        this.indexer = indexer;
        
        addRequirements(intake, indexer);
    }

    @Override
    public void initialize(){
        
        intake.raiseIntake();
        num = 0;
    }

    @Override
    public void execute(){
        num++;
        intake.raiseIntake();
    }

    @Override
    public void end(boolean interrupted){
        intake.setVoltageSpin(0);
        
    }

    @Override
    public boolean isFinished(){
        //4 seconds
        if(num >= 200){
            return true;
        }   
        return false;
    }
}
