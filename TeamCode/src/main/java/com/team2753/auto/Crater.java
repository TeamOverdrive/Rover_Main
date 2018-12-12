package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Crater Auto", group = "0_auto")
//@Disabled
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
            while(Robot.getLift().getAveragePosition() >= 75){}
            Robot.getLift().unlock();
            Robot.getLift().setPower(0);
            Robot.getLift().setTarget(3800);
            while(Robot.getLift().getLockPosition() != Robot.getLift().unlockPosition){}
            Robot.getLift().setPower(1);
        while(Robot.getLift().getAveragePosition() <= 3600){}
            Robot.getLift().setPower(0);

            //Forward 6 inches
            Robot.getDrive().encoderDrive(0.5, 16, 16, 4, this);

            //Lower lift
        /*
            Robot.getLift().setTarget(0);
            Robot.getLift().setPower(-0.75);
            while(Robot.getLift().getAveragePosition() >= 100){}
            Robot.getLift().setPower(0);
            */

            /*
            //Collect Mineral
            Robot.getDrive().encoderDrive(0.8, 20, 20, 8, this);

            //Drive to Crater
            Robot.getDrive().encoderDrive(0.8, 20, 20, 8, this);
            */

            //Drive to Crater
            Robot.getDrive().encoderTurn(90, .6, 5, this);

            //sampling goes here

            Robot.getDrive().encoderDrive(.7, 48, 48, 5, this);
            Robot.getDrive().encoderTurn(45, .75, 5, this);
            Robot.getDrive().encoderDrive(0.7, 38, 38, 10, this);

            //Deposit Team Marker
            //Robot.getMarker().deploy();
            Robot.getIntake().setIntakePower(-0.5);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);
            Robot.getIntake().setIntakePower(0);

            //Return to Crater
            Robot.getDrive().encoderDrive(0.8, -74, - 74, 10, this);

            finalAction();
    }
}
