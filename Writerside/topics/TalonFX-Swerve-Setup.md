# TalonFX Swerve Setup

<note>The swerve project folder includes a predefined AdvantageScope layout with tabs for each setup and tuning step described below. To open it, click <code>File</code> > <code>Import Layout...</code> in the tab bar of AdvantageScope and select the file <code>AdvantageScope Swerve Calibration.json</code> in the swerve project folder.</note>
<list type="decimal" start="1">
    <li>Download and install everything linked below
        <list type="alpha-lower">
            <li><a href="https://github.com/Hemlock5712/2025SwerveTemplate">Swerve Template</a></li>
            <li><a href="https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html">WPILib Tools</a></li>
            <li><a href="https://git-scm.com/downloads/win">Git</a></li>
        </list>
    </li>
    <li>Open the template in VSCode.</li>
    <li>Click the WPILib icon in the VSCode toolbar and find the task <code>WPILib: Set Team Number</code>. Enter your team number and press enter.</li>
    <li>Follow the instructions in the Phoenix documentation for the Tuner X Swerve Project Generator.</li>
    <li>On the final screen in Tuner X, choose "Generate only TunerConstants" and overwrite the file located at <code>src/main/java/frc/robot/generated/TunerConstants.java</code>.</li>
    <li>Inside of <code>TunerConstants.java</code>
        <list>
            <li>Replace the <a href="https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/1db713d75b08a4315c9273cebf5b5e6a130ed3f7/java/SwerveWithPathPlanner/src/main/java/frc/robot/generated/TunerConstants.java#L18">last import</a>  marked as an error with <code-block lang="java">import frc.robot.subsystems.drive.DriveIOCTRE;</code-block></li>
            <li>Replace the <a href="https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/1db713d75b08a4315c9273cebf5b5e6a130ed3f7/java/SwerveWithPathPlanner/src/main/java/frc/robot/generated/TunerConstants.java#L167-L175">last method</a> marked as an error with <code-block lang="java">
            /** Creates a DriveIOCTRE instance. This should only be called once in your robot program,. */
            public static DriveIOCTRE createDrivetrain() {
                return new DriveIOCTRE(DrivetrainConstants, FrontLeft, FrontRight, BackLeft, BackRight);
                }</code-block></li>
</list>
    </li>
</list>