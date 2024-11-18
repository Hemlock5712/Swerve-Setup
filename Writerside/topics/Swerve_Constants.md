# Swerve Constants

For setting up your robot post steps can be found [here](https://docs.advantagekit.org/example-projects/talonfx-swerve-template/) 

This section is focused on pointing out swerve constants to tune.

## TunerConstants {collapsible="true"}

### PID and FeedForward

These are the swerve and steer PID and FF values. We will be tuning FF values using SysID. PID values should be good if using `Voltage` control.

<code-block lang="java" src="TunerConstants.java" include-lines="6-13"/>

### Closed Loop Type

These values control how you are controlling your motor. More experienced will opt to use `TorqueCurrentFOC` however this will require you to re-tune PID and FF values.

<code-block lang="java" src="TunerConstants.java" include-lines="17,20"/>

### Slip Current

This prevents your wheels from slipping information on how to tune for robot
can be found [here](https://v6.docs.ctr-electronics.com/en/stable/docs/hardware-reference/talonfx/improving-performance-with-current-limits.html#preventing-wheel-slip).
This value will also be used with PathPlanner this year to generate paths. If you are experiencing brown-out issues you may want to adjust current limits also but this is only current limit for setup we will be tuning.

<code-block lang="java" src="TunerConstants.java" include-lines="28"/>

### Max Speed at 12 Volts

This is the most important value as this also directly affect your acceleration when using PathPlanner Swerve Setpoint Generator


<code-block lang="java" src="TunerConstants.java" include-lines="57"/>


## Drive (PathPlanner) {collapsible="true"}

These are the following values you will need to tune.

<code-block lang="java" src="Drive.java" include-lines="75-77"/>

* `ROBOT_MASS_KG` - Should be weight of robot with bumpers and battery.
* `ROBOT_MOI` - Can be found using this formula found [here](https://sleipnirgroup.github.io/Choreo/usage/estimating-moi/#system-identification-methods).
  * To find `kA` values you will need to run two SysID characterizations. You will need to change the `runCharacterization` inside `Modules.java`
<br/>
<compare type="top-bottom" first-title="kA Linear" second-title="kA Angular">
<code-block lang="java" src="Module.java" include-lines="1-5"/>
<code-block lang="java" src="Module.java" include-lines="7-11"/>
</compare>
* `WHEEL_COF` - This can be found several ways. Approximation for common wheels
  * Black Nitrile - 1.5
  * Colson - 0.9 
  * Grip Lock - 1.9







