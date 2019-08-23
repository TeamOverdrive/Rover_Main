package com.team2753.archive.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.archive.Team753Linear;
import com.team2753.archive.subsystems.Robot;

import static com.team2753.archive.Constants.COUNTS_PER_INCH;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Crater", group = "0_auto")

public class Crater extends Team753Linear{
    @Override
    public void runOpMode(){

        waitForStart("Crater", true);

        //Deploy intake
        Robot.getIntake().intakeCenter();

        //Land

        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Robot.getLift().setPower(-1);
        setTimer1(3000);
        Robot.getLift().setPower(0);

        //Drive Forward
        Robot.getDrive().encoderDrive(0.7, 12, 12, 3, this);

        //Lower lift
        Robot.getLift().setPower(0.75);

        //drive to depot to claim
        Robot.getDrive().encoderTurn(90, 0.8, 3, this);

        Robot.getLift().setPower(0);
        //Robot.getLift().shiftToScore();

        Robot.getDrive().encoderDrive(0.8, 44,44, 4, this);

        Robot.getDrive().encoderTurn(45, 0.8, 3, this);

        /*
        Robot.getIntake().setSlidePower(1);
        setTimer1(2000);
        Robot.getIntake().intakeAngledDown();
        Robot.getIntake().setSlidePower(0);

        Robot.getIntake().setIntakePower(-1);
        setTimer1(1000);
        Robot.getIntake().setIntakePower(0);
        Robot.getIntake().intakeCenter();

        Robot.getIntake().setSlidePower(-1);
        */

        Robot.getDrive().encoderDrive(1,48, 48, 3, this);
        Robot.getIntake().intakeAngledDown();
        Robot.getIntake().setIntakePower(-1);
        setTimer1(750);
        Robot.getIntake().setIntakePower(0);
        Robot.getDrive().encoderDrive(1, -48, -48, 3, this);


        Robot.getDrive().encoderTurn(-45, 0.75, 3, this);

        Robot.getIntake().setSlidePower(0);

        Robot.getDrive().encoderDrive(0.8, -44, -44, 4, this);

        //Robot.getDrive().encoderTurn(-90, 0.8, 3, this);

        Robot.getIntake().intakeDown();

        //sample gold mineral
        switch(goldPosition){
            case LEFT:
                Robot.getDrive().encoderTurn(-40, 0.75, 3, this);
                Robot.getDrive().encoderDrive(.8, 16, 16,3, this);
                Robot.getDrive().encoderDrive(.8, -16, -16,3, this);
                Robot.getDrive().encoderTurn(-40, 0.75, 3, this);
                break;
            case CENTER:
                Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                Robot.getIntake().intakeDown();
                Robot.getIntake().setIntakePower(1);
                setTimer1(500);
                break;
            case RIGHT:
                Robot.getDrive().encoderTurn(-135, 0.75, 3, this);
                Robot.getIntake().intakeDown();
                Robot.getIntake().setIntakePower(1);
                setTimer1(500);
            default:
                Robot.getDrive().encoderTurn(-90, 0.75, 3, this);
                Robot.getIntake().intakeDown();
                Robot.getIntake().setIntakePower(1);
                setTimer1(500);
                break;
        }
        finalAction();
    }
}
