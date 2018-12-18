package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

import static com.team2753.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Crater Auto", group = "0_auto")
@Disabled
public class Crater extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Autonomous", true);

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
        Robot.getDrive().encoderDrive(0.5, 16, 16, 4, this);

        //Lower lift

        //Sample
        /*
        double totalDistance = 78;

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


        double distanceRemaining = 76 - ((leftPos+rightPos)/2);


        Robot.getDrive().encoderDrive(0.75, distanceRemaining, distanceRemaining, 4, this);
        */

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
