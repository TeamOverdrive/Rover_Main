package com.team2753.other.test;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team2753.Team753Linear;

import static com.team2753.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/6/2018.
 */

@Autonomous
@Disabled
public class MineralSampleTest extends Team753Linear{

    @Override
    public void runOpMode() throws InterruptedException {

        double totalDistance = 78;

        waitForStart("Sampling Test", true);
        Robot.getDrive().encoderTurn(90, 0.75, 6, this);
        Robot.getDrive().encoderDrive(0.7, -24, -24, 5, this);
        enableDetector();
        Robot.getDrive().zeroSensors();
        threadSleep(500);

        while(opModeIsActive() && !goldAligned() && getGoldPos() < 300){
            Robot.getDrive().setLeftRightPower(0.35, 0.35);
        }

        double leftPos = Math.abs(Robot.getDrive().getLeftCurrentPosition() * (1/COUNTS_PER_INCH));
        double rightPos = Math.abs(Robot.getDrive().getRightCurrentPosition() * (1/COUNTS_PER_INCH));

        telemetry.addData("Distance Travled", ((leftPos+rightPos)/2));
        telemetry.update();

        Robot.getDrive().encoderTurn(-90, 0.75, 4, this);
        Robot.getDrive().encoderDrive(0.75, 18, 12, 5, this);
        Robot.getDrive().encoderDrive(0.75, -18, -12, 5, this);
        Robot.getDrive().encoderTurn(90, 0.75, 4, this);


        double distanceRemaining = 78 - ((leftPos+rightPos)/2);


        Robot.getDrive().encoderDrive(0.75, distanceRemaining, distanceRemaining, 8, this);


        finalAction();
    }
}
