# Swerve Constants

For setting up your robot post steps can be found [here](https://docs.advantagekit.org/example-projects/talonfx-swerve-template/) 

An AdvantageScope layout that follows steps above can be found in `dashboard` folder. For configuring dashboard see the [dashboard page](Dashboards.md)
This section is focused on pointing out swerve constants to tune.

## TunerConstants {collapsible="true"}

### PID and FeedForward

These are the swerve and steer PID and FF values. We will be tuning FF values using SysID. PID values should be good if using `Voltage` control.

<code-block lang="java" src="TunerConstants.java" include-lines="6-13"/>

<tip>
By default, SysID dynamic step may accelerate your robot too quickly. This can cause your robot to pop a wheelie or brown out.
When running SysID we recommend changing the dynamic step based on test.
<list>
<li>Translation and Rotation = Volts.of(4)
</li>
<li>Steer = Volts.of(7)
</li>
</list>
</tip>

For code changes need to run these test please see [here](https://docs.advantagekit.org/example-projects/talonfx-swerve-template/#feedforward-characterization)  

### Closed Loop Type

These values control how you are controlling your motor. More experienced will opt to use `TorqueCurrentFOC` however this will require you to re-tune PID and FF values.

<code-block lang="java" src="TunerConstants.java" include-lines="17,20"/>

### Slip Current

This prevents your wheels from slipping. Information on how to tune can be found [here](https://v6.docs.ctr-electronics.com/en/stable/docs/hardware-reference/talonfx/improving-performance-with-current-limits.html#preventing-wheel-slip).
This value will also be used with PathPlanner this year to generate paths. If you are experiencing brown-out issues you may want to adjust current limits also but this is only current limit for setup we will be tuning.

<code-block lang="java" src="TunerConstants.java" include-lines="28"/>

### Max Speed at 12 Volts

<note>This is the most important value as this also directly affect your acceleration when using PathPlanner Swerve Setpoint Generator</note>

Max speed your robot can go at 12 Volts in meters per second.

<code-block lang="java" src="TunerConstants.java" include-lines="57"/>







