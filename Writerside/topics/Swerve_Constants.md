# Swerve Constants

In this section, we will cover swerve constants and their meaning.

<code-block lang="java" src="TunerConstants.java" include-lines="6-13"/>

These are the PID and FF values for your swerve drive. These can be tuned by running SysID.

<code-block lang="java" src="TunerConstants.java" include-lines="15-20"/>

You will want to keep these values as is.
Changing these to TorqueCurrent makes you control the acceleration of the motors, which results in the robot being un-drivable.

<code-block lang="java" src="TunerConstants.java" include-lines="24"/>

Leave as is. It will automatically adjust based on whether you have Pro Licenses or not.

<code-block lang="java" src="TunerConstants.java" include-lines="28"/>

One of the first values you should be looking to tune. This prevents your wheels from slipping information on how to tune for robot
can be found [here](https://v6.docs.ctr-electronics.com/en/stable/docs/hardware-reference/talonfx/improving-performance-with-current-limits.html#preventing-wheel-slip).
We will also be tuning values for the PathPlanner Swerve Setpoint Generator to help with wheel slipping and skidding.



