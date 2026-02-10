package frc.robot.subsystems;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
//import com.revrobotics.Rev2mDistanceSensor;
//import com.revrobotics.Rev2mDistanceSensor.Port;
//import com.revrobotics.Rev2mDistanceSensor.RangeProfile;
//import com.revrobotics.Rev2mDistanceSensor.Unit;
//import com.revrobotics.spark.config.SparkMaxConfig;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.ctre.phoenix6.controls.VelocityVoltage;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;


//import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
//import frc.robot.Constants.IntakeConstants;




public class Climber extends SubsystemBase{
    private TalonFX climbMotor;
    private double speed = 0;
    private VelocityVoltage climberVelocity  = new VelocityVoltage(0);


    public Climber(){
        var talonFXConfigs = new TalonFXConfiguration();


        var slot0Configs = talonFXConfigs.Slot0;
        slot0Configs.kP = 0.01;


        climbMotor.getConfigurator().apply(talonFXConfigs);
        climbMotor.setNeutralMode(NeutralModeValue.Brake);
    }


    public TalonFX getClimbMotor(){
        return climbMotor;
    }


    public void setVoltageMotors(double volts){
        climbMotor.setVoltage(volts);
    }


    public void setSpeed(double rpm){
        speed = rpm;
    }


    public double getSpeed(){
        //return speed; //will make real speed later
        return 60*climbMotor.getRotorVelocity().getValueAsDouble();
    }


    public void SpinClimber(){ //will get changed to using position but is just spinning for now
        climberVelocity.withVelocity(speed/60);
        climbMotor.setControl(climberVelocity);
    }


    @Override
    public void periodic(){
        SpinClimber();
        SmartDashboard.putNumber("Climber Velocity:", getSpeed());
    }


}

