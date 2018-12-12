package com.team2753.other.test;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/6/2018.
 */

@TeleOp
@Disabled
public class MineralSampleTest extends Team753Linear{

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        initGoldDetector();
        enableDetector();
        Robot.getDrive().encoderTurn(90, 0.6, 6, this);
        Robot.getDrive().encoderDrive(0.7, -16.97, -16.97, 5, this);
        //while(!goldAligned() && )
    }
}
