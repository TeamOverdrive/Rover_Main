package com.team2753;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 10/17/2018.
 */

@TeleOp(name = "Teleop", group = "0_Main")
public class Teleop extends Team753Linear{

    @Override
    public void runOpMode(){

        waitForStart("Teleop", false);
        dashboardTelemetry.addLine("Yeet");
        sleep(5000);
    }
}
