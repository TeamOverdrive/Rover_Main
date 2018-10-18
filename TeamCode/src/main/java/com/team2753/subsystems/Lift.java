package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Lift implements Subsystem {

    //Vars

    private DcMotor leftLift, rightLift = null;

    private static final double brakepower = 0;

    @Override
    public void init(LinearOpMode linearOpMode, boolean auto) {
        leftLift = (DcMotor) linearOpMode.hardwareMap.get("left_lift");
        rightLift = (DcMotor) linearOpMode.hardwareMap.get("right_lift");

        rightLift.setDirection(REVERSE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setDirection(FORWARD);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {
        brake();
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {

    }

    public void setPower(double power){
        leftLift.setPower(power);
        rightLift.setPower(power);
    }

    public void brake(){
        setPower(brakepower);
    }

    public int getLeftPosition(){
        return leftLift.getCurrentPosition();
    }

    public int getRightPosition(){
        return getRightPosition();
    }

    public int getPosition(){
        int position = ((getLeftPosition()+getRightPosition())/2);
        return position;
    }
}
