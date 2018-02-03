package org.usfirst.frc.team449.robot.subsystem.interfaces.motionProfile.TwoSideMPSubsystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.commands.general.CommandSequence;
import org.usfirst.frc.team449.robot.components.PathRequester;
import org.usfirst.frc.team449.robot.generalInterfaces.poseCommand.PoseCommand;
import org.usfirst.frc.team449.robot.generalInterfaces.poseEstimator.UnidirectionalPoseEstimator;
import org.usfirst.frc.team449.robot.subsystem.interfaces.AHRS.SubsystemAHRS;
import org.usfirst.frc.team449.robot.subsystem.interfaces.motionProfile.TwoSideMPSubsystem.SubsystemMPTwoSides;

import java.util.ArrayList;
import java.util.List;

/**
 * A command that drives the given subsystem to a list of absolute positions in sequence.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class GoToPositionSequence<T extends Subsystem & SubsystemMPTwoSides & SubsystemAHRS> extends CommandGroup {

    /**
     * Default Constructor
     *
     * @param path          The path to follow, represented by a list of arrays organized {X, Y, Theta}
     * @param subsystem     The subsystem to run the path gotten from the Jetson on.
     * @param pathRequester The object for interacting with the Jetson.
     * @param poseEstimator The object to get robot pose from.
     * @param deltaTime     The time between setpoints in the profile, in seconds.
     */
    @JsonCreator
    public GoToPositionSequence(@NotNull @JsonProperty(required = true) List<double[]> path,
                                @NotNull @JsonProperty(required = true) T subsystem,
                                @NotNull @JsonProperty(required = true) PathRequester pathRequester,
                                @NotNull @JsonProperty(required = true) UnidirectionalPoseEstimator poseEstimator,
                                @JsonProperty(required = true) double deltaTime) {
        for(double[] node : path){
            addSequential(new GoToPosition(subsystem, pathRequester, poseEstimator, node[0], node[1], node[2], deltaTime));
        }
    }

}
