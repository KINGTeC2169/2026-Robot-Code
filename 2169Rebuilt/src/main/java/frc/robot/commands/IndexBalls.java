package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;


public class IndexBalls extends Command{
    private Indexer indexer;

    public double takeSpeed;

    public IndexBalls(Indexer indexer){
        this.indexer = indexer;
    }
    @Override
    public void initialize(){
        //indexer.setSpeed(takeSpeed);
        //indexer.SpinIndexer();
        indexer.spinFeeder();
    }
    @Override
    public void execute(){
        //indexer.setIndexerVoltage(0 * 12);
        indexer.spinFeeder();
    }

    @Override
    public void end(boolean isInterrupted){
        //indexer.StopIndexer();
        indexer.stopFeeder();
    }

    //temporary finish condiiton? 
    /* 
    public boolean isFinished(){
        return true;
    }
    */
}
