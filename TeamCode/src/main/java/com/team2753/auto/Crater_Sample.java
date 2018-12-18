package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

import static com.team2753.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 12/15/2018.
 */

@Autonomous(name = "Crater_Sample")
public class Crater_Sample extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Sample", true);

        //Flip phone

        //Deploy intake

        //Land
        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.5);
        Robot.getLift().unlock();
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 25){}
        Robot.getLift().setPower(0);
        Robot.getLift().setTarget(3800);
        Robot.getLift().setPower(1);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3700){}
        Robot.getLift().setPower(0);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.5, 15, 15, 3, this);

        //Start Camera
        Robot.getDrive().encoderTurn(90, 0.75, 3, this);
        Robot.getDrive().encoderDrive(0.7, -20, -20, 3, this);
        enableDetector();

        //Lower lift
        Robot.getLift().unlock();
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);

        //Drive to first mineral
        Robot.getDrive().encoderDrive(0.5, 4, 4, 2, this);

        if(goldAligned()){
            Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
            Robot.getDrive().encoderDrive(0.75, 18, 18, 3, this);
            Robot.getDrive().encoderDrive(0.75, -18, -18, 3, this);
            Robot.getDrive().encoderTurn(90, 0.75, 3, this);
            //drive to wall
            Robot.getDrive().encoderDrive(0.75, 68, 68, 4, this);
        }
        else {
            Robot.getDrive().encoderDrive(0.6, 16, 16, 3, this);

            if (goldAligned()) {
                Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                Robot.getDrive().encoderDrive(0.75, 18, 18, 3, this);
                Robot.getDrive().encoderDrive(0.75, -18, -18, 3, this);
                Robot.getDrive().encoderTurn(90, 0.75, 3, this);
                //drive to wall
                Robot.getDrive().encoderDrive(0.6, 52, 52, 4, this);
            }
            else {
                Robot.getDrive().encoderDrive(0.6, 16, 16, 3, this);

                if (goldAligned()) {
                    Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                    Robot.getDrive().encoderDrive(0.75, 18, 18, 3, this);
                    Robot.getDrive().encoderDrive(0.75, -18, -18, 3, this);
                    Robot.getDrive().encoderTurn(90, 0.75, 3, this);
                }
                //drive to wall
                Robot.getDrive().encoderDrive(0.8, 36, 36, 3, this);
            }
        }

        //Drive to Depot
        Robot.getDrive().encoderTurn(45, .6, 4, this);
        Robot.getDrive().encoderDrive(0.8, 54, 54, 3, this);

        //Deposit Team Marker
        //Robot.getMarker().deploy();
        Robot.getIntake().setIntakePower(0.7);
        threadSleep(5000);
        Robot.getIntake().setIntakePower(0);

        //Return to Crater
        Robot.getDrive().encoderDrive(0.8, -66, - 66, 5, this);

        finalAction();
    }
}
