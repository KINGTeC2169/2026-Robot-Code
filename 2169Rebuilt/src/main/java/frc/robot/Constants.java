package frc.robot;

import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.controller.PIDController;

public final class Constants {
    
    public static final class Ports{
        //Driver station controller ids
        public static final int leftStick = 0;
        public static final int rightStick = 1;
        public static final int controller = 2;
        public static final int leftFly = 11;
        public static final int rightFly = 14;
        public static final int turret = 17;
        public static final int indexMotor = 15;        
        public static final int feeder = 16;            
        public static final int pivotMotor = 12;
        public static final int spinMotor = 13; 
        public static final int intakeEncoder = 18;
    }

    public static final class IntakeConstants{
        public static final double pivotMaxHeight = -0.164;  
        public static final double pivotMinHeight = -.32;

        public static final double kP = .8;  // TODO: REPLACE WITH REAL KP
        public static final double kI = 0;  // TODO: REPLACE WITH REAL KI
        public static final double kD = 0;  // TODO: REPLACE WITH REAL KD

        public static final double encoderExpectedZero = 0; // TODO: REPLACE WITH REAL ENCODER ZERO
        
    }

    public static final class IndexerConstants{
        public static final double initialIndexerVoltage = 0; // TODO: Make value real
        public static final double initialFeederVoltage = 9;
    }
    
    
    public static final class TurretConstants {

        public static double kp = 0.01; 
        public static double ki = 0.0; 
        public static double kd = 0.0;

        //max 18 rpm
        //GEAR RATIO OF ~35:1
        public static double maxVoltage = 0.003; //TODO: REPLACE WITH REAL MAX VOLTAGE
        public static double maxAngle = 0;       //TODO: REPLACE WITH REAL MAX ANGLE
        public static double minAngle = 0;       //TODO: REPLACE WITH REAL MIN ANGLE 
    }

    public static final class ShooterConstants {
        public static double kp = 1.5; 
        public static double ki = 0; 
        public static double kd = 0;

        public static boolean shooterOn = false; //not actually a constant...

        public final static int defaultGear = 1;

        //public static double shootRPM = 0.0; //not actually a constant...

        public static double[] shootSpeeds = {1200, 2400, 3600, 4800, 6000}; //rpm, arranged lowest to highest

        public static final double flyTolerance = 50.0; //rpm

        public static final double encoderExpectedMax = 0;  // TODO: REPLACE WITH REAL ENCODER MAX
        public static final double encoderExpectedMin = 0;  // TODO: REPLACE WITH REAL ENCODER MIN
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

        public static final double hoodAngle = 0;                 // TODO: REPLACE WITH REAL ANGLE
        public static final double hoodHeight = 0;                // TODO: REPLACE WITH REAL HEIGHT
        public static final double hubTagHeight = 44.25;          // inches
        public static final String hoodLimelightName = "name";    // TODO: REPLACE WITH HOOD LL NAME
    }
}
