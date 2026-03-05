package frc.robot.commands;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.Command;

public class Feed extends Command{

        private Shooter shooter;
        private Indexer indexer;

        public Feed(Shooter shoot, Indexer index){
            this.shooter = shoot;
            this.indexer = index;
            addRequirements(shooter, indexer);
        }

        @Override
        public void initialize(){

        }

        @Override
        public void execute(){
            if (shooter.isReady()){
                indexer.spinFeeder();
            }
        }

        @Override
        public void end(boolean interrupted){
            indexer.StopPreShoot();
        }

        @Override
        public boolean isFinished(){
            return !shooter.isReady();
        }
    }

