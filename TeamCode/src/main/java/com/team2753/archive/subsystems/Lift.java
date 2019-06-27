package com.team2753.archive.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.team2753.archive.Team753Linear;
import com.team2753.archive.subsystems.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Lift implements Subsystem {
    private static final double HANGPOS = 0;//could be 1
    private static final double SCOREPOS = 0.5;

    //Vars

    //shifter2 encoder is on the scoring slide does it even work lul
    private DcMotor motor1, motor2;
    private Servo shifter;

    public double lockPosition = 0.2;
    public double unlockPosition = 0.8;

    private static final double brakePower = 0;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {
        motor1 = (DcMotor) linearOpMode.hardwareMap.get("shifter1");
        motor2 = (DcMotor) linearOpMode.hardwareMap.get("shifter2");

        motor1.setDirection(REVERSE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor2.setDirection(FORWARD);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shifter = (Servo) linearOpMode.hardwareMap.get("shifter");

        if(auto){
            setPower(-0.6);
            threadSleep(500);
            setPower(0);
            //lock();
            threadSleep(100);
            zeroSensors();
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void zeroSensors() {
        stop();
        while(getShifter1Position()!=0 && getShifter2Position()!=0)
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
        //telemetry.addData("Lock Position", getLockPosition());
    }

    public void setPower(double power){
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior mode){
        motor1.setZeroPowerBehavior(mode);
        motor2.setZeroPowerBehavior(mode);
    }

    public void brake(){
        setPower(brakePower);
    }

    public int getShifter1Position(){return motor1.getCurrentPosition();}

    public int getShifter2Position(){return motor2.getCurrentPosition();}

    public int getAveragePosition(){
        int position = ((getShifter1Position()+getShifter2Position())/2);
        return position;
    }

    public double getShifter1Power(){return motor1.getPower();}

    public double getShifter2Power(){return motor2.getPower();}

    public double getPower(){
        double power = ((getShifter1Power()+getShifter2Power())/2);
        return power;
    }

    public void setRunMode(DcMotor.RunMode runMode){
        motor1.setMode(runMode);
        motor2.setMode(runMode);
    }

    @Deprecated
    public DcMotor.RunMode getMode(){
        return motor2.getMode();
    }

    @Deprecated
    public void setTarget(int target){
        motor1.setTargetPosition(target);
        motor2.setTargetPosition(target);
    }

    public boolean getLiftBusy(){
        if(motor1.isBusy())
            return true;
        else
            return false;
    }

    public void setShifterPosition(double pos){
        shifter.setPosition(pos);
    }

    public void shiftToHang(){
        setShifterPosition(HANGPOS);
    }

    public void shiftToScore(){
        setShifterPosition(SCOREPOS);
    }

    private void threadSleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
