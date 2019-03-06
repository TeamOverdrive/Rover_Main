package com.team2753.archive.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.archive.Team753Linear;

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

        //DriveBase Forward
        Robot.getDrive().encoderDrive(0.8, 14, 14, 3, this);

        //Lower lift
        Robot.getLift().unlock();
        Robot.getLift().setTarget(0);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 100){}
        Robot.getLift().setPower(0);

        if(goldAligned()){

            //Deposit Team Marker
            Robot.getIntake().setIntakePower(0.75);
            setTimer1(500);
            Robot.getIntake().setIntakePower(0);

            //Mineral is in the center
            Robot.getDrive().encoderDrive(0.8, 48, 48, 3, this);

            Robot.getDrive().encoderTurn(-52, 0.6, 2, this);

            Robot.getDrive().encoderDrive(0.75, -8, -8, 2, this);

            //Park
            Robot.getDrive().encoderDrive(0.85, -70, -70, 4, this);
        }
        else if(!goldAligned()){
            Robot.getDrive().encoderTurn(45, 0.65, 3, this);
            if(goldAligned()){
                Robot.getDrive().encoderDrive(0.8, 35, 35, 3, this);

                Robot.getDrive().encoderTurn(-97, 0.75, 4, this);
                Robot.getDrive().encoderDrive(0.75, 24, 24, 2, this);

                Robot.getIntake().setIntakePower(0.7);
                setTimer1(750);
                Robot.getIntake().setIntakePower(0);

                //Park
                Robot.getDrive().encoderDrive(0.75, -76, -76, 4, this);

            }
            else{
                Robot.getDrive().encoderTurn(-90, 0.75, 2, this);
                if (goldAligned()){
                    Robot.getDrive().encoderDrive(0.8, 18, 18, 3, this);
                    Robot.getDrive().encoderDrive(0.8, -16, -16, 3, this);
                    Robot.getDrive().encoderTurn(-50, 0.65, 3, this);
                    Robot.getDrive().encoderDrive(0.8, -54, -54, 4, this);
                }
                else {
                    Robot.getDrive().encoderTurn(-50, 0.65, 3, this);
                    Robot.getDrive().encoderDrive(0.8, -52, -52, 4, this);
                }
                Robot.getDrive().encoderTurn(48, 0.65, 3, this);
                Robot.getDrive().encoderDrive(0.8, 52, 52, 3, this);

                //Deposit Team Marker
                Robot.getIntake().setIntakePower(0.7);
                setTimer1(750);
                Robot.getIntake().setIntakePower(0);


                //DriveBase to Crater
                Robot.getDrive().encoderDrive(0.9, -55, -55, 4, this);
            }
        }

        finalAction();
    }
}
