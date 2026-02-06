package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
//import com.revrobotics.Rev2mDistanceSensor;
//import com.revrobotics.Rev2mDistanceSensor.Port;
//import com.revrobotics.Rev2mDistanceSensor.RangeProfile;
//import com.revrobotics.Rev2mDistanceSensor.Unit;
import com.revrobotics.spark.config.SparkMaxConfig;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.ctre.phoenix6.controls.VelocityVoltage;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import frc.robot.Constants.IntakeConstants;

//I copied and pasted all of the imports from last years code and commented out the stuff with errors so hopefully thats right?
//im also just kinda winging this since i dont really know what im doing...


public class Indexer extends SubsystemBase{
    private TalonFX toBackMotor; 
    private TalonFX toSideMotor;
    private TalonFX preShootMotor; //this one is probably supposed to be different than the other 2 but i just did it the same for now
    
    private VelocityVoltage indexVelocity = new VelocityVoltage(0);
    private double speed;
    public Indexer(){

        var talonFXConfigs = new TalonFXConfiguration(); //idk what this actually does

        var slot0Configs = talonFXConfigs.Slot0; //again idk what this does but in last years code it appears too be setting up the kraken
        slot0Configs.kP = 0.01;

        toBackMotor.getConfigurator().apply(talonFXConfigs);
        toBackMotor.setNeutralMode(NeutralModeValue.Brake);
        toSideMotor.getConfigurator().apply(talonFXConfigs);
        toSideMotor.setNeutralMode(NeutralModeValue.Brake);
        preShootMotor.getConfigurator().apply(talonFXConfigs);
        preShootMotor.setNeutralMode(NeutralModeValue.Brake);
    }

    public TalonFX getToBackMotor(){
        return toBackMotor;
    }

    public TalonFX getToSideMotor(){
        return toSideMotor;
    }

    public TalonFX getToFlyMotor(){
        return preShootMotor;
    }

    public void setVoltageMotors(double volts){
        toBackMotor.setVoltage(volts);
        toSideMotor.setVoltage(volts);
        preShootMotor.setVoltage(volts);
    }
    public void setSpeed(double rpm){
        speed = rpm;
    }   

    public double getSpeed(){//should probably make seperate ones for the different motors
        //return speed;
        return 60 * toBackMotor.getRotorVelocity().getValueAsDouble();
    }
    public void Spin(){
        indexVelocity.withVelocity(speed/60);
        toBackMotor.setControl(indexVelocity);
        toSideMotor.setControl(indexVelocity);
        preShootMotor.setControl(indexVelocity);

        
    }

    @Override //again i dont realy know what this does but there was onein my ftc code to so like it makes a little sense
    public void periodic(){
        Spin();

        SmartDashboard.putNumber("Indexer speed", getSpeed());

    }
}
