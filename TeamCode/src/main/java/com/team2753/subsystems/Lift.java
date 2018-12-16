package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

    public double lockPosition = 0.8;
    public double unlockPosition = 0.2;

    private static final double brakePower = 0;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {
        leftLift = (DcMotor) linearOpMode.hardwareMap.get("left_lift");
        rightLift = (DcMotor) linearOpMode.hardwareMap.get("right_lift");

        rightLift.setDirection(REVERSE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setDirection(FORWARD);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftLock = (Servo) linearOpMode.hardwareMap.get("lift_lock");

        //TODO: add limit switch (maybe rev magnetic switch?)

        //TODO: add a rev distance sensor

        unlock();
        if(auto){
            setPower(-0.6);
            threadSleep(500);
            setPower(0);
            lock();
            threadSleep(100);
            zeroSensors();
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void zeroSensors() {
        stop();
        while(getLeftPosition()!=0 && getRightPosition()!=0)
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void stop() {
        setPower(0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Lift position", getAveragePosition());
        telemetry.addData("Lift Power", getPower());
        telemetry.addData("Lock Position", getLockPosition());
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

    public int getAveragePosition(){
        int position = ((getLeftPosition()+getRightPosition())/2);
        return position;
    }

    public double getLeftPower(){return leftLift.getPower();}

    public double getRightPower(){return rightLift.getPower();}

    public double getPower(){
        double power = ((getLeftPower()+getRightPower())/2);
        return power;
    }

    public void setRunMode(DcMotor.RunMode runMode){
        rightLift.setMode(runMode);
        leftLift.setMode(runMode);
    }

    public void setTarget(int target){
        rightLift.setTargetPosition(target);
        leftLift.setTargetPosition(target);
    }

    public boolean getLiftBusy(){
        if(leftLift.isBusy() || rightLift.isBusy())
            return true;
        else
            return false;
    }

    /*
    @Deprecated
    public void runToPosition(int target){
        setTarget(target);
        if (leftLift.getMode() != DcMotor.RunMode.RUN_TO_POSITION || rightLift.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        setPower(1);
    }

    @Deprecated
    public void fullUp(){
        setTarget(3800);
    }
    */

    public void lockSetPosition(double position){
        liftLock.setPosition(position);
    }

    public void unlock(){
        lockSetPosition(unlockPosition);
    }

    public void lock(){
        lockSetPosition(lockPosition);
    }

    public double getLockPosition(){
        double position = liftLock.getPosition();
        return position;
    }

    public boolean isLocked(){
        return getLockPosition() != lockPosition;
    }

    public boolean shouldLiftStop(){
        if(getAveragePosition()>=25){
            return true;
        }
        else return false;
    }

    private void threadSleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
