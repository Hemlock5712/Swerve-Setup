# TalonFX Swerve Tuning

<p>Here, you will find many tutorials on how to tune aspects of the swerve template.</p>

<warning>If you plan to tune it is recommended to find Steer FF and PID before finding Drive values.</warning>

## Feedforward Characterization
<p>The project includes default <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html#introduction-to-dc-motor-feedforward" ><format color="#fbc30c"> feedforward gains</format></a>. Provided are three SysID tests. You will change <code>m_sysIdRoutineToApply</code> inside of <code>Drive.java</code> to change the type of test.</p> Translation and Steer are needed. A Rotation test is also needed to find MOI.

<note>The following steps use buttons. Autonomous modes are also available.</note>
<p><format style="bold">Running FeedForward Test</format></p>
<list type="decimal">
<li>Place the robot in an open space.</li>
<li>Connect robot and controller to driver station.</li>
<li>Enable the robot.</li>
<li>Hold down <code>select</code>, and hold <code>y</code> for 4 seconds or longer.</li>
<li>Hold down <code>select</code>, and hold <code>x</code> for 4 seconds or longer.</li>
<li>Hold down <code>start</code>, and hold <code>y</code> for 4 seconds or longer.</li>
<li>Hold down <code>start</code>, and hold <code>x</code> for 4 seconds or longer.</li>
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
<li>Open your log</li>
<li>Drag <code>RealOutputs/SysIdRotation_State</code> to the Data Selector</li>
<li>Drag <code>Module0/DrivePosition</code>, <code>Module0/DriveVelocity</code>, <code>Module0/DriveAppliedVolts</code> to their respective locations</li>
<li>Change the Mechanism type to Simple</li>
<li>Change the Units to Radians</li>
<li>Load</li>
</list>

<p><format style="bold">Values to Use</format></p>
<list type="decimal">
<li>For Drive FeedForward - Use <code>kV</code> - Log <code>kA</code> for MOI</li>
<li>For Steer FeedForward - Use <code>kS</code> and <code>kV</code></li>
<li>For Rotation FeedForward - Log <code>kA</code></li>
</list>
We have found that these FeedForward values are accurate, but the PID is not always.

## Drive/Turn PID Tuning

<p>The project includes default gain, which can be found in the <code>steerGains</code> and <code>driveGains</code> configs in <code>TunerConstants.java</code>. These gains should be tuned for each robot.</p>
<note>More information about PID tuning can be found in the <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-feedforward.html#introduction-to-dc-motor-feedforward">WPILib documentation</a>.</note>

<p><format style="bold">PID Tuning Tips</format></p>
<list type="decimal">
<li>For Drive PID - Only use <code>kP</code></li>
<li>For Steer PID - Use <code>kP</code> and <code>kD</code></li>
</list>

## TorqueCurrent Control
<p>Congrats! You should have a fully tuned robot. For Phoenix Pro you can also attempt to use TorqueCurrent control, as described in the <a href="https://pro.docs.ctr-electronics.com/en/latest/docs/api-reference/device-specific/talonfx/talonfx-control-intro.html#torquecurrentfoc" ><format color="#fbc30c"> Phoenix documentation</format></a>. You can configure by changing the values of <code>kSteerClosedLoopOutput</code> and/or <code>kDriveClosedLoopOutput</code> in <code>TunerConstants.java</code> to <code>ClosedLoopOutputType.TorqueCurrentFOC</code>.</p>
<note>TorqueCurrent control requires different FF and PID values than Voltage control. We recommend TorqueCurrent only after the Voltage is working.</note>

## Wheel Radius Characterization

<p>The effective wheel radius of a robot tends to change over time as wheels are worn down, swapped, or compressed into the carpet. Having the wrong wheel size can have significant impacts on odometry accuracy. We recommend regularly recharacterizing wheel radius to combat these issues.</p>
<p>The project includes an automated wheel radius characterization routine, which only requires enough space for the robot to rotate in place.</p>
<list type="decimal">
<li>Place the robot on the carpet. Characterizing on a hard floor may produce errors in the measurement, as the robot's effective wheel radius is affected by carpet compression.</li>
<li>Select the "Drive Wheel Radius Characterization" auto routine.</li>
<li>Enable the robot in autonomous mode. The robot will slowly rotate in place.</li>
<li>Disable the robot after at least one full rotation.</li>
<li>Check the console output for the measured wheel radius, and copy the value to <code>kWheelRadius</code> in <code>TunerConstants.java</code>.</li>
</list>

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

<p>The project includes a built-in robot configuration for <a href="https://pathplanner.dev/robot-config.html"><format color="#fbc30c"> PathPlanner</format></a>, located in the constructor of <code>Drive.java</code>. You will want to update all values:</p>
<list type="bullet">
<li>Robot mass, MOI, and wheel coefficient as configured in <code>Constants.java</code></li>
<li>Drive PID constants as configured in <code>AutoBuilder</code></li>
<li>Turn PID constants as configured in <code>AutoBuilder</code></li>
</list>

## Swerve Setpoint Generator

<p>This project include skid and slip prevention using PathPlanners <a href="https://pathplanner.dev/pplib-swerve-setpoint-generator.html"><format color="#fbc30c">Swerve Setpoint Generator</format></a></p>
Once you set the PathPlanner Configurations, you can test this drive mode by pressing <code>X</code> button. Once you feel comfortable, replacing the default drive with this is recommended.
