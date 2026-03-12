package frc.robot.commands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
public class Stop extends Command{

    private Indexer indexer;
    private Intake intake;
    
    public Stop(Intake intake, Indexer indexer){
        this.intake = intake;
        this.indexer = indexer;
    }

    @Override
    public void initialize(){
        //shooter.stopTurret();
        intake.stopIntake();
        intake.setVoltagePivot(0);
        indexer.stopIndexer();
        indexer.stopFeeder();
    }

    @Override
    public void execute(){
        //shooter.stopTurret();
        intake.stopIntake();
        intake.setVoltagePivot(0);
        indexer.stopIndexer();
        indexer.stopFeeder();
    }

    @Override
    public void end(boolean isInterrupted){
        //shooter.stopTurret();
        intake.stopIntake();
        intake.setVoltagePivot(0);
        indexer.stopIndexer();
        indexer.stopFeeder();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
