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
import frc.robot.Constants.Ports;
//import frc.robot.Constants.IntakeConstants;

//I copied and pasted all of the imports from last years code and commented out the stuff with errors so hopefully thats right?
//im also just kinda winging this since i dont really know what im doing...


public class Indexer extends SubsystemBase{
    private TalonFX indexMotor; 
    private TalonFX feeder; 
    private double indexSpeed;
    private double feederSpeed;


    public Indexer(){
        indexMotor = new TalonFX(Ports.indexMotor);
        feeder = new TalonFX(Ports.feeder);
    }

    
    public TalonFX getindexMotor(){
        return indexMotor;
    }

    public TalonFX getfeeder(){
        return feeder;
    }
    
    public void setIndexerVoltage(double volts){
        indexMotor.setVoltage(volts);
    }

    public void setFeederVoltage(double voltage){
        feederSpeed = voltage;
    }

    public double getfeederRPM(){
        return 60 * feeder.getRotorVelocity().getValueAsDouble();
    }

    public double getIndexerRPM(){//should probably make seperate ones for the different motors
        //return speed;
        return 60 * indexMotor.getRotorVelocity().getValueAsDouble();
    }

    public void StopIndexer(){
        indexMotor.stopMotor();
        feeder.stopMotor();
    }

    public void StopPreShoot(){
         feeder.stopMotor();
    }

    public void StopIndex(){
        indexMotor.stopMotor();
    }


    @Override //again i dont realy know what this does but there was onein my ftc code to so like it makes a little sense
    public void periodic(){
    }
}