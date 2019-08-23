package com.team2753.archive.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.team2753.archive.Team753Linear;
import com.team2753.archive.subsystems.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Lift implements Subsystem {

    private static final double HANGPOS = 0;
    private static final double SCOREPOS = 0.4;

    //shifter2 encoder is on the scoring slide does it even work lul
    private DcMotor master, slave;
    private Servo shifter, dumperLeft, dumperRight;

    private static final double brakePower = 0;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {
        slave = (DcMotor) linearOpMode.hardwareMap.get("shifter1");
        master = (DcMotor) linearOpMode.hardwareMap.get("shifter2");

        master.setDirection(FORWARD);
        master.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slave.setDirection(REVERSE);
        slave.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slave.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shifter = (Servo) linearOpMode.hardwareMap.get("shifter");

        dumperLeft = (Servo) linearOpMode.hardwareMap.get("dumper_left");
        dumperRight = (Servo) linearOpMode.hardwareMap.get("dumper_right");
        //zeroSensors();

        if(auto){
            shiftToHang();
            setPower(0.6);
            threadSleep(750);
            setPower(0);
            threadSleep(100);
            zeroSensors();
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //setPower(-0.5);
        }
        else{
            shiftToScore();
        }
    }

    @Override
    public void zeroSensors() {
        stop();
        /*
        while(getShifter1Position()!=0 && getShifter2Position()!=0)

            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/

        while(getMasterPosition()!=0) {
            master.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        while(getSlavePosition()!=0){
            slave.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    @Override
    public void stop() {
        setPower(0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Lift Master Power", getMasterPower());
        telemetry.addData("Lift Slave Power Difference", getPowerDifference());
        telemetry.addData("Lift Master Position", getMasterPosition());
        telemetry.addData("Scorer Slide Position", getSlavePosition());
        telemetry.addData("Shifter Position", getShifterPosition());
    }

    //TODO: fix methods to reflect hardware changes

    public int getScorerEncoder(){
        return slave.getCurrentPosition();
    }

    public void updateSlaveMotor(){
        slave.setPower(getMasterPower());
    }

    public void setDumperPosition(double pos){
        Range.clip(pos, 0, 1);
        dumperLeft.setPosition(1-pos);
        dumperRight.setPosition(pos);
    }

    /**
     * old stuff
     *
     */
    public void setPower(double power){
        master.setPower(power);
        slave.setPower(power);
    }


    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior mode){
        master.setZeroPowerBehavior(mode);
        slave.setZeroPowerBehavior(mode);
    }

    @Deprecated
    public void brake(){
        setPower(brakePower);
    }

    public int getMasterPosition(){return master.getCurrentPosition();}

    public int getSlavePosition(){return slave.getCurrentPosition();}

    /*
    @Deprecated
    public int getAveragePosition(){
        int position = ((getShifter1Position()+getShifter2Position())/2);
        return position;
    }
    */

    public double getMasterPower(){return master.getPower();}

    public double getSlavePower(){return slave.getPower();}


    public double getAveragePower(){
        double power = ((getMasterPower()+getSlavePower())/2);
        return power;
    }

    public double getPowerDifference(){
        return Math.abs(getMasterPower()-getSlavePower());
    }

    /**
     * be careful with this
     * @param runMode
     */

    public void setRunMode(DcMotor.RunMode runMode){
        master.setMode(runMode);
        //slave.setMode(runMode);
    }

    @Deprecated
    public DcMotor.RunMode getMode(){
        return master.getMode();
    }

    //@Deprecated
    public void setTarget(int target){
        master.setTargetPosition(target);
        slave.setTargetPosition(target);
    }

    public boolean getLiftBusy(){
        if(master.isBusy())
            return true;
        else
            return false;
    }

    public void setShifterPosition(double pos){
        shifter.setPosition(pos);
    }

    public double getShifterPosition(){
        return shifter.getPosition();
    }

    public void shiftToHang(){
        setShifterPosition(HANGPOS);
    }

    public void shiftToScore(){
        setShifterPosition(SCOREPOS);
    }

    /*
    public void runToPosition(double target, double speed, Team753Linear linearOpMode){

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
