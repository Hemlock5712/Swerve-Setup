public class Arm extends SubsystemBase {
  private final LoggableTalonFX armMotor = new LoggableTalonFX(15, "arm");
  private final LoggableCANcoder armEncoder = new LoggableCANcoder(16, "arm");
  private TalonFXConfiguration armConfig = new TalonFXConfiguration();

  /** Creates a new Arm. */
  public Arm() {
    armConfig.Feedback.FeedbackRemoteSensorID = armEncoder.getDeviceID();
    armConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
    armConfig.Feedback.SensorToMechanismRatio = 1;
    armMotor.getConfigurator().apply(armConfig, 1);
    BaseStatusSignal.setUpdateFrequencyForAll(
        50,
        armMotor.getVelocity(),
        armMotor.getPosition(),
        armEncoder.getVelocity(),
        armEncoder.getAbsolutePosition());
  }

  @Override
  public void periodic() {}

  /** This will most likely be replaced with PID */
  public void setSpeed(double speed) {
    armMotor.set(speed);
  }
}