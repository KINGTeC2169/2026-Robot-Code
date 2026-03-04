package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.LimeLightHelpers;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;

public class HoodTracking extends Command {
    
    private Shooter shooter;
    private String name;
    private String acceptedIDs;
    private PIDController hoodPID = new PIDController(Constants.TurretConstants.kp, Constants.TurretConstants.ki, Constants.TurretConstants.kd);

    public HoodTracking(Shooter shooter, String limelightName) {
        this.shooter = shooter;
        this.name = limelightName;

        addRequirements(shooter);
    }

    @Override
    public void initialize(){
        Alliance color = DriverStation.getAlliance().get();
        if(color == Alliance.Red){
            acceptedIDs = Constants.Vision.redHubTags;
        } else if (color == Alliance.Blue) {
            acceptedIDs = Constants.Vision.blueHubTags;
        }
    }

    @Override
    public void execute(){
        String id = Double.toString(LimeLightHelpers.getFiducialID(name));

        if(acceptedIDs.contains(id)) {
            double tx = LimeLightHelpers.getTX(name);
            shooter.setTurretVoltage(hoodPID.calculate(tx, 0) * Constants.TurretConstants.maxVoltage);
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
