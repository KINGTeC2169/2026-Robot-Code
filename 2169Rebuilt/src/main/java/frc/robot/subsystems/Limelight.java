package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.Vision;
import Util.LimelightHelpers;

public class Limelight {

    private static String hoodName = Vision.hoodLimelightName;
  
    public Limelight(){
        SmartDashboard.putNumber("tx", getTx());
        SmartDashboard.putNumber("id", getID());
    }

    // Getters
    // get tx from limelight 1
    // where tx is the horizontal offset from center of april tag to LL crosshair
    public double getTx(){
        return LimelightHelpers.getTX(hoodName);
    }
    // get ty from limelight 1
    // where tx is the horizontal offset from center of april tag to LL crosshair
    public double getTy(){
        return LimelightHelpers.getTY(hoodName);
    }
    // get id from limelight 1
    // where id is the id of the tag
    public double getID(){
        return LimelightHelpers.getFiducialID(hoodName);
    }
    // checks if the limelight is looking
    // at one of the hub april tags
    public boolean isValidID(){
        double id = getID();
        Alliance color = DriverStation.getAlliance().get();
        
        if(color == Alliance.Red){
            for(double tag : Vision.redHubTags){
                if(id == tag) return true;
            }
        }
        
        if (color == Alliance.Blue) {
            for(double tag : Vision.blueHubTags){
                if(id == tag) return true;
            }
        
        }

        return false;
    }
    // Distance from tag in inches
    // Might need some constant value added or multiplied
    public double getDistance(){
        return ((Vision.hubTagHeight - Vision.hoodHeight) / Math.tan(Vision.hoodAngle + getTy())) +22;
    }
}
