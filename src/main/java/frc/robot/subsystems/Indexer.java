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
    private TalonFX toBackMotor; //i need to look at the CAD tp see if these are okish names
    private TalonFX toSideMotor;
    private TalonFX toFlyMotor;

    public Indexer(){

        var talonFXConfigs = new TalonFXConfiguration(); //idk what this actually does

        var slot0Configs = talonFXConfigs.Slot0; //again idk what this does but in last years code it appears too be setting up the kracken
        slot0Configs.kP = 0.01;

        toBackMotor.getConfigurator().apply(talonFXConfigs);
        toBackMotor.setNeutralMode(NeutralModeValue.Brake);
        toSideMotor.getConfigurator().apply(talonFXConfigs);
        toSideMotor.setNeutralMode(NeutralModeValue.Brake);
        toFlyMotor.getConfigurator().apply(talonFXConfigs);
        toFlyMotor.setNeutralMode(NeutralModeValue.Brake);
    }

    public TalonFX getToBackMotor(){
        return toBackMotor;
    }

    public TalonFX getToSideMotor(){
        return toSideMotor;
    }

    public TalonFX getToFlyMotor(){
        return toFlyMotor;
    }
}
