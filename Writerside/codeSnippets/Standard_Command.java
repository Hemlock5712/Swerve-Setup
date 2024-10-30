// Run flywheel full speed then stop
Commands.startEnd(
    () -> flywheel.setSpeed(1),
    () -> flywheel.setSpeed(0),
    flywheel
)