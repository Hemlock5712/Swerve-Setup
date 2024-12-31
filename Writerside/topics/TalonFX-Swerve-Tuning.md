# TalonFX Swerve Tuning

<p>Below you will find many tutorials on how to tune aspects of the swerve template.</p>

## Torque-Current Control
<p>The project defaults to voltage control for both the drive and turn motors. Phoenix Pro subscribers can optionally switch to torque-current control, as described in the <a href="https://pro.docs.ctr-electronics.com/en/latest/docs/api-reference/device-specific/talonfx/talonfx-control-intro.html#torquecurrentfoc" ><format color="#fbc30c"> Phoenix documentation</format></a>. This can be configured by changing the values of <code>kSteerClosedLoopOutput</code> and/or <code>kDriveClosedLoopOutput</code> in <code>TunerConstants.java</code> to <code>ClosedLoopOutputType.TorqueCurrentFOC</code>.</p>
<note>Torque-current control requires different gains than voltage control. We recommend following the steps below to tune feedforward and PID gains.</note>

## Feedforward Characterization
<p>The project includes default <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html#introduction-to-dc-motor-feedforward" ><format color="#fbc30c"> feedforward gains</format></a> for velocity control of the drive motors (<code>kS</code> and <code>kV</code>), acceleration control of the drive motors (<code>kA</code>), and velocity control of the turn motors (<code>kS</code> and <code>kV</code>).</p>
<note>The drive <code>kS</code> and <code>kV</code> gains should always be characterized (as described below). The drive/turn <code>kA</code> gains and turn <code>kS</code> and <code>kV</code> gains are unnecessary in most cases, but can be tuned by advanced users.</note>
<p>The project includes a simple feedforward routine that can be used to quickly measure the drive <code>kS</code> and <code>kV</code> values using <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/system-identification/index.html" ><format color="#fbc30c"> SysID</format>:</a></p>

<p><format style="bold">Running The Test</format></p>
<list type="decimal">
<li>Tune turning PID gains as described here.</li>
<li>Place the robot in an open space.</li>
<li>Connect robot and controller to driver station.</li>
<li>Enable the robot.</li>
<li>Hold down <code>select</code>, and hold <code>y</code> for 4 seconds.</li>
<li>Hold down <code>select</code>, and hold <code>x</code> for 4 seconds.</li>
<li>Hold down <code>start</code>, and hold <code>y</code> for 4 seconds.</li>
<li>Hold down <code>start</code>, and hold <code>x</code> for 4 seconds.</li>
<li>Disable the robot.</li>
</list>

<p><format style="bold">Saving The Test</format></p>
<list type="decimal">
<li>Go to <code>File</code> > <code>Download Logs...</code></li>
<li>Select the log that is most recent.</li>
<li>Go to <code>File</code> > <code>Export Data...</code></li>
<li>Set the format to <code>WPILOG</code> and the timestamps to <code>AdvantageKit Cycles</code>. For large log files, enter the prefixes for only the fields and tables necessary for SysId analysis (see the <a href="https://docs.advantagescope.org/more-features/export#options" ><format color="#fbc30c"> export options</format>:</a> documentation for details).</li><li>Click the save icon and choose a location to save the log.</li>
</list>

<p><format style="bold">Running SysID</format></p>
<list type="decimal">
<li>Open SysID in the "2025 WPILib Tools" folder on your desktop.</li>
<li>I forget</li>
</list>

## Wheel Radius Characterization
<p>The effective wheel radius of a robot tends to change over time as wheels are worn down, swapped, or compress into the carpet. This can have significant impacts on odometry accuracy. We recommend regularly recharacterizing wheel radius to combat these issues.</p>
<p>The project includes an automated wheel radius characterization routine, which only requires enough space for the robot to rotate in place.</p>
<list type="decimal">
<li>Place the robot on carpet. Characterizing on a hard floor may produce errors in the measurement, as the robot's effective wheel radius is affected by carpet compression.</li>
<li>Select the "Drive Wheel Radius Characterization" auto routine.</li>
<li>Enable the robot in autonomous mode. The robot will slowly rotate in place.</li>
<li>Disable the robot after at least one full rotation.</li>
<li>Check the console output for the measured wheel radius, and copy the value to <code>kWheelRadius</code> in <code>TunerConstants.java</code>.</li>
</list>

## Drive/Turn PID Tuning

<p>The project includes default gains for the drive velocity PID controllers and turn position PID controllers, which can be found in the <code>steerGains</code> and <code>driveGains</code> configs in <code>TunerConstants.java</code>. These gains should be tuned for each robot.</p>
<note>More information about PID tuning can be found in the <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html#introduction-to-dc-motor-feedforward">WPILib documentation</a>.</note>

<p>We recommend using AdvantageScope to plot the measured and setpoint values while tuning. Measured values are published to the <code>/RealOutputs/SwerveStates/Measured</code> field and setpoint values are published to the <code>/RealOutputs/SwerveStates/SetpointsOptimized</code> field.</p>

<tip>The PID gains used in simulation can be tuned using the same method. Simulation gains are stored in <code>ModuleIOSim.java</code> instead of <code>TunerConstants.java</code>.</tip>

## Max Speed Measurement

<p>The effective maximum speed of a robot is typically slightly less than the theoretical max speed based on motor free speed and gearing. To ensure that the robot remains controllable at high speeds, we recommend measuring the effective maximum speed of the robot.</p>
<list type="decimal">
<li>Set <code>kSpeedAt12Volts</code> in <code>TunerConstants.java</code> to the theoretical max speed of the robot based on motor free speed and gearing. This value can typically be found on the product page for your chosen swerve modules.</li>
<li>Place the robot in an open space.</li>
<li>Plot the measured robot speed in AdvantageScope using the <code>/RealOutputs/SwerveChassisSpeeds/Measured</code> field.</li>
<li>In teleop mode, drive forwards at full speed until the robot velocity is no longer increasing.</li>
<li>Record the maximum velocity achieved and update the value of <code>kSpeedAt12Volts</code>.</li>
</list>

## Slip Current Measurement

<p>The value of <code>kSlipCurrent</code> can be tuned to avoid slipping the wheels.</p>
<list type="decimal">
<li>Place the robot against a solid wall.</li>
<li>Using AdvantageScope, plot the current of a drive motor from the <code>/Drive/Module.../DriveCurrentAmps</code> key, and the velocity of the motor from the <code>/Drive/Module.../DriveVelocityRadPerSec</code> key.</li>
<li>Accelerate forwards until the drive velocity increases (the wheel slips). Note the current at this time.</li>
<li>Update the value of <code>kSlipCurrent</code> to this value.</li>
</list>

## PathPlanner Configuration

<p>The project includes a built-in configuration for <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html#introduction-to-dc-motor-feedforward" ><format color="#fbc30c"> PathPlanner</format></a>, located in the constructor of <code>Drive.java</code>. You may wish to manually adjust the following values:</p>
<list type="bullet">
<li>Robot mass, MOI, and wheel coefficient as configured in <code>Constants.java</code></li>
<li>Drive PID constants as configured in <code>AutoBuilder</code></li>
<li>Turn PID constants as configured in <code>AutoBuilder</code></li>
</list>
