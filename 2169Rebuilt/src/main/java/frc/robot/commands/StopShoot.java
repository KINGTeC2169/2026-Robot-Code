package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.Command;


public class StopShoot extends Command{
    
    private Shooter shooter;
    private Indexer indexer;

    public StopShoot(Shooter shoot, Indexer indexer){
        this.shooter = shoot;
        this.indexer = indexer;
    }

    @Override
    public void initialize(){
        shooter.stopFlywheel();
        indexer.stopFeeder();
        addRequirements(shooter, indexer);
    }

    @Override
    public void execute(){
        shooter.stopFlywheel();
        indexer.stopFeeder();
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return true;
    }

}
