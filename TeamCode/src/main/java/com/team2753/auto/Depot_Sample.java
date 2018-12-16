package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 12/15/2018.
 */
@Autonomous(name = "Depot Sample")
public class Depot_Sample extends Team753Linear{
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
        while(opModeIsActive() && Robot.getLift().isLocked()){}
        Robot.getLift().setPower(1);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3700){}
        Robot.getLift().setPower(0);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.7, 74, 74, 5, this);

        //Lower lift


        //Sample
        Robot.getDrive().encoderTurn(90, 0.75, 4, this);
        Robot.getDrive().encoderDrive(0.7, -16, -16, 5, this);
        enableDetector();

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
        Robot.getDrive().encoderTurn(-45, 0.8, 3, this);
        Robot.getDrive().encoderDrive(0.8, 50, 50, 4, this);


        finalAction();
    }
}
