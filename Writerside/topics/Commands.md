# Commands

Shows options on how to set up a new commands. More information on commands can be found [here](https://docs.wpilib.org/en/stable/docs/software/commandbased/what-is-command-based.html)

There are two main ways we recommend setting up a commands.

<tabs>
    <tab id="standard" title="Standard">
        This is a standard command. Refer to WPILib for more examples
        <br/>
        <code-block lang="java" src="Standard_Command.java"/>
    </tab>
    <tab id="complex" title="Complex">
        With more complex systems you need a way to better define states for this we implement a state system designed by CCShambots 
        <br/>
        <code-block lang="java" src="Complex_Command.java"/>
    </tab>
</tabs>

Let's go ahead and break down the Complex command.

<code-block lang="java" src="Complex_Command.java" include-lines="1"/>

This code allows us to extend CCShambots StateMachine class and add different states to it. States are gotten from our enum `Flywheel.State`

<code-block lang="java" src="Complex_Command.java" include-lines="25-31"/>

In this block of code we are setting our flywheel to an undetermined state, registering all states the flywheel can be in,
and also the state to state transitions that are allowed

Now let's dive into `registerStateCommands()`.

<code-block lang="java" src="Complex_Command.java" include-lines="60-64"/>

This code is defining what we want to happen when we tell the Flywheel to go into AMP mode.

In this case we want to set the flywheel speed along with checking if our speed is within tolerance to shoot.

After defining all mode that the robot can be in it is time to tell the robot what states you can go to when in a specific state. 
This will allow you to add safety and potentially add more automation into your code.

<code-block lang="java" src="Complex_Command.java" include-lines="82-88"/>

This allows for our flywheel to be able to transition between any states without restrictions. This will change on your applications

After that is all code that is fairly standard in other commands. 

Now you then just need to call from `RobotContainer.java` to set state. For very complicated robots you may need states in your `RobotContainer` 

<compare type="top-bottom" first-title="Simple State (bind to button)" second-title="Complex State">
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

