package org.usfirst.frc.team449.robot.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.team254.lib.util.motion.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.other.MotionProfileData;

import java.util.List;

/**
 * A wrapper for 254's onboard path generation.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class PathGenerator {

    /**
     * The parameters for the motion profile.
     */
    @NotNull
    private final MotionProfileConstraints settings;

    /**
     * The amount of time between each point, in seconds.
     */
    private final double deltaTimeSecs;

    /**
     * The profile generated using 254's code. Field to avoid garbage collection.
     */
    private MotionProfile generatedProfile;

    /**
     * The current point being loaded from 254's profile into a {@link MotionProfileData}. Field to avoid garbage collection.
     */
    private MotionState motionState;

    /**
     * The list of positions, velocities, and accelerations in the profile. Field to avoid garbage collection.
     */
    private List<Double> pos,  vel,  accel;

    /**
     * Default constructor.
     *
     * @param maxVel The maximum velocity for the profile to reach, in feet/sec.
     * @param maxAccel The maximum acceleration for the profile to reach, in feet/(sec^2)
     * @param deltaTimeSecs The amount of time between each point, in seconds. Defaults to .02 seconds, or 20 milliseconds.
     */
    @JsonCreator
    public PathGenerator(@JsonProperty(required = true) double maxVel,
                         @JsonProperty(required = true) double maxAccel,
                         @Nullable Double deltaTimeSecs){
        this.settings = new MotionProfileConstraints(maxVel, maxAccel);
        this.deltaTimeSecs = deltaTimeSecs != null ? deltaTimeSecs : 0.02;
    }

    /**
     * Generate a profile given the current state and desired end state.
     *
     * @param currentPos The current position in feet.
     * @param currentVel The current velocity in feet/sec.
     * @param currentAccel The current acceleration in feet/(sec^2).
     * @param destinationPos The desired position in feet.
     * @return A motion profile that will move from the current state to the destination.
     */
    public MotionProfileData generateProfile(double currentPos, double currentVel, double currentAccel, double destinationPos){
        generatedProfile = MotionProfileGenerator.generateProfile(settings,
                new MotionProfileGoal(Math.abs(destinationPos-currentPos)), new MotionState(0,0, currentVel, currentAccel));

        for (double t = 0; t < generatedProfile.endTime(); t += deltaTimeSecs){
            motionState = generatedProfile.stateByTimeClamped(t);
            pos.add(motionState.pos());
            vel.add(motionState.vel());
            accel.add(motionState.acc());
        }
        motionState = generatedProfile.stateByTimeClamped(generatedProfile.endTime());
        pos.add(motionState.pos());
        vel.add(motionState.vel());
        accel.add(motionState.acc());
        return new MotionProfileData(pos, vel, accel, deltaTimeSecs, currentPos > destinationPos, false, false);
    }

}
