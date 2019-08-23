package com.team2753.archive.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.archive.Team753Linear;

import static com.team2753.archive.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Depot", group = "0_auto")

public class Depot extends Team753Linear{
    @Override
    public void runOpMode(){

        waitForStart("Depot", true);

        //Deploy intake
        Robot.getIntake().intakeCenter();

        //Land

        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Robot.getLift().setPower(-1);
        setTimer1(3000);
        Robot.getLift().setPower(0);


        //Extend
        Robot.getIntake().setSlidePower(1);

        Robot.getLift().setPower(0.75);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.7, 12, 12, 3, this);

        Robot.getIntake().setSlidePower(0);


        Robot.getLift().setPower(0);
        //Robot.getLift().shiftToScore();

        Robot.getIntake().intakeAngledDown();
        Robot.getIntake().setIntakePower(-1);
        setTimer1(1000);

        Robot.getIntake().intakeCenter();
        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().setSlidePower(-1);

        //sample gold mineral
        switch(goldPosition){
            case LEFT:
                Robot.getDrive().encoderTurn(45, 0.8, 3, this);
                break;
            case CENTER:
                setTimer1(1500);
                break;
            case RIGHT:
                Robot.getDrive().encoderTurn(-45, 0.8, 3, this);
                break;
            default:
                setTimer1(1500);
                break;
        }

        Robot.getIntake().intakeDown();
        Robot.getIntake().setIntakePower(1);
        setTimer1(500);
        //collect sample

        Robot.getIntake().setSlidePower(1);
        setTimer1(2000);
        Robot.getIntake().intakeCenter();
        Robot.getIntake().setSlidePower(-1);
        setTimer1(2000);
        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().setSlidePower(0);

        //drive to crater/autocycles

        switch(goldPosition){
            case LEFT:
                Robot.getDrive().encoderTurn(45, 0.8, 3, this);
                break;
            case CENTER:
                Robot.getDrive().encoderTurn(90, 0.8, 3, this);
                break;
            case RIGHT:
                Robot.getDrive().encoderTurn(135, 0.8, 3, this);
                break;
            default:
                Robot.getDrive().encoderTurn(90, 0.8, 3, this);
                break;
        }

        Robot.getIntake().intakeUp();

        Robot.getDrive().encoderDrive(0.8, 42,42, 4, this);

        Robot.getDrive().encoderTurn(45, 0.8, 3, this);

        Robot.getIntake().intakeCenter();

        Robot.getIntake().setSlidePower(1);
        setTimer1(1000);
        Robot.getIntake().setSlidePower(0);

        finalAction();
    }
}
