package frc.robot.commands;

import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;

public class SpinTurret extends Command {

    private Shooter shooter;
    private double volts;

    public SpinTurret(Shooter shooter, double volts) {
        this.shooter = shooter;
        this.volts = volts;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.whipTurret(volts);
    }
    
    @Override
    public void execute() {
        shooter.whipTurret(volts);
    }
    
    @Override
    public void end(boolean interrupted) {
        shooter.stopTurret();
    }

    @Override
    public boolean isFinished() {
        return volts <= 1 && volts >= -1; //TODO: REPLACE WITH REAL FINISH CONDITION
    }

}
