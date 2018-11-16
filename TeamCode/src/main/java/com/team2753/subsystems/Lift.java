package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.team2753.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Lift implements Subsystem {

    //Vars

    private DcMotor leftLift, rightLift;
    private Servo liftLock;

    private static final double brakePower = 0;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {
        leftLift = (DcMotor) linearOpMode.hardwareMap.get("left_lift");
        rightLift = (DcMotor) linearOpMode.hardwareMap.get("right_lift");

        rightLift.setDirection(REVERSE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setDirection(FORWARD);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //TODO: @hardware add lift_lock to hub 1 servo port 1
        //liftLock = (Servo) linearOpMode.hardwareMap.get("lift_lock");

        //TODO: add limit switch (maybe rev magnetic switch?)

        //TODO: add a rev distance sensor (should get v2? we already have v1)
    }

    @Override
    public void zeroSensors() {
        stop();
    }

    @Override
    public void stop() {
        setPower(0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Lift position", getPosition());
        telemetry.addData("Lift Power", getPower());
    }

    public void setPower(double power){
        leftLift.setPower(power);
        rightLift.setPower(power);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior mode){
        rightLift.setZeroPowerBehavior(mode);
        leftLift.setZeroPowerBehavior(mode);
    }

    public void brake(){
        setPower(brakePower);
    }

    public int getLeftPosition(){return leftLift.getCurrentPosition();}

    public int getRightPosition(){return rightLift.getCurrentPosition();}

    public int getPosition(){
        int position = ((getLeftPosition()+getRightPosition())/2);
        return position;
    }

    public double getLeftPower(){return leftLift.getPower();}

    public double getRightPower(){return rightLift.getPower();}

    public double getPower(){
        double position = ((getLeftPower()+getRightPower())/2);
        return position;
    }
}
