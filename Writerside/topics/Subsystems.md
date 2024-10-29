# Subsystem Setup

This will show how to set up a new subsystem.


There are three main ways we recommend setting up a subsystem.

<tabs>
    <tab id="simple" title="Simple">
        This is a simple subsystem setup this includes full logging with no simulation.
        <br/>
        <code-block lang="java" src="Simple.java"/>
    </tab>
    <tab id="standard" title="Standard">
        This is a standard subsystem setup this includes full logging with simulation.
        <br/>/br>
        <code-block lang="java" src="Standard.java"/>
    </tab>
    <tab id="advantage" title="AdvantageKit">
        If in need of full replay ability, simulation, logging, or need to be able to switch hardware types on a mechanisms we recommend using AdvantageKit.
        <br/> Link to example AdvantageKit flywheel implementation can be found <a href="https://github.com/Mechanical-Advantage/AdvantageKit/tree/main/example_projects/advanced_swerve_drive/src/main/java/frc/robot/subsystems/flywheel">here</a>
    </tab>
</tabs>

After you have chosen your subsystem type in almost all cases your next thing to do is to implement PID and FF.

We recommend following the instructions found [here](https://phoenixpro-documentation--161.org.readthedocs.build/en/161/docs/application-notes/manual-pid-tuning.html) or using SysID. 