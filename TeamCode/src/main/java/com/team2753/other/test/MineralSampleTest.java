package com.team2753.other.test;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/6/2018.
 */

@Autonomous
public class MineralSampleTest extends Team753Linear{

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while(opModeIsActive()) {
            initGoldDetector();
            sampleGoldMineral();
        }

    }
}
