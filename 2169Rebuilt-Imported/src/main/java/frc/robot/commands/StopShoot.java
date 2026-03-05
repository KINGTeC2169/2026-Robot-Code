package frc.robot.commands;

import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;


public class StopShoot extends Command{
    
    private Shooter shooter;

    public StopShoot(Shooter shoot){
        this.shooter = shoot;
    }

    @Override
    public void initialize(){
        shooter.stopFlywheel();
    }

    @Override
    public void execute(){
        shooter.stopFlywheel();
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return true;
    }

}
