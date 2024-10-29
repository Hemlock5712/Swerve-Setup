# Swerve Constants

In this portion we will be covering constants and what they mean.

<code-block lang="java" src="TunerConstants.java" include-lines="6-13"/>

These are your PID and FF values for your swerve drive. These can be tuned by running SysID

<code-block lang="java" src="TunerConstants.java" include-lines="15-20"/>

These values you will want to keep as is. 
Changing these to TorqueCurrent makes you control the acceleration of the motors which results in robot being un-drivable

<code-block lang="java" src="TunerConstants.java" include-lines="24"/>

Leave as is. Will automatically adjust based on if you have Pro Licenses or not

<code-block lang="java" src="TunerConstants.java" include-lines="28"/>

First value you should be looking to tune. This prevents your wheels from slipping information on how to tune for robot 
can be found [here](https://v6.docs.ctr-electronics.com/en/stable/docs/hardware-reference/talonfx/improving-performance-with-current-limits.html#preventing-wheel-slip).
We will also be tuning values for PathPlanner Swerve Setpoint Generator that will help with wheel slipping and skidding.



