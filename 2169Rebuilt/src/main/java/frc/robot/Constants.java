
package frc.robot;

import com.pathplanner.lib.config.PIDConstants;

public final class Constants {
    
    public static final class Ports{
        //Driver station controller ids
        public static final int leftStick = 0;
        public static final int rightStick = 1;
        public static final int controller = 2;
        public static final int topIntake = 12; 
        public static final int bottomIntake = 15;
    }

    public static final class IntakeConstants{
        public static final double intakeVolts = -0.25 * 12;
        public static final double outtakeVolts = 0.25 * 12;
    }
    

    public static final class DriveConstants {
        public static PIDConstants autoTranslationPID = new PIDConstants(5, 0, 0);
        public static PIDConstants autoRotationPID = new PIDConstants(5, 0, 0);
    }

    public final class Vision{
        //8/5/11/2 are scary
        //18/27/21/24 are spooky
        public static final String blueHubTags = "18/27/26/25/21/24";
        public static final String redHubTags = "8/5/9/10/11/2";
    }
}
