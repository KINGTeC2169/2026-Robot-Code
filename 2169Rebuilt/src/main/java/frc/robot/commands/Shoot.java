package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
public class Shoot extends Command{
    
    private Shooter shooter;
    private Intake intake;
    //private Indexer index;
    private double rpm;
    private int num;


    public Shoot(Shooter shoot, Intake itake, double rotationsPerMinute){
        this.shooter = shoot;
        this.intake = itake;
        //this.index = indexer;
        this.rpm = rotationsPerMinute;
    }

    @Override
    public void initialize(){
        shooter.setTargetRPM(rpm);
        //intake.raiseIntake();
        num = 0;
    }

    @Override
    public void execute(){
        shooter.setFlywheelRPM();
        /* 
        if(shooter.isReady()){
            index.SpinPreShooter();
        }
        */
        num++;
    }

    @Override
    public void end(boolean interrupted){
        shooter.stopFlywheel();
        shooter.setTargetRPM(0);
        //index.StopPreShoot();
    }

    @Override
    public boolean isFinished(){
        /* 
        //4 seconds
        if(num >= 200){
            return true;
        }        
        */
        return false;
    }

}
