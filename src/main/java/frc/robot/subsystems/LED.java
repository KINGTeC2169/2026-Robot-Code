package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLED.ColorOrder;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;

    //Used to control each strip of LED

    //fix for actual leds once they are real
    private AddressableLEDBufferView m_bufferView;

    private int ledLength = 139; //need actual length once there are real LEDs

    // Our LED strip has a density of 60 LEDs per meter
    //private final Distance kLedSpacing = Meters.of(1 / 60.0);

    private Color royalMaroon = new Color("#A00014");
    private Color royalRed = new Color("#C00000");
    private Color royalYellow = new Color("#FAD200");

    //Patterns
    private LEDPattern breathing = LEDPattern.gradient(GradientType.kDiscontinuous, royalRed, royalYellow).breathe(Seconds.of(2));
    private LEDPattern gradient = LEDPattern.gradient(GradientType.kDiscontinuous, royalRed, royalYellow);
    private LEDPattern solidYellow = LEDPattern.solid(royalYellow);
    
    private LEDPattern solidRed = LEDPattern.solid(royalRed);
    private LEDPattern solidGreen = LEDPattern.solid(Color.kLimeGreen);
    private LEDPattern off = LEDPattern.kOff;

    private Map<Number, Color> blinkingRedMaskSteps = Map.of(0, Color.kWhite, 0.32, Color.kBlack);
    private LEDPattern blinkingRed = solidRed.mask(LEDPattern.steps(blinkingRedMaskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(175))).reversed();

    private Map<Number, Color> blinkingYellowMaskSteps = Map.of(0, Color.kWhite, 0.32, Color.kBlack);
    private LEDPattern blinkingYellow = solidYellow.mask(LEDPattern.steps(blinkingYellowMaskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(175)));

    
    private LEDPattern currentPattern = breathing;

    public LED(){
        m_led = new AddressableLED(Constants.Ports.ledPort);//need to put port in constants once it exists
        m_ledBuffer = new AddressableLEDBuffer(ledLength);
        m_led.setColorOrder(ColorOrder.kRGB);

        m_led.setLength(m_ledBuffer.getLength());

        m_bufferView = m_ledBuffer.createView(0, ledLength - 1);
        
        m_led.start();
    }

    public void initialize(){ //robot turns on
        currentPattern = breathing;
    }

    public void still(){ //robot enabled
        currentPattern = gradient;
    }

    public void setYellow(){
        currentPattern = solidYellow;
    }

    public void scrollOrange(){ //intake
        //Map<Double, Color> maskSteps = Map.of(0, Color.kWhite, 0.5, Color.kBlack);
         /*LEDPattern maskSteps = LEDPattern.steps(Map.of(0, Color.kWhite, 0.5, Color.kBlack));
    
        LEDPattern mask =(maskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(0.5));
        currentPattern = solidYellow.mask(mask);*/
        currentPattern= blinkingYellow;
    }

    public void setGreen(){
            currentPattern=solidGreen;
    }

    public void setRed(){
        currentPattern = solidRed;
    }

    public void scrollRed(){ //outtake
        //Map<Double, Color> 
        /*LEDPattern maskSteps = LEDPattern.steps(Map.of(0, Color.kWhite, 0.5, Color.kBlack));
        LEDPattern mask =(maskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(0.5));
        currentPattern = solidRed.mask(mask).reversed(); //dont kno which one to be reversed yet*/
        currentPattern = blinkingRed;
    }

    public void rainbow(){//intake stops
        currentPattern = LEDPattern.rainbow(255, 255);
    }


    public void off(){
        currentPattern = off;
    }

    private void setAllStrips(LEDPattern pattern){
        pattern.applyTo(m_bufferView);
    }

    @Override
    public void periodic(){
        setAllStrips(currentPattern);
        m_led.setData(m_ledBuffer);
    }

}
