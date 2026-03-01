package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class IntakeBall extends Command {
    private Intake intake;
    private Indexer indexer;
    private Timer timer;

    private boolean hasStarted = false;
    
    public IntakeBall(Intake intake, Indexer indexer) {
        this.intake = intake;
        this.indexer = indexer;
        addRequirements(intake, indexer);
    }

    @Override
    public void initialize(){
        timer = new Timer();
        //intake.lowerIntake();  
    }

    @Override
    public void execute(){
        intake.setVoltageSpin(-0.5 * 12);  
        intake.lowerIntake();  
        // indexer.setVoltage(0); TODO: MAKE THIS WORK WITH INDEXER

        if(intake.getVelocitySpin() < 100 && !hasStarted) {
            timer.start();
            hasStarted = true;
        }
    }

    @Override
    public void end(boolean interrupted){
        intake.setVoltageSpin(0);
        // indexer.setVoltage(0); TODO: MAKE THIS WORK WITH INDEXER

        //intake.raiseIntake();
    }

    @Override
    public boolean isFinished(){
        return timer.get() > 3;    // TODO: TEMPORARY FINISH CONDITION, REPLACE WITH REAL ONE
    }
}
