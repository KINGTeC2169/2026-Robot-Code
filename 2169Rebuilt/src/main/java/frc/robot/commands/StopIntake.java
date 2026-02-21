package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;
    private Indexer indexer;
    private Timer timer;
    
    public StopIntake(Intake intake, Indexer indexer) {
        this.intake = intake;
        this.indexer = indexer;
        addRequirements(intake, indexer);
    }

    @Override
    public void initialize(){
        intake.stopIntake();  
        //intake.raiseIntake();
        //stop indexer
    }

    @Override
    public void execute(){
    }

    @Override
    public void end(boolean interrupted){
        //not super necessary i think
        intake.setVoltageSpin(0);
        //intake.raiseIntake();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
