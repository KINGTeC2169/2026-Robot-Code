package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Indexer;

import frc.robot.Constants.ShooterConstants;

import edu.wpi.first.wpilibj2.command.Command;
public class Shoot extends Command{
    
    private Shooter shooter;
    private double rpm;
    private int num;


    public Shoot(Shooter shoot, double rotationsPerMinute){
        this.shooter = shoot;
        this.rpm = rotationsPerMinute;
        addRequirements(shooter);
    }

    @Override
    public void initialize(){
        shooter.setTargetRPM(rpm);
        shooter.toggleShoot();
    }

    @Override
    public void execute(){
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return true;
        //4 seconds
    //     if(num >= 200){
    //         return true;
    //     }        
    //     return !ShooterConstants.shooterOn;
    // }

}
}
