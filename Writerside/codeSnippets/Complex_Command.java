public class Flywheel extends StateMachine<Flywheel.State> {

    private final TalonFX flywheel = new TalonFX(53);

    private final StatusSignal<Measure<Angle>> flywheelPosition = flywheel.getPosition();
    private final StatusSignal<Measure<Velocity<Angle>>> flywheelVelocity = flywheel.getVelocity();
    private final StatusSignal<Measure<Voltage>> flywheelAppliedVolts = flywheel.getMotorVoltage();
    private final StatusSignal<Measure<Current>> flywheelCurrent = flywheel.getSupplyCurrent();

    final MotionMagicVelocityVoltage request = new MotionMagicVelocityVoltage(0);

    TalonFXConfiguration config = new TalonFXConfiguration();

    private static final Measure<Velocity<Angle>> AMP_SHOT = RotationsPerSecond.of(20.0);
    private static final Measure<Velocity<Angle>> TRAP_SHOT = RotationsPerSecond.of(3.6);
    private static final Measure<Velocity<Angle>> SUBWOOFER_SHOT = RotationsPerSecond.of(10.0);

    private static final Measure<Velocity<Angle>> AMP_TOLERANCE = RotationsPerSecond.of(5.0);
    private static final Measure<Velocity<Angle>> TRAP_TOLERANCE = RotationsPerSecond.of(1.0);
    private static final Measure<Velocity<Angle>> SUBWOOFER_TOLERANCE = RotationsPerSecond.of(4.0);
    private static final Measure<Velocity<Angle>> SPEAKER_TOLERANCE = RotationsPerSecond.of(0.75);

    private Supplier<Measure<Velocity<Angle>>> speaker;

    public Flywheel(Supplier<Measure<Velocity<Angle>>> speaker) {
      super("Flywheel", State.UNDETERMINED, State.class);
      this.speaker = speaker;
      registerStateCommands();
      registerTransitions();
      configureMotors();
    }

    private void configureMotors() {
      config.CurrentLimits.SupplyCurrentLimit = 40.0;
      config.CurrentLimits.SupplyCurrentLimitEnable = true;
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
      config.Slot0.kS = 0.366; // Add 0.31 V output to overcome static friction
      config.Slot0.kV = 8.0 / 54.154; // 7.25 / 50.0
      config.Slot0.kA = 0.0; // An acceleration of 1 rps/s requires 0.01 V output
      config.Slot0.kP = 0.9; // An error of 1 rps results in 0.11 V output
      config.Slot0.kI = 0.0; // no output for integrated error
      config.Slot0.kD = 0.001; // no output for error derivative

      // set Motion Magic Velocity settings
      var motionMagicConfigs = config.MotionMagic;
      // Some value that is achievable
      motionMagicConfigs.MotionMagicAcceleration =
          50.0; // Target acceleration of 400 rps/s (0.25 seconds to max)
      motionMagicConfigs.MotionMagicJerk = 500; // Target jerk of 4000 rps/s/s (0.1 seconds)

      for (int i = 0; i < 4; i++) {
        boolean statusOK = flywheel.getConfigurator().apply(config, 0.1) == StatusCode.OK;
        if (statusOK) break;
      }
      BaseStatusSignal.setUpdateFrequencyForAll(
          50.0, flywheelPosition, flywheelVelocity, flywheelAppliedVolts, flywheelCurrent);
    }

    private void registerStateCommands() {
      registerStateCommand(
          State.AMP,
          new ParallelCommandGroup(
              new InstantCommand(() -> setFlywheelTarget(AMP_SHOT)),
              atSpeedCommand(() -> AMP_SHOT, AMP_TOLERANCE)));

      registerStateCommand(
          State.SPEAKER,
          new ParallelCommandGroup(
              new RunCommand(() -> setFlywheelTarget(speaker.get())),
              atSpeedCommand(speaker, SPEAKER_TOLERANCE)));

      registerStateCommand(
          State.SUBWOOFER,
          new ParallelCommandGroup(
              new RunCommand(() -> setFlywheelTarget(SUBWOOFER_SHOT)),
              atSpeedCommand(() -> SUBWOOFER_SHOT, SUBWOOFER_TOLERANCE)));

      registerStateCommand(State.IDLE, this::stop);
      registerStateCommand(State.TRAP, () -> setFlywheelTarget(TRAP_SHOT));
    }

    private void registerTransitions() {
      addOmniTransition(State.IDLE);
      addOmniTransition(State.AMP);
      addOmniTransition(State.SPEAKER);
      addOmniTransition(State.TRAP);
      addOmniTransition(State.SUBWOOFER);
    }

    public void setFlywheelTarget(Measure<Velocity<Angle>> velocity) {
      flywheel.setControl(request.withVelocity(velocity));
    }

    private Command atSpeedCommand(
        Supplier<Measure<Velocity<Angle>>> speedProvider, Measure<Velocity<Angle>> accuracy) {
      return new RunCommand(
          () -> {
            if (flywheel.getVelocity().getValue().minus(speedProvider.get()).magnitude()
                < accuracy.magnitude()) {
              setFlag(State.AT_SPEED);
            } else {
              clearFlag(State.AT_SPEED);
            }
          });
    }

    public void stop() {
      flywheel.stopMotor();
    }

    public enum State {
      UNDETERMINED,
      IDLE,
      SPEAKER,
      AMP,
      TRAP,
      SUBWOOFER,

      // flags
      AT_SPEED
    }

    @Override
    protected void determineSelf() {
      setState(State.IDLE);
    }
  }