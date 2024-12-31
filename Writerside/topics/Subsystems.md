# Subsystems

<p>Here you will learn about subsystems.</p>

Shows options on how to set up a new subsystem. More information on subsystems can be found [here](https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html)

There are three main ways we recommend setting up a subsystem.

<tabs>
    <tab id="simple" title="Simple">
        This is a simple subsystem setup that includes full logging with no simulation.
        <br/>
        <code-block lang="java" src="Simple_Subsystem.java"/>
    </tab>
    <tab id="standard" title="Standard">
        This is a standard subsystem setup that includes full logging with simulation.
        <br/>
        <code-block lang="java" src="Standard_Subsystem.java"/>
    </tab>
    <tab id="advantage" title="AdvantageKit">
        If you need full replay ability, simulation, logging, or the ability to switch hardware types on mechanisms, we recommend using AdvantageKit.
        An example of AdvantageKit flywheel implementation can be found <a href="https://github.com/Mechanical-Advantage/AdvantageKit/tree/main/example_projects/advanced_swerve_drive/src/main/java/frc/robot/subsystems/flywheel">here</a>
    </tab>
</tabs>

In almost all cases, the next step after choosing your subsystem type is to implement PID and FF.

We recommend following the instructions found [here](https://phoenixpro-documentation--161.org.readthedocs.build/en/161/docs/application-notes/manual-pid-tuning.html) or using SysID. 