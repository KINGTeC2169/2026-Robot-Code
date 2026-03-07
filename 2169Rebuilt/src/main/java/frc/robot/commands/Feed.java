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
            indexer.spinFeeder();
        }

        @Override
        public void execute(){
            // if (shooter.isReady()){
            //     indexer.spinFeeder();
            //     wasReady = true;
            //     indexer.spinIndexer();
            // }
            indexer.spinFeeder();
            indexer.spinIndexer();
        }

        @Override
        public void end(boolean interrupted){
            indexer.stopFeeder();
            indexer.stopIndexer();

        }

        // @Override
        // public boolean isFinished(){
        //     // if(wasReady && !shooter.isReady()){
        //     //     return !shooter.isReady();
        //     // }
        //     // return false;
        //     return true;
        // }
    }

