public class Arm extends SubsystemBase {
  private static final double kGearRatio = 10.0;
  private final LoggableTalonFX armMotor = new LoggableTalonFX(15, "arm");
  private final LoggableCANcoder armEncoder = new LoggableCANcoder(16, "arm");
  private TalonFXConfiguration armConfig = new TalonFXConfiguration();
  private final SingleJointedArmSim m_motorSimModel =
      new SingleJointedArmSim(
          DCMotor.getKrakenX60Foc(2),
          kGearRatio,
          1.06328,
          1,
          -3.14 / 2,
          3.14 / 2,
          true,
          Units.degreesToRadians(0.0));

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

  @Override
  public void simulationPeriodic() {
    m_motorSimModel.setInputVoltage(armMotor.getSimState().getMotorVoltage());
    m_motorSimModel.update(0.020);

    final double position_rot =
        Units.radiansToRotations(m_motorSimModel.getAngleRads()) * kGearRatio;
    final double velocity_rps =
        Units.radiansToRotations(m_motorSimModel.getVelocityRadPerSec()) * kGearRatio;

    armMotor.getSimState().setRawRotorPosition(position_rot);
    armMotor.getSimState().setRotorVelocity(velocity_rps);

    armMotor.getSimState().setSupplyVoltage(12 - armMotor.getSimState().getSupplyCurrent() * 0.002);

    armEncoder.getSimState().setRawPosition(position_rot / kGearRatio);
    armEncoder.getSimState().setVelocity(velocity_rps / kGearRatio);
  }

  public void setSpeed(double speed) {
    armMotor.set(speed);
  }
}