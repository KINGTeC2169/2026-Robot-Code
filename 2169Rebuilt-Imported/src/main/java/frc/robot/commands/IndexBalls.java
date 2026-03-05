package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;


public class IndexBalls extends Command{
    private Indexer indexer;

    public double takeSpeed;

    public IndexBalls(Indexer indexer, int direction){
        this.indexer = indexer;
        this.direction = direction;
    }
    @Override
    public void initialize(){
        //indexer.setSpeed(takeSpeed);
        //indexer.SpinIndexer();
        //indexer.spinFeeder();
    }
    @Override
    public void execute(){
        //indexer.setIndexerVoltage(0 * 12);
        //indexer.spinFeeder();
        indexer.spinFeeder(direction);
    }

    @Override
    public void end(boolean isInterrupted){
        //indexer.StopIndexer();
        //indexer.StopPreShoot();
        indexer.StopIndexer();
    }

    //temporary finish condiiton? 
    /* 
    public boolean isFinished(){
        return true;
    }
    */
}
