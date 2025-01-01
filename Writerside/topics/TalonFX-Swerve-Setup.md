# TalonFX Swerve Setup

<p>Here, you will learn how to set up your CTRE Swerve Template.</p>

<warning>CTRE only permits the swerve project generator on swerve robots with exclusively CTRE hardware (eight TalonFX controllers, four CANcoders, and a Pigeon 2.0)</warning>
<list type="decimal" start="1">
    <li>Download and install everything linked below
        <list type="alpha-lower">
            <li><a href="https://github.com/Hemlock5712/2025SwerveTemplate">Swerve Template</a></li>
            <li><a href="https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/frc-game-tools.html">Game Tools</a></li>
            <li><a href="https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html">WPILib</a></li>
            <li><a href="https://git-scm.com/downloads/win">Git</a></li>
            <li><a href="https://github.com/mjansen4857/pathplanner/releases">PathPlanner</a></li>
            <li><a href="https://github.com/CrossTheRoadElec/Phoenix-Releases/releases">Tuner X - (Preview if using Beta)</a></li>
        </list>
    </li>
    <li>Open the template in VSCode.</li>
    <li>Click the WPILib icon in the VSCode toolbar and find the task <code>WPILib: Set Team Number</code>. Enter your team number and press enter.</li>
    <li>Connect to Robot.</li>
    <li>Open Tuner X (if this is a newly flashed robot, you will want to run a temporary diagnostic server found in <code>Settings/FRC Advanced</code>).</li>
    <li>Follow the swerve setup guide under the Mechanisms tab.</li>
    <li>On the final screen in Tuner X, choose "Generate only TunerConstants" and overwrite the file at <code>src/main/java/frc/robot/generated/TunerConstants.java.</code></li>
    <li>Inside of <code>TunerConstants.java</code>.
        <list>
            <li>Replace the <a href="https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/1db713d75b08a4315c9273cebf5b5e6a130ed3f7/java/SwerveWithPathPlanner/src/main/java/frc/robot/generated/TunerConstants.java#L18">last import</a>  marked as an error with <code-block lang="java">import frc.robot.subsystems.drive.DriveIOCTRE;</code-block></li>
            <li>Replace the <a href="https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/1db713d75b08a4315c9273cebf5b5e6a130ed3f7/java/SwerveWithPathPlanner/src/main/java/frc/robot/generated/TunerConstants.java#L167-L175">last method</a> marked as an error with 
            <code-block lang="java">
            /** Creates a DriveIOCTRE instance.*/
            public static DriveIOCTRE createDrivetrain() {
                return new DriveIOCTRE(
                    DrivetrainConstants, 
                    FrontLeft, FrontRight, BackLeft, BackRight
                );
            }</code-block>
            </li>
    </list>
    </li>
</list>

### TalonFX Swerve Tuning
Congratulations! Your robot should be completely drivable! Next, follow the instructions on tuning your swerve drive.