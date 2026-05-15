// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static final class Ports {
    // Driver station controller ids
    public static final int leftStick = 0;
    public static final int rightStick = 1;
    public static final int controller = 2;

    // Intake can ids
    public static final int topIntake = 12;
    public static final int bottomIntake = 15;

    // LED DIO port
    public static final int ledPort = 0;
  }

  public static final class IntakeConstants {

    public static final double intakeVolts = 0.7 * 12; // INTAKE IS POSITIVE
    public static final double outtakeVolts = -0.7 * 12; // -0.7 * 12

    // 1.25 max boost = 125% faster
    public static final double outtakeMaxBoost = 0.5;
  }

  public static final class Vision {

    // PhotonVision stuff
    public static final Translation3d FRONT_LEFT_TRANSLATION =
        new Translation3d(0.26035, 0.31115, 0.24765);
    public static final Translation3d BACK_RIGHT_TRANSLATION =
        new Translation3d(-0.26035, -0.31115, 0.24765);
    public static final Translation3d FRONT_TRANSLATION = new Translation3d(0.3048, 0, 0.1651);

    public static final Rotation3d FRONT_LEFT_ROTATION =
        new Rotation3d(
            Units.degreesToRadians(0), Units.degreesToRadians(-53), Units.degreesToRadians(45));
    public static final Rotation3d BACK_RIGHT_ROTATION =
        new Rotation3d(
            Units.degreesToRadians(0), Units.degreesToRadians(-53), Units.degreesToRadians(225));

    public static final Transform3d FRONT_LEFT_CAMERA_TO_ROBOT =
        new Transform3d(FRONT_LEFT_TRANSLATION, FRONT_LEFT_ROTATION);
    public static final Transform3d BACK_RIGHT_CAMERA_TO_ROBOT =
        new Transform3d(BACK_RIGHT_TRANSLATION, BACK_RIGHT_ROTATION);

    public static final double FRONT_CAMERA_HEIGHT_METERS =
        .24765; 
    public static final double FRONT_CAMERA_PITCH_RADIANS = 0;
  }
}
