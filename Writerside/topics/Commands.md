# Commands

Shows options on how to set up new commands. More information on commands can be found [here](https://docs.wpilib.org/en/stable/docs/software/commandbased/what-is-command-based.html)

There are two main ways we recommend setting up commands.

<tabs>
    <tab id="standard" title="Standard">
        This is a standard command. Refer to WPILib for more examples.
        <br/>
        <code-block lang="java" src="Standard_Command.java"/>
    </tab>
    <tab id="complex" title="Complex" >
        With more complex systems, you need a way to better define states. For this, we implement a state system designed by CCShambots.
        <note>
        This example code is extended; however, this is a complete subsystem with commands added.
        </note>
        <br/>
        <code-block lang="java" collapsible="true" collapsed-title="flywheel.java" src="Complex_Command.java"/>
    </tab>
</tabs>

## Complex Example Breakdown {collapsible="true"}

Let's go ahead and break down the complex command.

<code-block lang="java" src="Complex_Command.java" include-lines="19"/>

This code allows us to extend the CCShambots StateMachine class and add different states. States are gotten from our enum `Flywheel.State`

<code-block lang="java" src="Complex_Command.java" include-lines="40-46"/>

In this block of code, we are setting our flywheel to an undetermined state, registering all states the flywheel can be in,
and also the state-to-state transitions that are allowed

Now let's dive into `registerStateCommands()`.

<code-block lang="java" src="Complex_Command.java" include-lines="77-82"/>

This code defines what we want to happen when we tell the Flywheel to go into AMP mode.

In this case, we want to set the flywheel speed and trigger a flag when our speed is within tolerance to alert another part of our code to shoot.

By defining all possible states of the robot, we give it a clear purpose and direction, enhancing its functionality.
This will allow you to add safety and more automation to your code.

<code-block lang="java" src="Complex_Command.java" include-lines="105-111"/>

This allows our flywheel to transition between any state without restrictions. This will change on your applications.

After that, all the code is pretty standard in other commands.

Finally, you need to call from `RobotContainer.java` to set the state. You may need states in your `RobotContainer.java` for very complicated robots.

<br/>
<compare type="top-bottom" first-title="Simple State" second-title="Complex State">
    <code-block lang="Java">
        joystick.a().whileTrue(
            flywheel.transitionCommand(Flywheel.State.AMP)
        );
    </code-block>
    <code-block lang="Java">
        registerStateCommand(
            State.AMP,
            Commands.either(
                Commands.parallel(
                    flywheel.transitionCommand(Flywheel.State.AMP),
                    arm.transitionCommand(Arm.State.AMP),
                    magazine.transitionCommand(Magazine.State.IDLE)),
                Commands.parallel(
                    flywheel.transitionCommand(Flywheel.State.IDLE),
                    arm.transitionCommand(Arm.State.INTAKE),
                    feedIfArmInIntakePosition),
                () -> lineBreak.getState() == LineBreak.State.LOADED));
        joystick.a().whileTrue(RobotContainer.transitionCommand(State.AMP));
    </code-block>
</compare>

