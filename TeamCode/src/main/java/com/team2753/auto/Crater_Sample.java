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
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 150){
            /*
            if(opModeIsActive() &&
                    Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition &&
                    Robot.getLift().getAveragePosition()<= 200){
                Robot.getLift().unlock();
            }
            */
        }
        Robot.getLift().setPower(-0.15);
        Robot.getLift().unlock();
        Robot.getLift().setTarget(3800);
        while(opModeIsActive() && Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition){}
        Robot.getLift().setPower(1);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3700){}
        Robot.getLift().setPower(0);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.5, 14, 14, 4, this);

        //Lower lift

        //Sample
        Robot.getDrive().encoderTurn(90, 0.75, 4, this);
        Robot.getDrive().encoderDrive(0.7, -20, -20, 5, this);
        enableDetector();
        Robot.getDrive().encoderDrive(0.35, 4, 4, 2, this);

        if(goldAligned()){
            Robot.getDrive().encoderTurn(-90, 0.75, 4, this);
            Robot.getDrive().encoderDrive(0.75, 18, 18, 5, this);
            Robot.getDrive().encoderDrive(0.75, -18, -18, 5, this);
            Robot.getDrive().encoderTurn(90, 0.75, 4, this);
            //drive to wall
            Robot.getDrive().encoderDrive(0.75, 78, 78, 4, this);
        }
        else {
            Robot.getDrive().encoderDrive(0.6, 16, 16, 3, this);

            if (goldAligned()) {
                Robot.getDrive().encoderTurn(-90, 0.75, 4, this);
                Robot.getDrive().encoderDrive(0.75, 18, 18, 5, this);
                Robot.getDrive().encoderDrive(0.75, -18, -18, 5, this);
                Robot.getDrive().encoderTurn(90, 0.75, 4, this);
                //drive to wall
                Robot.getDrive().encoderDrive(0.6, 62, 62, 4, this);
            }
            else {
                Robot.getDrive().encoderDrive(0.6, 16, 16, 3, this);

                if (goldAligned()) {
                    Robot.getDrive().encoderTurn(-90, 0.75, 4, this);
                    Robot.getDrive().encoderDrive(0.75, 18, 18, 5, this);
                    Robot.getDrive().encoderDrive(0.75, -18, -18, 5, this);
                    Robot.getDrive().encoderTurn(90, 0.75, 4, this);
                }
                //drive to wall
                Robot.getDrive().encoderDrive(0.6, 46, 46, 3, this);
            }
        }

        double leftPos = Math.abs(Robot.getDrive().getLeftCurrentPosition() * (1/COUNTS_PER_INCH));
        double rightPos = Math.abs(Robot.getDrive().getRightCurrentPosition() * (1/COUNTS_PER_INCH));

        telemetry.addData("Distance Travled", ((leftPos+rightPos)/2));
        telemetry.update();




        double distanceRemaining = 76 - ((leftPos+rightPos)/2);


        Robot.getDrive().encoderDrive(0.75, distanceRemaining, distanceRemaining, 4, this);


        //Drive to Depot
        Robot.getDrive().encoderTurn(90, .6, 4, this);

        Robot.getDrive().encoderDrive(.7, 46, 46, 4, this);
        Robot.getDrive().encoderTurn(45, .75, 5, this);
        Robot.getDrive().encoderDrive(0.7, 38, 38, 5, this);

        //Deposit Team Marker
        //Robot.getMarker().deploy();
        Robot.getIntake().setIntakePower(0.7);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);
        Robot.getIntake().setIntakePower(0);

        //Return to Crater
        Robot.getDrive().encoderDrive(0.8, -66, - 66, 5, this);

        finalAction();
    }
}
