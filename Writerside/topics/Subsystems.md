# Subsystems

Shows options on how to set up a new subsystem. More information on subsystems can be found [here](https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html)

There are three main ways we recommend setting up a subsystem.

<tabs>
    <tab id="simple" title="Simple">
        This is a simple subsystem setup this includes full logging with no simulation.
        <br/>
        <code-block lang="java" src="Simple_Subsystem.java"/>
    </tab>
    <tab id="standard" title="Standard">
        This is a standard subsystem setup this includes full logging with simulation.
        <br/>/br>
        <code-block lang="java" src="Standard_Subsystem.java"/>
    </tab>
    <tab id="advantage" title="AdvantageKit">
        If in need of full replay ability, simulation, logging, or need to be able to switch hardware types on a mechanisms we recommend using AdvantageKit.
        <br/> Link to example AdvantageKit flywheel implementation can be found <a href="https://github.com/Mechanical-Advantage/AdvantageKit/tree/main/example_projects/advanced_swerve_drive/src/main/java/frc/robot/subsystems/flywheel">here</a>
    </tab>
</tabs>

After you have chosen your subsystem type in almost all cases your next thing to do is to implement PID and FF.

We recommend following the instructions found [here](https://phoenixpro-documentation--161.org.readthedocs.build/en/161/docs/application-notes/manual-pid-tuning.html) or using SysID. 