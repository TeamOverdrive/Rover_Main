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
        //while(opModeIsActive() && !isStopRequested()) {

            //Flip phone

            //Deploy intake

            //Land
            Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
            Robot.getLift().setTarget(0);
            Robot.getLift().setPower(-0.25);
            while(Robot.getLift().getAveragePosition() >= 75){}
            Robot.getLift().unlock();
            Robot.getLift().setPower(0);
            Robot.getLift().setTarget(3800);
            Robot.getLift().setPower(1);
            while(Robot.getLift().getAveragePosition() <= 3700){}
            Robot.getLift().setPower(0);

            //Forward 6 inches
            Robot.getDrive().encoderDrive(0.5, 12, 12, 4, this);

            //Lower lift
            Robot.getLift().setTarget(0);
            Robot.getLift().setPower(-0.75);
            while(Robot.getLift().getAveragePosition() >= 100){}
            Robot.getLift().setPower(0);

            /*
            //Collect Mineral
            Robot.getDrive().encoderDrive(0.8, 20, 20, 8, this);

            //Drive to Crater
            Robot.getDrive().encoderDrive(0.8, 20, 20, 8, this);
            */

            //Drive to Crater
            Robot.getDrive().encoderTurn(-90, .75, 5, this);
            Robot.getDrive().encoderDrive(.9, -50, -50, 10, this);
            Robot.getDrive().encoderTurn(45, .75, 5, this);
            Robot.getDrive().encoderDrive(0.9, -40, -40, 10, this);

            //Deposit Team Marker
            Robot.getMarker().retract();

            //Return to Crater
            //78
            Robot.getDrive().encoderDrive(1, 78, 78, 10, this);

            finalAction();
        //}
    }
}
