package frc.robot.commands;
import frc.robot.subsystems.Intake;
import frc.robot.commands.*;
import frc.robot.Constants.IntakeConstants;

import edu.wpi.first.wpilibj2.command.Command;
public class JustIntakeN extends Command {

    private Intake intake;

    public JustIntakeN(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        
    }
    
    @Override 
    public void execute(){
        intake.spinToggleN();
    }

    @Override
    public void end(boolean interrupted){
        
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
