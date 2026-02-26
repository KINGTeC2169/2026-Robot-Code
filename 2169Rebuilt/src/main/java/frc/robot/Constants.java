package frc.robot;


public final class Constants {

    public final class Vision{
        public static final String blueHubTags = "18/27/19/20/26/25/21/24";
        public static final String redHubTags = "8/5/9/10/4/3/11/2";

        public static final double hoodAngle = 0;                 // TODO: REPLACE WITH REAL ANGLE
        public static final double hoodHeight = 0;                // TODO: REPLACE WITH REAL HEIGHT
        public static final double hubTagHeight = 44.25;          // inches
        public static final String hoodLimelightName = "name";    // TODO: REPLACE WITH HOOD LL NAME
    }

    
    public static final class Ports{
        //Driver station controller ids
        public static final int leftStick = 0;
        public static final int rightStick = 1;
        public static final int buttons = 5;             // TODO: REPLACE WITH REAL BUTTONBOARD ID
        public static final int controller = 6;            // TODO: REPLACE WITH REAL CONTROLLER ID
        public static final int pivotMotor = 11;         // TODO: REPLACE WITH REAL MOTOR ID
        public static final int spinMotor = 12;          // TODO: REPLACE WITH REAL MOTOR ID
        public static final int intakeEncoder = 13;      // TODO: REPLACE WITH REAL ENCODER ID
    }

    public static final class IntakeConstants{
        public static final double pivotMaxHeight = 0;  // TODO: REPLACE WITH REAL NUM
        public static final double pivotMinHeight = 0;  // TODO: REPLACE WITH REAL NUM

        public static final double kP = 0;  // TODO: REPLACE WITH REAL KP
        public static final double kI = 0;  // TODO: REPLACE WITH REAL KI
        public static final double kD = 0;  // TODO: REPLACE WITH REAL KD

        public static final double encoderExpectedZero = 0; // TODO: REPLACE WITH REAL ENCODER ZERO
    }
    
    public static final class TurretConstants {

        public static double kp = 0.0; 
        public static double ki = 0.0; 
        public static double kd = 0.0;

        //angles junk
    }

    public static final class ShooterConstants {
        public static double kp = 0.0; 
        public static double ki = 0.0; 
        public static double kd = 0.0;

        public static double shootRPM = 0.0; //not actually a constant...

        public static final double flyTolerance = 15.0;
    }
}
