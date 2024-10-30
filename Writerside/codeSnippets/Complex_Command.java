package frc.robot.subsystems.flywheel;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.measure.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.utils.loggable.LoggableTalonFX;
import frc.robot.utils.statemachine.StateMachine;
import java.util.function.Supplier;

public class Flywheel extends StateMachine<Flywheel.State> {

  private final LoggableTalonFX flywheel = new LoggableTalonFX(53, "flywheel");

  final MotionMagicVelocityVoltage request = new MotionMagicVelocityVoltage(0);

  TalonFXConfiguration config = new TalonFXConfiguration();

  private static final AngularVelocity AMP_SHOT =
      RotationsPerSecond.of(20.0);
  private static final AngularVelocity TRAP_SHOT =
      RotationsPerSecond.of(3.6);
  private static final AngularVelocity SUBWOOFER_SHOT =
      RotationsPerSecond.of(10.0);

  private static final AngularVelocity AMP_TOLERANCE =
      RotationsPerSecond.of(5.0);
  private static final AngularVelocity TRAP_TOLERANCE =
      RotationsPerSecond.of(1.0);
  private static final AngularVelocity SUBWOOFER_TOLERANCE =
      RotationsPerSecond.of(4.0);
  private static final AngularVelocity SPEAKER_TOLERANCE =
      RotationsPerSecond.of(0.75);

  private Supplier<AngularVelocity> speaker;

  public Flywheel(Supplier<AngularVelocity> speaker) {
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
    config.Slot0.kS = 0.366; // Add 0.366 V output to overcome static friction
    config.Slot0.kV = 8.0 / 54.154; // When applying 8V spins at 54.154
    config.Slot0.kA = 0.0;
    config.Slot0.kP = 0.9;
    config.Slot0.kI = 0.0;
    config.Slot0.kD = 0.001;

    // set Motion Magic Velocity settings
    var motionMagicConfigs = config.MotionMagic;
    motionMagicConfigs.MotionMagicAcceleration = 50.0;
    motionMagicConfigs.MotionMagicJerk = 500;

    boolean statusOK = flywheel.getConfigurator().apply(config, 0.1) ==
        StatusCode.OK;

    BaseStatusSignal.setUpdateFrequencyForAll(
        50.0,
        flywheel.getPosition(),
        flywheel.getVelocity(),
        flywheel.getMotorVoltage(),
        flywheel.getTorqueCurrent());
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

    registerStateCommand(
        State.TRAP,
        new ParallelCommandGroup(
            new RunCommand(() -> setFlywheelTarget(TRAP_SHOT)),
            atSpeedCommand(() -> TRAP_SHOT, TRAP_TOLERANCE)));

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

  public void setFlywheelTarget(AngularVelocity velocity) {
    flywheel.setControl(request.withVelocity(velocity));
  }

  private Command atSpeedCommand(
      Supplier<AngularVelocity> speedProvider,
      AngularVelocity accuracy) {
    return new RunCommand(
        () -> {
          AngularVelocity diff = flywheel.getVelocity()
              .getValue()
              .minus(speedProvider.get());
          if (diff.magnitude() < accuracy.magnitude()) {
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