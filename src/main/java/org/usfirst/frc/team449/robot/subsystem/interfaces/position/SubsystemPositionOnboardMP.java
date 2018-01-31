package org.usfirst.frc.team449.robot.subsystem.interfaces.position;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.components.PathGenerator;
import org.usfirst.frc.team449.robot.generalInterfaces.updatable.Updatable;
import org.usfirst.frc.team449.robot.jacksonWrappers.FPSTalon;

/**
 * A SubsystemPosition that moves using motion profiles.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class SubsystemPositionOnboardMP extends Subsystem implements SubsystemPosition, Updatable{

    /**
     * The Talon SRX this subsystem controls.
     */
    private final FPSTalon talon;

    /**
     * The object for generating the paths for the Talon to run.
     */
    private final PathGenerator pathGenerator;

    /**
     * The previously observed Talon velocity. Used for calculating acceleration.
     */
    private double lastVel;

    /**
     * The acceleration of the Talon.
     */
    private double accel;

    /**
     * Whether or not the profile loaded into the Talon has been started.
     */
    private boolean startedProfile;

    /**
     * Default constructor.
     *
     * @param talon The Talon SRX this subsystem controls.
     * @param pathGenerator The object for generating the paths for the Talon to run.
     */
    @JsonCreator
    public SubsystemPositionOnboardMP(@NotNull @JsonProperty(required = true) FPSTalon talon,
                                      @NotNull @JsonProperty(required = true) PathGenerator pathGenerator){
        this.talon = talon;
        this.pathGenerator = pathGenerator;
        startedProfile = false;
    }

    /**
     * Initialize the default command for a subsystem By default subsystems have no default command, but if they do, the
     * default command is set with this method. It is called on all Subsystems by CommandBase in the users program after
     * all the Subsystems are created.
     */
    @Override
    protected void initDefaultCommand() {
        //Do nothing
    }

    /**
     * Set the position setpoint
     *
     * @param feet Setpoint in feet from the limit switch used to zero
     */
    @Override
    public void setPositionSetpoint(double feet) {
        disableMotor();
        talon.loadProfile(pathGenerator.generateProfile(talon.getPositionFeet(), talon.getVelocity(), accel, feet));
        startedProfile = false;
    }

    /**
     * Set a % output setpoint for the motor.
     *
     * @param output The speed for the motor to run at, on [-1, 1]
     */
    @Override
    public void setMotorOutput(double output) {
        talon.setVelocity(output);
    }

    /**
     * Get the state of the reverse limit switch.
     *
     * @return True if the reverse limit switch is triggered, false otherwise.
     */
    @Override
    public boolean getReverseLimit() {
        return talon.getRevLimitSwitch();
    }

    /**
     * Get the state of the forwards limit switch.
     *
     * @return True if the forwards limit switch is triggered, false otherwise.
     */
    @Override
    public boolean getForwardLimit() {
        return talon.getFwdLimitSwitch();
    }

    /**
     * Set the position to 0.
     */
    @Override
    public void resetPosition() {
        talon.resetPosition();
    }

    /**
     * Enable the motors of this subsystem.
     */
    @Override
    public void enableMotor() {
        talon.enable();
    }

    /**
     * Disable the motors of this subsystem.
     */
    @Override
    public void disableMotor() {
        talon.disable();
    }

    /**
     * Updates all cached values with current ones.
     */
    @Override
    public void update() {
        //Update acceleration
        accel = talon.getVelocity() - lastVel;
        lastVel = talon.getVelocity();

        //Start the profile if it's ready
        if (!startedProfile && talon.readyForMP()){
            talon.startRunningMP();
            startedProfile = true;
        }
    }
}
