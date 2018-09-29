package com.team2753.other.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/15/2018.
 * Test Teleop
 */



@TeleOp
@Disabled
public class drivetestteleop extends OpMode{

    private DcMotor rightBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor leftFrontMotor;
    private DcMotor liftMotor;


    @Override
    public void init() {

        rightBackMotor = (DcMotor) hardwareMap.get("right_back");
        rightFrontMotor = (DcMotor) hardwareMap.get("right_front");
        leftBackMotor = (DcMotor) hardwareMap.get("left_back");
        leftFrontMotor = (DcMotor) hardwareMap.get("left_front");

        liftMotor = (DcMotor) hardwareMap.get("lift_motor");

        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        liftMotor.setDirection(DcMotor.Direction.REVERSE);

        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Robot Initialized Successfully!");
    }

    @Override
    public void loop() {

        /* Drivetrain Controls */ //Gamepad 1 joysticks
        //D-pad controls for slower movement
        if (Math.abs(gamepad1.right_stick_y) < 0.01 && Math.abs(gamepad1.left_stick_y) < 0.01) {
            if (gamepad1.dpad_up) {
                setLeftRightPower(-0.3, -0.3);
            } else if (gamepad1.dpad_down) {
                setLeftRightPower(0.3, 0.3);
            } else if (gamepad1.dpad_left) {
                setLeftRightPower(0.35, -0.35);
            } else if (gamepad1.dpad_right) {
                setLeftRightPower(-0.35, 0.35);
            } else {
                setLeftRightPower(0, 0);
            }
        }
        else
            {
            float leftThrottle = gamepad1.left_stick_y;
            float rightThrottle = gamepad1.right_stick_y;

            /* Clip the left and right throttle values so that they never exceed +/- 1.  */
            leftThrottle = Range.clip(leftThrottle, -1, 1);
            rightThrottle = Range.clip(rightThrottle, -1, 1);

            /* Scale the throttle values to make it easier to control the robot more precisely at slower speeds.  */
            leftThrottle = (float) scaleInput(leftThrottle);
            rightThrottle = (float) scaleInput(rightThrottle);

            setLeftRightPower(leftThrottle, rightThrottle);
            }

        float liftThrottle = gamepad2.left_stick_y;
        //Clip
        liftThrottle = Range.clip(liftThrottle, -1, 1);
        //Scale
        liftThrottle = (float) scaleInput(liftThrottle);
        //Invert
        liftThrottle = liftThrottle * -1;
        //Apply power to motor
        liftMotor.setPower(liftThrottle);
    }

    public static double scaleInput(double dVal)   {
        double[] scaleArray = {
                0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00
                //to use a different scale, list alternate scale values here and comment out the line above
        };

        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16)  {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0)  {
            dScale = -scaleArray[index];
        }  else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

    public void setLeftRightPower(double leftPower, double rightPower){
        rightBackMotor.setPower(rightPower);
        rightFrontMotor.setPower(rightPower);
        leftFrontMotor.setPower(leftPower);
        leftBackMotor.setPower(leftPower);
    }
}
