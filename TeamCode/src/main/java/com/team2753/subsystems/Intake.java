package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.team2753.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Intake implements Subsystem {


    private DcMotor intakeMotor;
    private DcMotor slideMotor;
    private Servo intakeLift1, intakeLift2;

    private double gateDownPos = 1;
    private double gateUpPos = 0.15;


    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {

        intakeMotor = (DcMotor) linearOpMode.hardwareMap.get("intake");
        slideMotor = (DcMotor) linearOpMode.hardwareMap.get("slide");

        //TODO fix directions
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLift2 = (Servo) linearOpMode.hardwareMap.get("intake_gate");
        intakeLift1 = (Servo) linearOpMode.hardwareMap.get("intake_lift");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //gateDown();

        if(auto){
            setSlidePower(-0.6);
            threadSleep(750);
            setSlidePower(0);
            zeroSensors();
            threadSleep(100);
        }

        //TODO: add intake motors
        //TODO: add rev touch sensor

    }

    @Override
    public void zeroSensors() {
        stop();
        while(getSlidePosition() != 0)
            setSlideRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void stop() {
        intakeMotor.setPower(0);
        slideMotor.setPower(0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Intake Position", slideMotor.getCurrentPosition());
        telemetry.addData("Lift 1 Position", intakeLift1.getPosition());
        telemetry.addData("Lift 2 Position", intakeLift2.getPosition());
    }

    private double oneRotation = ((1.0/2.25)/8.0);
    private double oneDegree = (oneRotation/360);

    /*public void setGateAngle(double angle){
        intakeGate.setPosition(angle*oneDegree);
    }
    */

    public void setIntakePower(double power){
        intakeMotor.setPower(power);
    }

    public void setSlidePower(double power){slideMotor.setPower(power);}

    public int getSlidePosition(){return slideMotor.getCurrentPosition();}

    public double getSlidePower(){return slideMotor.getPower();}

    public void setSlideRunMode(DcMotor.RunMode runmode)
    {
        slideMotor.setMode(runmode);
    }

    public void setSlideTarget(int target){
        slideMotor.setTargetPosition(target);
    }

    public void setIntakePosition(double pos){
        Range.clip(pos, 0, 1);
        intakeLift1.setPosition(pos);
        intakeLift2.setPosition(1-pos);
    }


    public void intakeUp(){
        setIntakePosition(0);
    }

    public void intakeDown(){
        setIntakePosition(0.95);
    }

    public void intakeCenter(){
        setIntakePosition(0.35);
    }

    /*

    public void gateDown(){
        setGatePosition(gateDownPos);
    }

    public void gateUp(){
        setGatePosition(gateUpPos);
    }


    public void setGatePosition(double position){
        intakeGate.setPosition(position);
    }
    */

    private void threadSleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
