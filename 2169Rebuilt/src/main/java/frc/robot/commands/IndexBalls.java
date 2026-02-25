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
        //indexer.setSpeed(takeSpeed);
        //indexer.SpinIndexer();
    }

    //@Override //gave me an error
    public void end(){
        //indexer.setSpeed(0);
        //indexer.SpinIndexer();
    }

    /*public boolean isFinished(){

    }*/
}
