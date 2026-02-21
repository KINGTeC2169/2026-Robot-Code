public class Shoot extends Command{
    
    private Shooter shooter;
    private Intake intake;
    private Indexer index;
    private double rpm;
    private int num;


    public Shoot(Shooter shoot, Intake itake, Indexer indexer double rotationsPerMinute){
        this.shooter = shoot; // <-- bryson did that
        this.intake = itake;
        this.index = indexer
        this.rpm = rotationsPerMinute;
    }

    @Override
    public void initialize(){
        shooter.setTargetRPM(rpm);
        intake.raiseIntake();
        num = 0;
    }

    @Override
    public void execute(){
        shooter.setFlywheelRPM();
        if(shooter.isReady()){
            index.SpinPreShooter()
        }
        num++;
    }

    @Override
    public void end(boolean interrupted){
        shooter.stopFlywheel();
        index.StopPreShooter();
    }

    @Override
    public boolean isFinished(){
        if(num == 200)return true;
    }

}
