package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

import frc.robot.Constants.*;

public class ModifySpeed extends Command {

    private Shooter shooter; 
    private int num;

    public ModifySpeed(Shooter shooter, int num){
        this.shooter = shooter;
        this.num = num;
        addRequirements(shooter);
    }

    @Override
    public void initialize(){
        shooter.changeGear(num);
        shooter.setTargetRPM(ShooterConstants.shootSpeeds[shooter.getGear() - 1]);
    }
    
}
