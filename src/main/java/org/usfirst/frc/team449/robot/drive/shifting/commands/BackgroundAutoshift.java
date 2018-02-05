package org.usfirst.frc.team449.robot.drive.shifting.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.command.Command;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.components.AutoshiftComponent;
import org.usfirst.frc.team449.robot.drive.shifting.DriveShiftable;
import org.usfirst.frc.team449.robot.drive.unidirectional.DriveUnidirectional;
import org.usfirst.frc.team449.robot.other.Logger;

/**
 * Autoshift the drive but don't give any outputs to the motors.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class BackgroundAutoshift<T extends DriveShiftable & DriveUnidirectional> extends Command {

    /**
     * The subsystem this command controls.
     */
    private final T subsystem;

    /**
     * The component for determining when to shift.
     */
    private final AutoshiftComponent autoshiftComponent;

    /**
     * Default constructor.
     *
     * @param subsystem          The subsystem this command controls.
     * @param autoshiftComponent The component for determining when to shift.
     */
    @JsonCreator
    public BackgroundAutoshift(@NotNull @JsonProperty(required = true) T subsystem,
                               @NotNull @JsonProperty(required = true) AutoshiftComponent autoshiftComponent) {
        this.subsystem = subsystem;
        this.autoshiftComponent = autoshiftComponent;
    }

    /**
     * Log when this command is initialized
     */
    @Override
    protected void initialize() {
        Logger.addEvent("BackgroundAutoshift init.", this.getClass());
    }

    /**
     * Autoshift.
     */
    @Override
    protected void execute() {
        //Pass in 1 for forwardThrottle because it means the throttle has no effect.
        autoshiftComponent.autoshift(1, subsystem.getLeftVel(), subsystem.getRightVel(), subsystem::setGear);
    }

    /**
     * Run forever.
     *
     * @return false
     */
    @Override
    protected boolean isFinished() {
        return false;
    }

    /**
     * Log when this command ends
     */
    @Override
    protected void end() {
        Logger.addEvent("BackgroundAutoshift end.", this.getClass());
    }

    /**
     * Log when this command is interrupted.
     */
    @Override
    protected void interrupted() {
        Logger.addEvent("BackgroundAutoshift Interrupted!", this.getClass());
    }
}
