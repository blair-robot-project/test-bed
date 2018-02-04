package org.usfirst.frc.team449.robot.commands.general;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc.team449.robot.other.MotionProfileData;

import java.util.function.Supplier;

public class PrintProfile extends InstantCommand{

    private final Supplier<MotionProfileData> left, right;

    public PrintProfile(Supplier<MotionProfileData> left, Supplier<MotionProfileData> right){
        this.left = left;
        this.right = right;
    }

    public void execute(){
        MotionProfileData leftP = left.get(), rightP = right.get();
        System.out.println("Left: ");
        for (double[] line : leftP.getData()){
            System.out.println("Position: "+line[0]+", velocity: "+line[1]+", acceleration: "+line[2]);
        }
        System.out.println("Right: ");
        for (double[] line : rightP.getData()){
            System.out.println("Position: "+line[0]+", velocity: "+line[1]+", acceleration: "+line[2]);
        }
    }

    public void end(){
        System.out.println("PrintProfile end");
    }
}
