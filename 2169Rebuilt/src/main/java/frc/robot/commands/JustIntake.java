package frc.robot.commands;
import frc.robot.subsystems.Intake;
import frc.robot.commands.*;
import frc.robot.Constants.IntakeConstants;

import edu.wpi.first.wpilibj2.command.Command;
public class JustIntake extends Command {

    private Intake intake;

    public JustIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        intake.spinToggle();
    }
    
    @Override 
    public void execute(){
        //we are winning here
    }

    @Override
    public void end(boolean interrupted){
        
    }

    @Override
    public boolean isFinished(){
        return true;
    }

}
