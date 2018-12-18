package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

import static com.team2753.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Depot Auto", group = "0_auto")
@Disabled
public class Depot extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Autonomous", true);

        //Flip phone

        //Deploy intake

        //Land
        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.65);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 75){
            if(Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition &&
                    Robot.getLift().getAveragePosition()<= 200){
                Robot.getLift().unlock();
            }
        }
        Robot.getLift().setPower(0);
        Robot.getLift().setTarget(3800);
        Robot.getLift().setPower(1);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3700){}
        Robot.getLift().setPower(0);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.7, 72, 72, 5, this);

        //Lower lift


        //Sample
        /*
        double totalDistance = 78;



        Robot.getDrive().encoderTurn(90, 0.75, 6, this);
        Robot.getDrive().encoderDrive(0.7, -24, -24, 5, this);
        enableDetector();
        Robot.getDrive().zeroSensors();
        threadSleep(500);

        while(opModeIsActive() && !goldAligned() && getGoldPos() < 300 && Robot.getDrive().getLeftCurrentPosition() <= 48*COUNTS_PER_INCH){
            Robot.getDrive().setLeftRightPower(0.35, 0.35);
        }

        double leftPos = Math.abs(Robot.getDrive().getLeftCurrentPosition() * (1/COUNTS_PER_INCH));
        double rightPos = Math.abs(Robot.getDrive().getRightCurrentPosition() * (1/COUNTS_PER_INCH));

        telemetry.addData("Distance Travled", ((leftPos+rightPos)/2));
        telemetry.update();

        Robot.getDrive().encoderTurn(-90, 0.75, 4, this);
        Robot.getDrive().encoderDrive(0.75, 36, 36, 5, this);
        Robot.getIntake().setIntakePower(-0.5);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);
        Robot.getIntake().setIntakePower(0);
        Robot.getDrive().encoderDrive(0.75, -36, -36, 5, this);
        Robot.getDrive().encoderTurn(90, 0.75, 4, this);


        double distanceRemaining = 72 - ((leftPos+rightPos)/2);



        Robot.getDrive().encoderDrive(0.75, distanceRemaining, distanceRemaining, 4, this);
        */
        //Drive to Depot

        //Deposit Team Marker
        //Robot.getMarker().deploy();
        Robot.getIntake().setIntakePower(0.7);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);
        Robot.getIntake().setIntakePower(0);


        //Drive to Crater
        Robot.getDrive().encoderTurn(135, 0.8, 3, this);
        Robot.getDrive().encoderDrive(0.8, 50, 50, 4, this);


        finalAction();
    }
}
