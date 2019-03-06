package com.team2753.archive.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.archive.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/11/2018.
 */

@Autonomous(name = "Crater_Park", group = "0_auto")
@Disabled
@Deprecated
public class Crater_Park extends Team753Linear {
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater_Park", true);
        //while(opModeIsActive() && !isStopRequested()) {

        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.5);
        while(Robot.getLift().getAveragePosition() >= 75){}
        Robot.getLift().unlock();
        Robot.getLift().setPower(0);
        Robot.getLift().setTarget(3800);
        while(Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition){}
        Robot.getLift().setPower(1);
        while(Robot.getLift().getAveragePosition() <= 3600){}
        Robot.getLift().setPower(0);
        Robot.getDrive().encoderDrive(0.5, 6, 6, 4, this);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);
        Robot.getDrive().encoderDrive(0.75, 25, 25, 10, this);

        finalAction();
        //}
    }
}
