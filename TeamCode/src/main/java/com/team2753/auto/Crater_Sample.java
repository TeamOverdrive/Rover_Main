package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

import static com.team2753.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 12/15/2018.
 */

@Autonomous(name = "Crater Sample")
public class Crater_Sample extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Sample", true);

        //Flip phone

        //Deploy intake

        //Land
        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getLift().setTarget(-100);
        Robot.getLift().setPower(-1);
        Robot.getLift().unlock();
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 50){}
        setTimer1(250);
        Robot.getLift().setPower(0);
        Robot.getLift().setTarget(3800);
        Robot.getLift().setPower(1);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3700){}
        Robot.getLift().setPower(0);

        //Start Camera
        enableDetector();

        //Drive Forward
        Robot.getDrive().encoderDrive(0.8, 12, 12, 3, this);

        //Lower lift
        Robot.getLift().unlock();
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);

        if(goldAligned()){
            //Mineral is in the center
            Robot.getDrive().encoderDrive(0.8, 16, 16, 3, this);
            Robot.getDrive().encoderDrive(0.8, -14, -14, 3, this);
            Robot.getDrive().encoderTurn(90, 0.75, 3, this);
            //drive to wall
            Robot.getDrive().encoderDrive(0.75, 45, 45, 4, this);

            //Drive to Depot
            Robot.getDrive().encoderTurnTest(50, 0.6, 4, this);
            Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

            //Deposit Team Marker
            //Robot.getMarker().deploy();
            Robot.getIntake().setIntakePower(0.7);
            setTimer1(750);
            Robot.getIntake().setIntakePower(0);

            //Return to Crater
            Robot.getDrive().encoderDrive(0.8, -60, -60, 5, this);
        }
        else if(!goldAligned()){
            Robot.getDrive().encoderTurn(-45, 0.65, 3, this);
            if(goldAligned()){
                Robot.getDrive().encoderDrive(0.8, 18, 18, 3, this);
                Robot.getDrive().encoderDrive(0.8, -16, -16, 3, this);
                Robot.getDrive().encoderTurn(135, 0.8, 2, this);
                Robot.getDrive().encoderDrive(0.8, 46, 46, 4, this);

                //Drive to Depot
                Robot.getDrive().encoderTurnTest(45, 0.6, 4, this);
                Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

                //Deposit Team Marker
                //Robot.getMarker().deploy();
                Robot.getIntake().setIntakePower(0.7);
                setTimer1(750);
                Robot.getIntake().setIntakePower(0);

                //Return to Crater
                Robot.getDrive().encoderDrive(0.8, -70, -70, 5, this);
            }
            else{
                Robot.getDrive().encoderTurn(90, 0.75, 2, this);
                if (goldAligned()){
                    Robot.getDrive().encoderDrive(0.8, 18, 18, 3, this);
                    Robot.getDrive().encoderDrive(0.8, -16, -16, 3, this);
                    Robot.getDrive().encoderTurn(45, 0.65, 2, this);
                    Robot.getDrive().encoderDrive(0.8, 46, 46, 4, this);

                    //Drive to Depot
                    Robot.getDrive().encoderTurnTest(50, 0.6, 4, this);
                    Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

                    //Deposit Team Marker
                    //Robot.getMarker().deploy();
                    Robot.getIntake().setIntakePower(0.7);
                    setTimer1(750);
                    Robot.getIntake().setIntakePower(0);

                    //Return to Crater
                    Robot.getDrive().encoderDrive(0.8, -70, -70, 5, this);
                }
                else {
                    Robot.getDrive().encoderTurn(45, 0.65, 30, this);
                    Robot.getDrive().encoderDrive(0.8, 46, 46, 4, this);

                    //Drive to Depot
                    Robot.getDrive().encoderTurnTest(50, 0.6, 4, this);
                    Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

                    //Deposit Team Marker
                    //Robot.getMarker().deploy();
                    Robot.getIntake().setIntakePower(0.7);
                    setTimer1(750);
                    Robot.getIntake().setIntakePower(0);

                    //Return to Crater
                    Robot.getDrive().encoderDrive(0.8, -72, -72, 5, this);
                }
            }
        }

        finalAction();
    }
}
