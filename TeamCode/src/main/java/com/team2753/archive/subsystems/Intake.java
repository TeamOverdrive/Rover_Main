package com.team2753.archive.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.team2753.archive.Team753Linear;
import com.team2753.archive.subsystems.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Intake implements Subsystem {


    private DcMotor intakeMotor;
    private DcMotor slideMotor;
    private Servo intakeLift1;

    //DigitalChannel touch;


    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {

        intakeMotor = (DcMotor) linearOpMode.hardwareMap.get("intake");
        slideMotor = (DcMotor) linearOpMode.hardwareMap.get("intake_slide");

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //intakeLift2 = (Servo) linearOpMode.hardwareMap.get("intake_gate");
        intakeLift1 = (Servo) linearOpMode.hardwareMap.get("intake_flipper");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //touch = linearOpMode.hardwareMap.get(DigitalChannel.class, "slideStop");

        //touch.setMode(DigitalChannel.Mode.INPUT);

        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //gateDown();

        if(auto){
            setSlidePower(0);
            zeroSensors();
            intakeUp();
        }
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
        telemetry.addData("Slide Position", slideMotor.getCurrentPosition());
        telemetry.addData("Intake Servo 1 Position", intakeLift1.getPosition());
        telemetry.addData("Intake Slide Power", slideMotor.getPower());
        //telemetry.addData("Touch State", getTouchState());
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
        Range.clip(pos, -1, 1);
        intakeLift1.setPosition(pos);
        //intakeLift2.setPosition(1-pos);
    }


    public void intakeUp(){
        setIntakePosition(0);
    }

    public void intakeDown(){
        setIntakePosition(0.65);
    }

    public void intakeCenter(){
        setIntakePosition(0.3);
    }

    public void intakeAngledDown(){
        setIntakePosition(0.45);
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

    /*
    public boolean getTouchState(){
        return touch.getState();
    }

    public boolean getTouchPressed(){
        return !getTouchState();
    }

*/
    @Deprecated
    private void threadSleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
