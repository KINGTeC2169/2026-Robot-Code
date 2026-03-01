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

    }
    @Override
    public void execute(){
        indexer.setIndexerVoltage(0 * 12);
        indexer.setFeederVoltage(0 * 12);
    }

    //@Override //gave me an error
    public void end(){
        indexer.StopIndexer();
    }

    /*public boolean isFinished(){

    }*/
}
