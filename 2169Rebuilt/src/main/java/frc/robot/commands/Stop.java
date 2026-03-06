package frc.robot.commands;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj2.command.Command;
public class Stop extends Command{

    private Shooter shooter;
    private Indexer indexer;
    private Intake intake;
    
    public Stop(Shooter shooter, Intake intake, Indexer indexer){
        this.shooter = shooter;
        this.intake = intake;
        this.indexer = indexer;
    }

    @Override
    public void initialize(){
        shooter.stopFlywheel();
        //shooter.stopTurret();
        intake.stopIntake();
        intake.setVoltagePivot(0);
        indexer.stopIndexer();
        indexer.stopFeeder();
    }

    @Override
    public void execute(){
        shooter.stopFlywheel();
        //shooter.stopTurret();
        intake.stopIntake();
        intake.setVoltagePivot(0);
        indexer.stopIndexer();
        indexer.stopFeeder();
    }

    @Override
    public void end(boolean isInterrupted){
        shooter.stopFlywheel();
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
