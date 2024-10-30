# Swerve Constants

This section will cover swerve constants you need to tune for your specific robot.

<code-block lang="java" src="TunerConstants.java" include-lines="6-13"/>

These are the PID and FF values for your swerve drive. These can be tuned by running SysID.

<code-block lang="java" src="TunerConstants.java" include-lines="24"/>

Leave as is. It will automatically adjust based on whether you have Pro Licenses or not.

<code-block lang="java" src="TunerConstants.java" include-lines="28"/>

One of the first values you should be looking to tune. This prevents your wheels from slipping information on how to tune for robot
can be found [here](https://v6.docs.ctr-electronics.com/en/stable/docs/hardware-reference/talonfx/improving-performance-with-current-limits.html#preventing-wheel-slip).
We will also be tuning values for the PathPlanner Swerve Setpoint Generator to help with wheel slipping and skidding.



