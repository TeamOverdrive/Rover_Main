package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 12/3/2018.
 */

@Autonomous(name = "Land Only", group = "0_auto")
@Disabled
public class Hang extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Autonomous", true);

        //A Land only autonomous

        //Land
        //Land
        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.5);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 75){
            if(opModeIsActive() &&
                    Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition &&
                    Robot.getLift().getAveragePosition()<= 200){
                Robot.getLift().unlock();
            }
        }
        Robot.getLift().setPower(0);
        Robot.getLift().setTarget(3800);
        while(opModeIsActive() && Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition){}
        Robot.getLift().setPower(1);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3600){}
        Robot.getLift().setPower(0);

        //Forward 6 inches
        Robot.getDrive().encoderDrive(0.5, 6, 6, 4, this);

        //Lower lift
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);

        finalAction();
    }
}
