package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LED;

public class StopIntake extends Command {
    private Intake intake;
    private LED led;
    private double leftover;

    
    //Constructor for stop intake command
    public StopIntake(Intake intake, LED led) {
        this.intake = intake;
        this.led = led;
        this.leftover = 0;
        addRequirements(intake);
    }

    //this one keeps top motor running for a little bit
    public StopIntake(Intake intake, double leftover, LED led) {
        this.intake = intake;
        this.leftover = leftover;
        this.led = led;
        addRequirements(intake);
        addRequirements(led);
    }


    // Stops the intake upon running
    @Override
    public void initialize(){
        intake.stopIntaking(leftover);
        led.rainbow();
    }

    @Override
    public void execute(){}

    @Override
    public void end(boolean interrupted) {}  
    
    @Override
    public boolean isFinished(){
        return true;
    }
}
