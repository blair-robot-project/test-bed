package org.usfirst.frc.team449.robot.commands.general;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.SelectCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A Jackson-friendly wrapper on {@link SelectCommand}
 *
 * @param <T> The type of the key to the map.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public abstract class SelectCommandWrapper<T> extends SelectCommand{

    /**
     * Creates a new SelectCommand with given map of selectors and m_commands.
     * <p>
     * <p>Users of this constructor should also override selector().
     *
     * @param commands The map of selectors to the command that should be run if they're chosen via selector().
     */
    @JsonCreator
    public SelectCommandWrapper(@NotNull @JsonProperty(required = true) Map<T, Command> commands) {
        super(commands);
    }
}
