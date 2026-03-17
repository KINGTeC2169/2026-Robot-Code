package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Ports;
// import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;


public class IntakeBall extends Command {
    private Intake intake;
    // private Indexer indexer;
    private int timer;

    private int num;
    private double volts;
    private final CommandXboxController operator = new CommandXboxController(0);
    
    public IntakeBall(Intake intake, double volts) {
        this.intake = intake;
        this.volts = volts;
        addRequirements(intake);
      
    }



    @Override
    public void initialize(){
        if(volts > 0) {
            volts = 0.1 * 12 + volts * operator.getRightTriggerAxis();
        }
        intake.intakeDirection(volts);
    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
