package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
    public SendableChooser<Command> autoChooser;
    //public final Telemetry logger = new Telemetry(MaxSpeed); //needs the Telemtry file

    public Command getAutonomousCommand() {
    //An example command will be run in autonomous
    return autoChooser.getSelected();
 }
}
