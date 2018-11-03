package com.team2753.subsystems;

import com.qualcomm.hardware.motors.NeveRest40Gearmotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team2753.Team753Linear;
import com.team2753.libs.hardware.RevIMU;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

import static com.team2753.Constants.COUNTS_PER_INCH;
import static com.team2753.Constants.WHEEL_BASE;
import static java.lang.Math.PI;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/16/2018.
 */
public class Drive implements Subsystem{


    //TODO: Compare to last year's drive class to see if i'm missing anything important
    //TODO: Add gyro turns



    public static final MotorConfigurationType MOTOR_CONFIG = MotorConfigurationType.getMotorType(NeveRest40Gearmotor.class);

    private static final double TICKS_PER_REV = MOTOR_CONFIG.getTicksPerRev();

    //TODO: tune this
    //public static final PIDCoefficients NORMAL_VELOCITY_PID = new PIDCoefficients(20, 8, 12);


    private DcMotorEx leftFront, leftBack, rightBack, rightFront;
    private List<DcMotorEx> motors;

    private RevIMU imu1;

    /*
    TODO: Get rr working after Nov 11.
    public Drive(double trackWidth, NanoClock clock) {
        super(trackWidth, clock);
    }
    */

    //private LinearOpMode linearOpMode = null;
    private ElapsedTime timeout = new ElapsedTime();

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {

        //super(1);

        rightBack = linearOpMode.hardwareMap.get(DcMotorEx.class, "right_back");
        rightFront = linearOpMode.hardwareMap.get(DcMotorEx.class, "right_front");
        leftBack = linearOpMode.hardwareMap.get(DcMotorEx.class, "left_back");
        leftFront = linearOpMode.hardwareMap.get(DcMotorEx.class, "left_front");


        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);


        if(auto){
            imu1 = new RevIMU("imu 1", linearOpMode.hardwareMap);
        }
    }

    @Override
    public void zeroSensors() {
        stop();
        while(getLeftCurrentPosition()!=0 && getRightCurrentPosition()!=0)
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void stop() {
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setLeftRightPower(0,0);
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

    //@Deprecated
    public void setLeftRightPower(double leftPower, double rightPower){
        leftPower = Range.clip(leftPower, -1., 1.);
        rightPower = Range.clip(rightPower, -1., 1.);

        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);

    }

    public void setLeftRightTarget(int leftTarget, int rightTarget){
        leftFront.setTargetPosition(leftTarget);
        leftBack.setTargetPosition(leftTarget);
        rightBack.setTargetPosition(rightTarget);
        rightFront.setTargetPosition(rightTarget);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){

        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftBack.setZeroPowerBehavior(zeroPowerBehavior);
        rightBack.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public int getLeftCurrentPosition(){
        int position = (leftBack.getCurrentPosition()+leftFront.getCurrentPosition())/2;
        return position;
    }

    public int getRightCurrentPosition(){
        int position = (rightBack.getCurrentPosition()+rightFront.getCurrentPosition())/2;
        return position;
    }

    public double getGyroAngleDegrees() {
        try {
            return (imu1.getNormalHeading());
        } catch (Exception e){
            return 0;
        }

    }

    //TODO: Get encoder drive working and add encoder and gyro turns

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS, Team753Linear linearOpMode) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (linearOpMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = getLeftCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = getRightCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            setLeftRightTarget(newLeftTarget, newRightTarget);
            //int counter1 = 0;
            //int counter2 = 0;

            // Turn On RUN_TO_POSITION
            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            timeout.reset();
            setLeftRightPower(Math.abs(speed), Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (linearOpMode.opModeIsActive() &&
                    (timeout.seconds() < timeoutS) &&
                    (leftBack.isBusy() || rightBack.isBusy() || leftFront.isBusy() || rightFront.isBusy())) {
                linearOpMode.updateTelemetry();
                //slow the motors down to half the original speed when we get within 4 inches of our target and the speed is greater than 0.1.
                if ((Math.abs(newLeftTarget - getLeftCurrentPosition()) < (4.0 * COUNTS_PER_INCH))
                        && (Math.abs(newRightTarget - getRightCurrentPosition()) < (4.0 * COUNTS_PER_INCH))
                        && speed > 0.1) {
                    setLeftRightPower(Math.abs(speed * 0.75), Math.abs(speed * 0.75));
                }
                //slow the motors down to 0.35 of the original speed when we get within 2 inches of our target and the speed is greater than 0.1.
                if ((Math.abs(newLeftTarget - getLeftCurrentPosition()) < (2.0 * COUNTS_PER_INCH))
                        && (Math.abs(newRightTarget - getRightCurrentPosition()) < (2.0 * COUNTS_PER_INCH))
                        && speed > 0.1) {
                    setLeftRightPower(Math.abs(speed * 0.3), Math.abs(speed * 0.3));
                }
            }
            // Stop all motion;
            setLeftRightPower(0,0);

            // Turn off RUN_TO_POSITION
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  linearOpMode.sleep(250);   // optional pause after each move
        }
    }

    @Deprecated
    public void turnCW(double degrees, double speed, double timeoutS, Team753Linear linearOpMode){

        double leftDistance = (WHEEL_BASE*PI*degrees)/-360;
        double rightDistance = (WHEEL_BASE*PI*degrees)/360;

        encoderDrive(speed, leftDistance, rightDistance, timeoutS, linearOpMode);

    }

    @Deprecated
    public void turnCCW(double degrees, double speed, double timeoutS, Team753Linear linearOpMode){

        double leftDistance = (WHEEL_BASE*PI*degrees)/360;
        double rightDistance = (WHEEL_BASE*PI*degrees)/-360;

        encoderDrive(speed, leftDistance, rightDistance, timeoutS, linearOpMode);

    }

    /**
     * Turns the robot in place using the drive motor encoders only
     * @param degrees rotational degrees; positive is counter-clockwise and negative is clockwise
     * @param speed motor speed
     * @param timeoutS timeout in seconds for this movement
     * @param linearOpMode  opmode
     */

    public void encoderTurn(double degrees, double speed, double timeoutS, Team753Linear linearOpMode){

        double leftDistance = (WHEEL_BASE*PI*degrees)/360;
        double rightDistance = (WHEEL_BASE*PI*degrees)/-360;

        encoderDrive(speed, leftDistance, rightDistance, timeoutS, linearOpMode);

    }

    private void threadSleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /*
    Copied from roadrunner example

    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        return null;
    }

    @Override
    public void setMotorPowers(double v, double v1) {
        setLeftRightPower(v,v1);
    }
    */
}
