package org.usfirst.frc.team449.robot.subsystem.interfaces.compex.elevator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.components.PathGenerator;
import org.usfirst.frc.team449.robot.jacksonWrappers.FPSTalon;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedDoubleSolenoid;
import org.usfirst.frc.team449.robot.subsystem.interfaces.position.SubsystemPositionOnboardMP;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SubsystemSolenoid;

/**
 * A SubsystemPosition that uses motion profiles to move and has a pneumatic brake to hold itself in place.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class SubsystemPositionMPWithBrake extends SubsystemPositionOnboardMP implements SubsystemSolenoid{

    /**
     * The piston for the brake.
     */
    @NotNull
    private final MappedDoubleSolenoid piston;

    /**
     * The current position of the piston.
     */
    @NotNull
    private DoubleSolenoid.Value pistonPos;

    /**
     * Default constructor.
     *
     * @param talon         The Talon SRX this subsystem controls.
     * @param pathGenerator The object for generating the paths for the Talon to run.
     * @param piston The piston for the brake.
     */
    @JsonCreator
    public SubsystemPositionMPWithBrake(@JsonProperty(required = true) @NotNull FPSTalon talon,
                                        @JsonProperty(required = true) @NotNull PathGenerator pathGenerator,
                                        @JsonProperty(required = true) @NotNull MappedDoubleSolenoid piston) {
        super(talon, pathGenerator);
        this.piston = piston;
        pistonPos = DoubleSolenoid.Value.kOff;
    }

    /**
     * @param value The position to set the solenoid to.
     */
    @Override
    public void setSolenoid(@NotNull DoubleSolenoid.Value value) {
        piston.set(value);
        pistonPos = value;
    }

    /**
     * @return the current position of the solenoid.
     */
    @Override
    @NotNull
    public DoubleSolenoid.Value getSolenoidPosition() {
        return pistonPos;
    }
}
