package com.team2753.archive.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.archive.Team753Linear;

import static com.team2753.archive.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 12/15/2018.
 */

@Autonomous(name = "Crater")
public class Crater_Sample extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater", true);

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

        //DriveBase Forward
        Robot.getDrive().encoderDrive(0.7, 12, 12, 3, this);

        //Lower lift
        Robot.getLift().unlock();
        Robot.getLift().setTarget(600);
        Robot.getLift().setPower(-0.75);

        //drive to depot to claim
        Robot.getDrive().encoderTurn(90, 0.8, 3, this);

        Robot.getDrive().encoderDrive(0.8, 44,44, 4, this);

        if(Robot.getLift().getAveragePosition()<=450){
            Robot.getLift().setPower(0);
        }

        Robot.getDrive().encoderTurn(45, 0.8, 3, this);

        if(Robot.getLift().getAveragePosition()<=450){
            Robot.getLift().setPower(0);
        }

        Robot.getIntake().setSlideRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.getIntake().setSlideTarget(1000);
        Robot.getIntake().setSlidePower(0.7);

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

        Robot.getIntake().intakeUp();

        Robot.getDrive().encoderTurn(-45, 0.75, 3, this);

        Robot.getDrive().encoderDrive(0.8, -44, -44, 4, this);

        Robot.getIntake().intakeCenter();

        //Robot.getDrive().encoderTurn(-90, 0.8, 3, this);

        Robot.getIntake().intakeDown();

        //sample gold mineral
        switch(goldPosition){
            case LEFT:
                Robot.getDrive().encoderTurn(-40, 0.75, 3, this);
                Robot.getIntake().intakeDown();
                Robot.getIntake().setIntakePower(1);
                setTimer1(500);
                Robot.getIntake().setSlideTarget(800);
                Robot.getIntake().setSlidePower(0.6);
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
                Robot.getDrive().encoderTurn(-45, 0.8, 3, this);
                break;
            case CENTER:
                Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                Robot.getIntake().intakeDown();
                Robot.getIntake().setIntakePower(1);
                setTimer1(500);
                Robot.getIntake().setSlideTarget(600);
                Robot.getIntake().setSlidePower(0.8);
                while (Robot.getIntake().getSlidePosition() <= 550) {}
                Robot.getIntake().setSlideTarget(250);
                Robot.getIntake().setSlidePower(0.85);
                while(Robot.getIntake().getSlidePosition()>= 300){}
                Robot.getIntake().setSlidePower(0);
                Robot.getIntake().setSlideTarget(750);
                Robot.getIntake().setSlidePower(0.8);
                while (Robot.getIntake().getSlidePosition() <= 700) {}
                Robot.getIntake().setSlideTarget(-50);
                Robot.getIntake().setSlidePower(0.85);
                while(Robot.getIntake().getSlidePosition()>= 0){}
                Robot.getIntake().setSlidePower(0);

                Robot.getIntake().intakeCenter();
                Robot.getIntake().setIntakePower(0.4);
                break;
            case RIGHT:
                Robot.getDrive().encoderTurn(-135, 0.75, 3, this);
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
                Robot.getDrive().encoderTurn(45, 0.8, 3, this);
                break;
            default:
                break;
        }

        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().intakeUp();
        Robot.getIntake().setIntakePower(-0.5);

        /*
        Robot.getDrive().encoderDrive(0.65, 0, -5, 2, this);

        Robot.getDrive().encoderDrive(0.75, -11.5, -11.5, 3, this);
        */

        Robot.getIntake().setIntakePower(0);

        /*
        Robot.getLift().setTarget(3800);
        Robot.getLift().setPower(0.8);
        Robot.getIntake().intakeCenter();
        Robot.getIntake().setSlideTarget(950);
        Robot.getIntake().setSlidePower(0.8);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() <= 3500){}
        setTimer1(1000);


        Robot.getDrive().encoderDrive(0.8, 11.5, 11.5, 3, this);
        */

        Robot.getLift().setTarget(600);
        Robot.getLift().setPower(-0.75);
        while(opModeIsActive() && Robot.getLift().getAveragePosition() >= 800){}
        Robot.getLift().setPower(0);


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
            //Mineral is in the center
            Robot.getDrive().encoderDrive(0.8, 16, 16, 3, this);
            Robot.getDrive().encoderDrive(0.8, -14, -14, 3, this);
            Robot.getDrive().encoderTurn(90, 0.75, 3, this);
            //drive to wall
            Robot.getDrive().encoderDrive(0.75, 45, 45, 4, this);

            //DriveBase to Depot
            Robot.getDrive().encoderTurnTest(50, 0.6, 4, this);
            Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

            //Deposit Team Marker
            //Robot.getMarker().deploy();
            Robot.getIntake().setIntakePower(0.5);
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

                //DriveBase to Depot
                Robot.getDrive().encoderTurnTest(45, 0.6, 4, this);
                Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

                //Deposit Team Marker
                //Robot.getMarker().deploy();
                Robot.getIntake().setIntakePower(0.5);
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

                    //DriveBase to Depot
                    Robot.getDrive().encoderTurnTest(50, 0.6, 4, this);
                    Robot.getDrive().encoderDrive(0.8, 40, 40, 3, this);

                    //Deposit Team Marker
                    //Robot.getMarker().deploy();
                    Robot.getIntake().setIntakePower(0.5);
                    setTimer1(750);
                    Robot.getIntake().setIntakePower(0);

                    //Return to Crater
                    Robot.getDrive().encoderDrive(0.8, -70, -70, 5, this);
                }
                else {
                    Robot.getDrive().encoderTurn(45, 0.65, 30, this);
                    Robot.getDrive().encoderDrive(0.8, 46, 46, 4, this);

                    //DriveBase to Depot
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
        */