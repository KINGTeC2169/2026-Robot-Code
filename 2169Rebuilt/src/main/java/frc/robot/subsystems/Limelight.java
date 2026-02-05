package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
    private static NetworkTable table1 = NetworkTableInstance.getDefault().getTable("limelight 1");
    private static NetworkTable table2 = NetworkTableInstance.getDefault().getTable("limelight 2");
    
    public Limelight(){
        SmartDashboard.putNumber("tx LL 1", getTxLL1());
        SmartDashboard.putNumber("ty LL 1", getTyLL1());
        SmartDashboard.putNumber("id LL 1", getIDLL1());

        SmartDashboard.putNumber("tx LL 2", getTxLL2());
        SmartDashboard.putNumber("ty LL 2", getTyLL2());
        SmartDashboard.putNumber("id LL 2", getIDLL2());
    }

    // Getters
    // get tx from limelight 1
    // where tx is the horizontal offset from center of april tag to LL crosshair
    public double getTxLL1(){
        return table1.getEntry("tx").getDouble(0);
    }
    // get tx from limelight 1
    // where ty is the vertical offset from center of april tag to LL crosshair
    public double getTyLL1(){
        return table1.getEntry("ty").getDouble(0);
    }
    // get tid from limelight 1
    // where tid is the id of the tag
    public double getIDLL1(){
        return table1.getEntry("tid").getDouble(0);
    }
    // get tx from limelight 2
    // where tx is the horizontal offset from center of april tag to LL crosshair
    public double getTxLL2(){
        return table2.getEntry("tx").getDouble(0);
    }
    // get tx from limelight 2
    // where ty is the vertical offset from center of april tag to LL crosshair
    public double getTyLL2(){
        return table2.getEntry("ty").getDouble(0);
    }
    // get tid from limelight 2
    // where tid is the id of the tag
    public double getIDLL2(){
        return table2.getEntry("tid").getDouble(0);
    }
}
