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
//@Disabled
public class MineralSampleTest extends Team753Linear{

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Sampling Test", true);

        Robot.getDrive().encoderDrive(0.5, 15, 15, 3, this);

        //Sample
        Robot.getDrive().encoderTurn(90, 0.75, 3, this);
        Robot.getDrive().encoderDrive(0.7, -20, -20, 3, this);
        enableDetector();
        Robot.getDrive().encoderDrive(0.35, 4, 4, 2, this);

        if(goldAligned()){
            Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
            Robot.getDrive().encoderDrive(0.75, 16, 16, 3, this);
            Robot.getDrive().encoderDrive(0.75, -16, -16, 3, this);
            Robot.getDrive().encoderTurn(90, 0.75, 3, this);
            //drive to wall
            Robot.getDrive().encoderDrive(0.75, 68, 68, 4, this);
        }
        else {
            Robot.getDrive().encoderDrive(0.6, 16, 16, 3, this);

            if (goldAligned()) {
                Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                Robot.getDrive().encoderDrive(0.75, 16, 16, 3, this);
                Robot.getDrive().encoderDrive(0.75, -16, -16, 3, this);
                Robot.getDrive().encoderTurn(90, 0.75, 3, this);
                //drive to wall
                Robot.getDrive().encoderDrive(0.6, 52, 52, 4, this);
            }
            else {
                Robot.getDrive().encoderDrive(0.6, 16, 16, 3, this);

                if (goldAligned()) {
                    Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                    Robot.getDrive().encoderDrive(0.75, 16, 16, 3, this);
                    Robot.getDrive().encoderDrive(0.75, -16, -16, 3, this);
                    Robot.getDrive().encoderTurn(90, 0.75, 3, this);
                }
                //drive to wall
                Robot.getDrive().encoderDrive(0.6, 36, 36, 3, this);
            }
        }

        finalAction();
    }
}
