package com.team2753.archive.subsystems;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.hardware.motors.NeveRest40Gearmotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team2753.archive.Team753Linear;
import com.team2753.archive.libs.hardware.RevIMU;
import com.team2753.archive.subsystems.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

import static com.team2753.archive.Constants.COUNTS_PER_INCH;
import static com.team2753.archive.Constants.WHEEL_BASE;
import static java.lang.Math.PI;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/16/2018.
 */
public class Drive implements Subsystem {

    public static final MotorConfigurationType MOTOR_CONFIG = MotorConfigurationType.getMotorType(NeveRest40Gearmotor.class);

    private static final double TICKS_PER_REV = MOTOR_CONFIG.getTicksPerRev();

    //private PIDFCoefficients coefficients;

    private DcMotorEx leftFront, leftBack, rightBack, rightFront;
    private List<DcMotorEx> motors;

    private RevIMU imu, imu_1;

    private ElapsedTime timeout = new ElapsedTime();

    double kP = 0;
    double kI = 0;
    double kD = 0;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {

        rightBack = linearOpMode.hardwareMap.get(DcMotorEx.class, "right_back");
        rightFront = linearOpMode.hardwareMap.get(DcMotorEx.class, "right_front");
        leftBack = linearOpMode.hardwareMap.get(DcMotorEx.class, "left_back");
        leftFront = linearOpMode.hardwareMap.get(DcMotorEx.class, "left_front");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*
        rightFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(kP,kI,kD,0));
        rightBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(kP,kI,kD,0));
        leftFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(kP,kI,kD,0));
        rightBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(kP,kI,kD,0));
*/
        if(auto){
            //imu = new RevIMU("imu", linearOpMode.hardwareMap);
            //imu_1 = new RevIMU("imu_1", linearOpMode.hardwareMap);
            zeroSensors();
        }
    }

    @Override
    public void zeroSensors() {
        stop();
        while(getLeftCurrentPosition()!=0 && getRightCurrentPosition()!=0)
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void stop() {
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setLeftRightPower(0,0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Power", getLeftPower());
        telemetry.addData("Right Power", getRightPower());
        telemetry.addData("Left Pos", getLeftCurrentPosition());
        telemetry.addData("Right Pos", getRightCurrentPosition());
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

    public double getLeftPower(){
        double power = (leftBack.getPower()+leftFront.getPower())/2;
        return power;
    }

    public double getRightPower(){
        double power = (rightBack.getPower()+rightFront.getPower())/2;
        return power;
    }

    public double getGyroAngleDegrees() {
        try {
            return (imu.getNormalHeading());
        } catch (Exception e){
            return 0;
        }

    }

    //TODO: add gyro turns

    public void runToPosition(double speed, double leftInches, double rightInches, Team753Linear linearOpMode) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (linearOpMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = getLeftCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = getRightCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            setLeftRightTarget(newLeftTarget, newRightTarget);

            // Turn On RUN_TO_POSITION
            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
            setLeftRightPower(Math.abs(speed), Math.abs(speed));
        }
    }



    /**
     *
     * @param speed motor speed
     * @param leftInches how far should the left side run
     * @param rightInches how far should the right side run
     * @param timeoutS  timout length
     * @param linearOpMode this
     */

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
                //slow the motors down to half the original speed when we get within 5 inches of our target and the speed is greater than 0.1.
                if ((Math.abs(newLeftTarget - getLeftCurrentPosition()) < (5.0 * COUNTS_PER_INCH))
                        && (Math.abs(newRightTarget - getRightCurrentPosition()) < (5.0 * COUNTS_PER_INCH))
                        && speed > 0.1) {

                    setLeftRightPower(Math.abs(speed * 0.75), Math.abs(speed * 0.75));
                }
                //slow the motors down to 0.35 of the original speed when we get within 3 inches of our target and the speed is greater than 0.1.
                if ((Math.abs(newLeftTarget - getLeftCurrentPosition()) < (3.0 * COUNTS_PER_INCH))
                        && (Math.abs(newRightTarget - getRightCurrentPosition()) < (3.0 * COUNTS_PER_INCH))
                        && speed > 0.1) {
                    if(Math.abs(speed*0.3)>0.2) {
                        setLeftRightPower(Math.abs(speed * 0.3), Math.abs(speed * 0.3));
                    }
                    else{
                        setLeftRightPower(0.2, 0.2);
                    }
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

        double leftDistance = (WHEEL_BASE*PI*degrees)/-360;
        double rightDistance = (WHEEL_BASE*PI*degrees)/360;

        encoderDrive(speed, leftDistance, rightDistance, timeoutS, linearOpMode);
    }

    public void encoderTurnTest(double degrees, double speed, double timeoutS, Team753Linear linearOpMode){

        double leftDistance = (WHEEL_BASE*PI*degrees)/-360;
        double rightDistance = (WHEEL_BASE*PI*degrees)/360;

        //encoderDrive(speed, leftDistance, rightDistance, timeoutS, linearOpMode);
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (linearOpMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = getLeftCurrentPosition() + (int) (leftDistance * COUNTS_PER_INCH);
            newRightTarget = getRightCurrentPosition() + (int) (rightDistance * COUNTS_PER_INCH);
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
                    setLeftRightPower(0.25, 0.25);
                }
            }
            // Stop all motion;
            setLeftRightPower(0,0);

            // Turn off RUN_TO_POSITION
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  linearOpMode.sleep(250);   // optional pause after each move
        }

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
