
package frc.robot;

import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;

public final class Constants {
    
    public static final class Ports{
        //Driver station controller ids
        public static final int leftStick = 2;
        public static final int rightStick = 1;
        public static final int controller = 0;
        public static final int topIntake = 12; 
        public static final int bottomIntake = 15;
    }

    public static final class IntakeConstants{
        public static final double intakeVolts = -0.4 * 12;
        public static final double outtakeVolts = 0.4 * 12;
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

        //PhotonVision stuff
        public static final Translation3d FRONT_LEFT_TRANSLATION = new Translation3d(0.5, 0.0, 0.5); //TODO: Replace with correct values
        public static final Translation3d BACK_RIGHT_TRANSLATION = new Translation3d(0.5, 0.0, 0.5); //TODO: Replace with correct values

        public static final Rotation3d FRONT_LEFT_ROTATION = new Rotation3d(0,0,0); //TODO: Replace with correct values        public static final Rotation3d FRONT_LEFT_ROTATION = new Rotation3d(0,0,0); //TODO: Replace with correct values
        public static final Rotation3d BACK_RIGHT_ROTATION = new Rotation3d(0,0,0); //TODO: Replace with correct values
    }
}
