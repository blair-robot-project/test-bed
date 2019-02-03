package org.usfirst.frc.team449.robot;

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to
 * change the parameter class to the startRobot call.
 */
public final class TestBedMain {
    private TestBedMain() {
    }

    /**
     * TestBedMain initialization function. Do not perform any initialization here.
     *
     * <p>If you change your main robot class, change the parameter type.
     */
    public static void main(String... args) {
        TestBed.startRobot(TestBed::new);
    }
}