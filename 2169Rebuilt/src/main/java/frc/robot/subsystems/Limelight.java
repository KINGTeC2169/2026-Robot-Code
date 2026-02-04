package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    
    public Limelight(){
        SmartDashboard.putNumber("tx", getTx());
        SmartDashboard.putNumber("ty", getTy());
    }

    // Getters
    public double getTx(){
        return table.getEntry("tx").getDouble(0);
    }

    public double getTy(){
        return table.getEntry("ty").getDouble(0);
    }

    public double getID(){
        return table.getEntry("tid").getDouble(0);
    }
}
