package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Depot Auto", group = "0_auto")
//@Disabled
public class Depot extends Team753Linear{
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
            Robot.getDrive().encoderDrive(0.5, 14, 14, 4, this);

            //Lower lift
        /*
            Robot.getLift().setTarget(0);
            Robot.getLift().setPower(-0.75);
            while(Robot.getLift().getAveragePosition() >= 100){}
            Robot.getLift().setPower(0);
            */

            /*
            //Collect Mineral
            Gold_Position testPosition = Gold_Position.CENTER;

            switch (testPosition){
                case LEFT:{
                    Robot.getDrive().encoderTurn(45, 0.75, 3, this);
                }
                case RIGHT:{
                    Robot.getDrive().encoderTurn(-45, 0.75, 3, this);
                }
            }

            Robot.getDrive().encoderDrive(0.8, 30, 30, 8, this);


            //Drive forward into depot
            switch (testPosition){
                case RIGHT:{
                    Robot.getDrive().encoderTurn(45, 0.75, 3, this);
                }
                case LEFT:{
                    Robot.getDrive().encoderTurn(-45, 0.75, 3, this);
                }
            }
            Robot.getDrive().encoderDrive(0.8, 30, 30, 8, this);
            */

            //Drive into depot

        //turn 90 degrees then sample here


            Robot.getDrive().encoderDrive(0.5, 47, 47, 8, this);



            //Deposit Team Marker
        //Robot.getMarker().deploy();
        Robot.getIntake().setIntakePower(-0.5);
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);
        Robot.getIntake().setIntakePower(0);
        Robot.getDrive().encoderTurn(135, 0.5, 5, this);

            //Drive to Crater
            Robot.getDrive().encoderDrive(0.8, 74, 74, 10, this);

            finalAction();
    }
}
