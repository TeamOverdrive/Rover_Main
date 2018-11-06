package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Crater Auto", group = "0_auto")
//@Disabled
public class Depot extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Autonomous", true);

        //Flip phone up

        //Land

        //Deploy intake

        //Forward ~3 inches

        //Turn towards mineral sample

        //Collect Mineral

        //Drive forward into depot

        //Deposit Team Marker

        //Drive to Crater

        finalAction();
    }
}
