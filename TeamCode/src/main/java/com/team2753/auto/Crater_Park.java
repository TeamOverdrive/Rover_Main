package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/11/2018.
 */

@Autonomous(name = "Crater_Park")
public class Crater_Park extends Team753Linear {
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater_Park", true, this);

        int i = 0;
        while(opModeIsActive() && i == 0){
            Robot.getDrive().encoderDrive(0.8, -36,-36,10, this);
            i++;
        }
        finalAction();
    }
}
