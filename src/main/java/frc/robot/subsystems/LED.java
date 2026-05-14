package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLED.ColorOrder;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.util.Map;

public class LED extends SubsystemBase {
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;

  // Used to control each strip of LED

  // fix for actual leds once they are real
  private AddressableLEDBufferView m_bufferView;

  private int ledLength = 139; // need actual length once there are real LEDs

  // Our LED strip has a density of 60 LEDs per meter
  // private final Distance kLedSpacing = Meters.of(1 / 60.0);

  private Color royalRed = new Color("#C00000");
  private Color royalYellow = new Color("#FAD200");
  private Color RSLOrange = new Color("#bc3800");

  // Patterns
  private LEDPattern solidYellow = LEDPattern.solid(royalYellow);
  private LEDPattern solidOrange = LEDPattern.solid(RSLOrange);

  private LEDPattern solidRed = LEDPattern.solid(royalRed);
  private LEDPattern solidGreen = LEDPattern.solid(Color.kGreen);
  private LEDPattern off = LEDPattern.kOff;

  private Map<Number, Color> blinkingRedMaskSteps = Map.of(0, Color.kWhite, 0.32, Color.kBlack);
  private LEDPattern blinkingRed =
      solidRed
          .mask(
              LEDPattern.steps(blinkingRedMaskSteps)
                  .scrollAtRelativeSpeed(Percent.per(Second).of(175)))
          .reversed();

  private LEDPattern blinkingOrange = solidOrange.breathe(Seconds.of(.5));

  private LEDPattern currentPattern = solidOrange;

  public LED() {
    m_led =
        new AddressableLED(Constants.Ports.ledPort); // need to put port in constants once it exists
    m_ledBuffer = new AddressableLEDBuffer(ledLength);
    m_led.setColorOrder(ColorOrder.kRGB);

    m_led.setLength(m_ledBuffer.getLength());

    m_bufferView = m_ledBuffer.createView(0, ledLength - 1);

    m_led.start();
  }

  public void initialize() { // robot turns on
    currentPattern = solidOrange;
  }

  public void setYellow() {
    currentPattern = solidYellow;
  }

  public void scrollOrange() { // intake
    currentPattern = blinkingOrange;
  }

  public void setGreen() {
    currentPattern = solidGreen;
  }

  public void setRed() {
    currentPattern = solidRed;
  }

  public void scrollRed() { // outtake
    currentPattern = blinkingRed;
  }

  public void rainbow() { // intake stops
    currentPattern = LEDPattern.rainbow(255, 255);
  }

  public void off() {
    currentPattern = off;
  }

  private void setAllStrips(LEDPattern pattern) {
    pattern.applyTo(m_bufferView);
  }

  @Override
  public void periodic() {
    setAllStrips(currentPattern);
    m_led.setData(m_ledBuffer);
  }
}
