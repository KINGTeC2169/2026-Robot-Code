
package frc.robot;

import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public final class Constants {
    
    public static final class Ports{
        //Driver station controller ids
        public static final int leftStick = 0;
        public static final int rightStick = 1;
        public static final int controller = 2;
        public static final int topIntake = 12; 
        public static final int bottomIntake = 15;
        public static final int ledPort = 5;
    }

    public static final class IntakeConstants{

        public static final double intakeVolts = 0.8 * 12; //INTAKE IS POSITIVE
        public static final double outtakeVolts = -0.8 * 12;

        //1.5 max boost = 150% faster
        public static final double outtakeMaxBoost = 1.0;
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
        public static final Translation3d FRONT_LEFT_TRANSLATION = new Translation3d(0.26035, 0.31115, 0.24765);
        public static final Translation3d BACK_RIGHT_TRANSLATION = new Translation3d(-0.26035, -0.31115, 0.24765);

        public static final Rotation3d FRONT_LEFT_ROTATION = new Rotation3d(Units.degreesToRadians(180),Units.degreesToRadians(53), Units.degreesToRadians(45)); 
        public static final Rotation3d BACK_RIGHT_ROTATION = new Rotation3d(Units.degreesToRadians(180),Units.degreesToRadians(53), Units.degreesToRadians(225));

        public static final double FRONT_CAMERA_HEIGHT_METERS = .24765; //TODO: Replace with correct value
        public static final double FRONT_CAMERA_PITCH_RADIANS = 0; 
    }
}
