package org.usfirst.frc.team449.robot.jacksonWrappers;

import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.components.RunningLinRegComponent;
import org.usfirst.frc.team449.robot.generalInterfaces.shiftable.Shiftable;

import java.util.List;
import java.util.Map;

/**
 * A Talon that logs the resistance
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class ResistanceLoggingFPSTalon extends FPSTalon {

    /**
     * The component for doing linear regression to find the resistance.
     */
    @NotNull
    private final RunningLinRegComponent voltagePerCurrentLinReg;

    /**
     * The PDP this Talon is connected to.
     */
    @NotNull
    private final PDP PDP;

    /**
     * Default constructor.
     *
     * @param port                       CAN port of this Talon.
     * @param name                       The talon's name, used for logging purposes. Defaults to talon_portnum
     * @param reverseOutput              Whether to reverse the output.
     * @param enableBrakeMode            Whether to brake or coast when stopped.
     * @param voltagePerCurrentLinReg    The component for doing linear regression to find the resistance.
     * @param PDP The PDP this Talon is connected to.
     * @param fwdLimitSwitchNormallyOpen Whether the forward limit switch is normally open or closed. If this is null,
     *                                   the forward limit switch is disabled.
     * @param revLimitSwitchNormallyOpen Whether the reverse limit switch is normally open or closed. If this is null,
     *                                   the reverse limit switch is disabled.
     * @param fwdSoftLimit               The forward software limit, in feet. If this is null, the forward software
     *                                   limit is disabled. Ignored if there's no encoder.
     * @param revSoftLimit               The reverse software limit, in feet. If this is null, the reverse software
     *                                   limit is disabled. Ignored if there's no encoder.
     * @param postEncoderGearing         The coefficient the output changes by after being measured by the encoder, e.g.
     *                                   this would be 1/70 if there was a 70:1 gearing between the encoder and the
     *                                   final output. Defaults to 1.
     * @param feetPerRotation            The number of feet travelled per rotation of the motor this is attached to.
     *                                   Defaults to 1.
     * @param currentLimit               The max amps this device can draw. If this is null, no current limit is used.
     * @param enableVoltageComp          Whether or not to use voltage compensation. Defaults to false.
     * @param feedbackDevice             The type of encoder used to measure the output velocity of this motor. Can be
     *                                   null if there is no encoder attached to this Talon.
     * @param encoderCPR                 The counts per rotation of the encoder on this Talon. Can be null if
     *                                   feedbackDevice is, but otherwise must have a value.
     * @param reverseSensor              Whether or not to reverse the reading from the encoder on this Talon. Ignored
     *                                   if feedbackDevice is null. Defaults to false.
     * @param perGearSettings            The settings for each gear this motor has. Can be null to use default values
     *                                   and gear # of zero. Gear numbers can't be repeated.
     * @param startingGear               The gear to start in. Can be null to use startingGearNum instead.
     * @param startingGearNum            The number of the gear to start in. Ignored if startingGear isn't null.
     *                                   Defaults to the lowest gear.
     * @param minNumPointsInBottomBuffer The minimum number of points that must be in the bottom-level MP buffer before
     *                                   starting a profile. Defaults to 20.
     * @param updaterProcessPeriodSecs   The period for the Notifier that moves points between the MP buffers, in
     *                                   seconds. Defaults to 0.005.
     * @param statusFrameRatesMillis     The update rates, in millis, for each of the Talon status frames.
     * @param controlFrameRatesMillis    The update rate, in milliseconds, for each of the control frame.
     * @param slaves                     The other {@link com.ctre.phoenix.motorcontrol.can.TalonSRX}s that are slaved to this one.
     */
    public ResistanceLoggingFPSTalon(@JsonProperty(required = true) int port,
                                     @Nullable String name,
                                     boolean reverseOutput,
                                     @JsonProperty(required = true) boolean enableBrakeMode,
                                     @JsonProperty(required = true) RunningLinRegComponent voltagePerCurrentLinReg,
                                     @JsonProperty(required = true) PDP PDP,
                                     @Nullable Boolean fwdLimitSwitchNormallyOpen,
                                     @Nullable Boolean revLimitSwitchNormallyOpen,
                                     @Nullable Double fwdSoftLimit,
                                     @Nullable Double revSoftLimit,
                                     @Nullable Double postEncoderGearing,
                                     @Nullable Double feetPerRotation,
                                     @Nullable Integer currentLimit,
                                     boolean enableVoltageComp,
                                     @Nullable FeedbackDevice feedbackDevice,
                                     @Nullable Integer encoderCPR,
                                     boolean reverseSensor,
                                     @Nullable List<PerGearSettings> perGearSettings,
                                     @Nullable Shiftable.gear startingGear,
                                     @Nullable Integer startingGearNum,
                                     @Nullable Integer minNumPointsInBottomBuffer,
                                     @Nullable Double updaterProcessPeriodSecs,
                                     @Nullable Map<StatusFrameEnhanced, Integer> statusFrameRatesMillis,
                                     @Nullable Map<ControlFrame, Integer> controlFrameRatesMillis,
                                     @Nullable List<SlaveTalon> slaves) {
        super(port, name, reverseOutput, enableBrakeMode, fwdLimitSwitchNormallyOpen, revLimitSwitchNormallyOpen, fwdSoftLimit, revSoftLimit, postEncoderGearing, feetPerRotation, currentLimit, enableVoltageComp, feedbackDevice, encoderCPR, reverseSensor, perGearSettings, startingGear, startingGearNum, minNumPointsInBottomBuffer, updaterProcessPeriodSecs, statusFrameRatesMillis, controlFrameRatesMillis, slaves);
        this.voltagePerCurrentLinReg = voltagePerCurrentLinReg;
        this.PDP = PDP;
    }

    /**
     * Get the headers for the data this subsystem logs every loop.
     *
     * @return An N-length array of String labels for data, where N is the length of the Object[] returned by getData().
     */
    @NotNull
    @Override
    public String[] getHeader() {
        return new String[]{
                "velocity",
                "position",
                "setpoint",
                "error",
                "battery_voltage",
                "voltage",
                "current",
                "control_mode",
                "gear",
                "resistance"
        };
    }

    /**
     * Get the data this subsystem logs every loop.
     *
     * @return An N-length array of Objects, where N is the number of labels given by getHeader.
     */
    @NotNull
    @Override
    public Object[] getData() {
        voltagePerCurrentLinReg.addPoint(getOutputCurrent(), PDP.getVoltage()-getBatteryVoltage());
        return new Object[]{
                getVelocity(),
                getPositionFeet(),
                getSetpoint(),
                getError(),
                getBatteryVoltage(),
                getOutputVoltage(),
                getOutputCurrent(),
                getControlMode(),
                getGear(),
                voltagePerCurrentLinReg.getSlope()
        };
    }
}
