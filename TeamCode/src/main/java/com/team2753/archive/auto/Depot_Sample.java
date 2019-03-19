package com.team2753.archive.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.archive.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 12/15/2018.
 */
@Autonomous(name = "Depot")
public class Depot_Sample extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Depot", true);

        //Deploy intake
        Robot.getIntake().intakeCenter();

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

        //Extend
        Robot.getIntake().setSlideRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getIntake().setSlideTarget(1000);
        Robot.getIntake().setSlidePower(0.7);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.7, 13.5, 13.5, 3, this);

        //Lower lift
        Robot.getLift().unlock();
        Robot.getLift().setTarget(600);
        Robot.getLift().setPower(-0.75);

        //Extend out to drop team marker
        while (Robot.getIntake().getSlidePosition() <= 800) {}

        //Drop
        Robot.getIntake().intakeAngledDown();
        Robot.getIntake().setIntakePower(-0.6);
        setTimer1(750);
        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().intakeCenter();
        Robot.getIntake().setSlideTarget(0);
        Robot.getIntake().setSlidePower(0.85);

        while(Robot.getIntake().getSlidePosition()>= 50){}
        Robot.getIntake().setSlidePower(0);
        if(Robot.getLift().getAveragePosition()<=450){
            Robot.getLift().setPower(0);
        }



        //sample gold mineral
        switch(goldPosition){
            case LEFT:
                Robot.getDrive().encoderTurn(45, 0.8, 3, this);
                break;
            case CENTER:
                break;
            case RIGHT:
                Robot.getDrive().encoderTurn(-45, 0.8, 3, this);
                break;
                default:
                    break;
        }

        if(Robot.getLift().getAveragePosition()<=450){
            Robot.getLift().setPower(0);
        }

        Robot.getIntake().intakeDown();
        Robot.getIntake().setIntakePower(1);
        setTimer1(500);
        Robot.getIntake().setSlideTarget(800);
        Robot.getIntake().setSlidePower(0.8);
        while (Robot.getIntake().getSlidePosition() <= 750) {}
        Robot.getIntake().setSlideTarget(250);
        Robot.getIntake().setSlidePower(0.85);
        while(Robot.getIntake().getSlidePosition()>= 300){}
        Robot.getIntake().setSlidePower(0);
        Robot.getIntake().setSlideTarget(950);
        Robot.getIntake().setSlidePower(0.8);
        while (Robot.getIntake().getSlidePosition() <= 800) {}
        Robot.getIntake().setSlideTarget(-50);
        Robot.getIntake().setSlidePower(0.85);
        while(Robot.getIntake().getSlidePosition()>= 0){}
        Robot.getIntake().setSlidePower(0);

        Robot.getIntake().intakeCenter();
        Robot.getIntake().setIntakePower(0.4);

        switch(goldPosition){
            case LEFT:
                Robot.getDrive().encoderTurn(-45, 0.8, 3, this);
                break;
            case CENTER:
                break;
            case RIGHT:
                Robot.getDrive().encoderTurn(45, 0.8, 3, this);
                break;
            default:
                break;
        }

        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().intakeUp();
        Robot.getIntake().setIntakePower(-0.95);

        Robot.getDrive().encoderDrive(0.8, -10, -10, 3, this);

        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().intakeCenter();

        Robot.getLift().setTarget(3800);
        Robot.getLift().setPower(0.8);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3500){}
        setTimer1(1000);

        Robot.getDrive().encoderDrive(0.8, 10, 10, 3, this);


        Robot.getLift().setTarget(600);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 800){}
        Robot.getLift().setPower(0);

        //drive to crater/autocycles

        Robot.getDrive().encoderTurn(90, 0.8, 3, this);

        Robot.getIntake().intakeUp();

        Robot.getDrive().encoderDrive(0.8, 43,43, 4, this);

        Robot.getDrive().encoderTurn(45, 0.8, 3, this);

        Robot.getIntake().intakeCenter();

        Robot.getIntake().setSlideTarget(950);
        Robot.getIntake().setSlidePower(0.8);
        while (Robot.getIntake().getSlidePosition() <= 800) {}
        Robot.getIntake().setSlidePower(0);

        finalAction();
    }
}


//old sampling code

        /*
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
        */
