package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.AddressableLED.ColorOrder;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;

    //Used to control each strip of LED

    //fix for actual leds once they are real
    private AddressableLEDBufferView m_leftSide;
    private AddressableLEDBufferView m_leftMiddle;
    private AddressableLEDBufferView m_rightSide;
    private AddressableLEDBufferView m_rightMiddle;

    private int ledLength = 205; //need actual length once there are real LEDs

    // Our LED strip has a density of 60 LEDs per meter
    //private final Distance kLedSpacing = Meters.of(1 / 60.0);

    //TODO: Find indexes where LED strip breaks
    //private final int breakOne = 63;
    //private final int breakTwo = 104;
    //private final int breakThree = 161  ;

    private Color royalMaroon = new Color("#A00014");
    private Color royalRed = new Color("#C00000");
    private Color royalYellow = new Color("#FAD200");

    //Patterns
    private LEDPattern breathing = LEDPattern.gradient(GradientType.kDiscontinuous, royalMaroon, royalYellow).breathe(Seconds.of(2));
    private LEDPattern gradient = LEDPattern.gradient(GradientType.kDiscontinuous, royalMaroon, royalYellow)
    private LEDPattern solidYellow = LEDPattern.solid(royalYellow);
    private LEDPattern solidRed = LEDPattern.solid(royalRed);
    //private LEDPattern solidBlue = LEDPattern.solid(Color.kBlue);
    private LEDPattern off = LEDPattern.kOff;

    
    private LEDPattern currentPattern = breathing;

    public LED(){
        m_led = new AddressableLED(Constants.Ports.ledPort);//need to put port in constants once it exists
        m_ledBuffer = new AddressableLEDBuffer(ledLength);
        m_led.setColorOrder(ColorOrder.kRGB);

        m_led.setLength(m_ledBuffer.getLength());
        m_led.start();
    }

    public void initialize(){
        currentPattern = breathing;
    }

    public void still(){
        currentPattern = gradient;
    }

    public void setYellow(){
        currentPattern = solidYellow;
    }

    public void scrollYellow(){ //use to scroll in yellow when we intake
        //Map<Double, Color> maskSteps = Map.of(0, Color.kWhite, 0.5, Color.kBlack);
         LEDPattern maskSteps = LEDPattern.steps(Map.of(0, Color.kWhite, 0.5, Color.kBlack));
    
        LEDPattern mask =(maskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(0.5));
        currentPattern = solidYellow.mask(mask);
    }

    public void setRed(){
        currentPattern = solidRed;
    }

    public void scrollRed(){ //scroll out red on outtake
        //Map<Double, Color> 
        LEDPattern maskSteps = LEDPattern.steps(Map.of(0, Color.kWhite, 0.5, Color.kBlack));
    
        LEDPattern mask =(maskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(0.5));
        currentPattern = solidRed.mask(mask).reversed(); //dont kno which one to be reversed yet
    }

    public void rainbow(){
        currentPattern = LEDPattern.rainbow(255, 255);
    }


    public void off(){
        currentPattern = off;
    }

    private void setAllStrips(LEDPattern pattern){
        pattern.applyTo(m_leftSide);
        pattern.applyTo(m_leftMiddle);
        pattern.applyTo(m_rightMiddle);
        pattern.applyTo(m_rightSide);
    }

    @Override
    public void periodic(){
        setAllStrips(currentPattern);
    }

}
