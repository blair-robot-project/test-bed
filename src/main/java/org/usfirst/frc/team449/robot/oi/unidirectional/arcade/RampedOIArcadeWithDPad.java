package org.usfirst.frc.team449.robot.oi.unidirectional.arcade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.components.RampComponent;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedJoystick;
import org.usfirst.frc.team449.robot.oi.throttles.Throttle;
import org.usfirst.frc.team449.robot.other.Polynomial;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "@class")
public class RampedOIArcadeWithDPad extends OIArcadeWithDPad {

    /**
     * Ramp components for the forwards and rotational outputs, respectively. Can be null for no ramp.
     */
    @Nullable
    private final RampComponent fwdRamp, rotRamp;

    /**
     * Default constructor
     *
     * @param rotThrottle         The throttle for rotating the robot.
     * @param fwdThrottle         The throttle for driving the robot straight.
     * @param dPadShift           How fast the dPad should turn the robot, on [0, 1]. Defaults to 0.
     * @param invertDPad          Whether or not to invert the D-pad. Defaults to false.
     * @param gamepad             The gamepad containing the joysticks and buttons. Can be null if not using the D-pad.
     * @param scaleRotByFwdPoly   The polynomial to scale the forwards throttle output by before using it to scale the
     *                            rotational throttle. Can be null, and if it is, rotational throttle is not scaled by
     *                            forwards throttle.
     * @param turnInPlaceRotScale The scalar that scales the rotational throttle while turning in place.
     * @param rescaleOutputs      Whether or not to scale the left and right outputs so the max output is 1. Defaults to
     *                            false.
     * @param fwdRamp             The ramp component for the forwards output. Can be null for no ramp.
     * @param rotRamp             The ramp component for the rotational output. Can be null for no ramp.
     */
    @JsonCreator
    public RampedOIArcadeWithDPad(
            @NotNull @JsonProperty(required = true) Throttle rotThrottle,
            @NotNull @JsonProperty(required = true) Throttle fwdThrottle,
            double dPadShift,
            boolean invertDPad,
            @Nullable MappedJoystick gamepad,
            @Nullable Polynomial scaleRotByFwdPoly,
            @JsonProperty(required = true) double turnInPlaceRotScale,
            boolean rescaleOutputs,
            @Nullable RampComponent rotRamp,
            @Nullable RampComponent fwdRamp) {
        super(rotThrottle, fwdThrottle, dPadShift, invertDPad, gamepad, scaleRotByFwdPoly, turnInPlaceRotScale, rescaleOutputs);
        this.rotRamp = rotRamp;
        this.fwdRamp = fwdRamp;
    }

    /**
     * The output of the throttle controlling linear velocity, smoothed and adjusted according to what type of joystick
     * it is.
     *
     * @return The processed stick output, sign-adjusted so 1 is forward and -1 is backwards.
     */
    @Override
    public double getFwd() {
        if (fwdRamp != null) {
            return fwdRamp.applyAsDouble(super.getFwd());
        } else {
            return super.getFwd();
        }
    }

    /**
     * Get the output of the D-pad or turning joystick, whichever is in use. If both are in use, the D-pad takes
     * preference.
     *
     * @return The processed stick or D-pad output, sign-adjusted so 1 is right and -1 is left.
     */
    @Override
    public double getRot() {
        if (rotRamp != null) {
            return rotRamp.applyAsDouble(super.getRot());
        } else {
            return super.getRot();
        }
    }
}
