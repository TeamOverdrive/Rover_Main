package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/16/2018.
 */
public class Drive implements Subsystem{

    //Vars

    //Drive Motors
    private DcMotor rightFront, leftFront, rightBack, leftBack = null;

    //Gyro



    @Override
    public void init(LinearOpMode linearOpMode, boolean auto) {
        rightBack = (DcMotor) linearOpMode.hardwareMap.get("right_back");
        rightFront = (DcMotor) linearOpMode.hardwareMap.get("right_front");
        leftBack = (DcMotor) linearOpMode.hardwareMap.get("left_back");
        leftFront = (DcMotor) linearOpMode.hardwareMap.get("left_front");


        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        if(!auto) {
            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {

    }

    public void setRunMode(DcMotor.RunMode runMode){
        rightFront.setMode(runMode);
        rightBack.setMode(runMode);
        leftFront.setMode(runMode);
        leftBack.setMode(runMode);
    }

    public void setLeftRightPower(double leftPower, double rightPower){
        leftPower = Range.clip(leftPower, -1., 1.);
        rightPower = Range.clip(rightPower, -1., 1.);

        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftBack.setZeroPowerBehavior(zeroPowerBehavior);
        rightBack.setZeroPowerBehavior(zeroPowerBehavior);
    }




}
