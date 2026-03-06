package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;


public class IndexBalls extends Command{
    private Indexer indexer;
    private int num;
    private int time;

    public double takeSpeed;

    public IndexBalls(Indexer indexer, int num){
        this.indexer = indexer;
        this.num = num;
    }
    @Override
    public void initialize(){
        //indexer.setSpeed(takeSpeed);
        indexer.spinIndexer(num);
        //indexer.spinFeeder();
    }
    @Override
    public void execute(){
        indexer.spinIndexer(num);
        //time++;
    }

    @Override
    public void end(boolean isInterrupted){
        indexer.stopIndexer();
    }

    //temporary finish condiiton? 
    
    @Override
    public boolean isFinished(){
        //4 seconds
        // if(time >= 200){
        //     return true;
        // }  
        return false;
    }
    
}
