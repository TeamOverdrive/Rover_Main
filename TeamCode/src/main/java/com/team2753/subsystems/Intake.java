package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.team2753.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Intake implements Subsystem {


    private DcMotor intakeMotor;
    private DcMotor slideMotor;
    private Servo intakeGate;

    private double gateDownPos = 1;
    private double gateUpPos = 0;


    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {

        intakeMotor = (DcMotor) linearOpMode.hardwareMap.get("intake");
        slideMotor = (DcMotor) linearOpMode.hardwareMap.get("slide");

        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeGate = (Servo) linearOpMode.hardwareMap.get("intake_gate");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gateDown();

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
        telemetry.addData("Gate Position", intakeGate.getPosition());
    }

    private double oneRotation = ((1.0/2.25)/8.0);
    private double oneDegree = (oneRotation/360);

    public void setGateAngle(double angle){
        intakeGate.setPosition(angle*oneDegree);
    }

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

    public void gateDown(){
        setGatePosition(gateDownPos);
    }

    public void gateUp(){
        setGatePosition(gateUpPos);
    }

    public void setGatePosition(double position){
        intakeGate.setPosition(position);
    }

    private void threadSleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
