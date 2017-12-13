package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.wpilibj.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedRunnable;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlCommand;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlSubsystem;
import org.usfirst.frc.team449.robot.oi.buttons.CommandButton;
import org.usfirst.frc.team449.robot.other.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Jackson-compatible object representing the entire robot.
 */
public class TestBedMap {

    /**
     * The buttons for controlling this robot.
     */
    @NotNull
    private final List<CommandButton> buttons;

    /**
     * The logger for recording events and telemetry data.
     */
    @NotNull
    private final Logger logger;

    /**
     * A runnable that updates cached variables.
     */
    @NotNull
    private final Runnable updater;

    /**
     * A map of subsystems to commands that sets the default command for each subsystem to its corresponding command.
     */
    @Nullable
    private final Map<YamlSubsystem, YamlCommand> defaultCommands;

    /**
     * The command to be run when first enabled in autonomous mode.
     */
    @Nullable
    private final Command autoStartupCommand;

    /**
     * The command to be run when first enabled in teleoperated mode.
     */
    @Nullable
    private final Command teleopStartupCommand;

    /**
     * The command to be run when first enabled.
     */
    @Nullable
    private final Command startupCommand;

    /**
     * Default constructor.
     *
     * @param buttons              The buttons for controlling this robot. Can be null for an empty list.
     * @param logger               The logger for recording events and telemetry data.
     * @param updater              A runnable that updates cached variables.
     * @param defaultCommands      A map of subsystems to commands that sets the default command for each subsystem to
     *                             its corresponding command.
     * @param autoStartupCommand   The command to be run when first enabled in autonomous mode.
     * @param teleopStartupCommand The command to be run when first enabled in teleoperated mode.
     * @param startupCommand       The command to be run when first enabled.
     */
    @JsonCreator
    public TestBedMap(@Nullable List<CommandButton> buttons,
                      @NotNull @JsonProperty(required = true) Logger logger,
                      @NotNull @JsonProperty(required = true) MappedRunnable updater,
                      @Nullable Map<YamlSubsystem, YamlCommand> defaultCommands,
                      @Nullable YamlCommand autoStartupCommand,
                      @Nullable YamlCommand teleopStartupCommand,
                      @Nullable YamlCommand startupCommand) {
        this.buttons = buttons != null ? buttons : new ArrayList<>();
        this.logger = logger;
        this.updater = updater;
        this.defaultCommands = defaultCommands;
        this.autoStartupCommand = autoStartupCommand != null ? autoStartupCommand.getCommand() : null;
        this.teleopStartupCommand = teleopStartupCommand != null ? teleopStartupCommand.getCommand() : null;
        this.startupCommand = startupCommand != null ? startupCommand.getCommand() : null;
    }

    /**
     * @return The buttons for controlling this robot.
     */
    @NotNull
    public List<CommandButton> getButtons() {
        return buttons;
    }

    /**
     * @return The logger for recording events and telemetry data.
     */
    @NotNull
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return The command to be run when first enabled in autonomous mode.
     */
    @Nullable
    public Command getAutoStartupCommand() {
        return autoStartupCommand;
    }

    /**
     * @return The command to be run when first enabled in teleoperated mode.
     */
    @Nullable
    public Command getTeleopStartupCommand() {
        return teleopStartupCommand;
    }

    /**
     * @return The command to be run when first enabled.
     */
    @Nullable
    public Command getStartupCommand() {
        return startupCommand;
    }

    /**
     * @return A runnable that updates cached variables.
     */
    @NotNull
    public Runnable getUpdater() {
        return updater;
    }

    /**
     * @return A map of subsystems to commands that sets the default command for each subsystem to its corresponding
     * command.
     */
    @Nullable
    public Map<YamlSubsystem, YamlCommand> getDefaultCommands() {
        return defaultCommands;
    }
}
