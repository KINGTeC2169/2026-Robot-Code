package frc.robot.commands;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.Command;

public class PreShoot extends Command{

        private Indexer indexer;

        private boolean wasReady;

        public PreShoot(Indexer indexer){
            this.indexer = indexer;
            addRequirements(indexer);
        }

        @Override
        public void initialize(){
            indexer.spinFeeder();

        }

        @Override
        public void execute(){
            indexer.spinFeeder();
        }

        @Override
        public void end(boolean interrupted){
            indexer.stopFeeder();
        }

        @Override
        public boolean isFinished(){
            return false;
        }
    }

