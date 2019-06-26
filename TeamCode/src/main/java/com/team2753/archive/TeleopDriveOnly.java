package com.team2753.archive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 10/17/2018.
 */

@TeleOp(name = "DriveOnly", group = "0_Main")
public class TeleopDriveOnly extends Team753Linear{

    private DcMotorEx leftFront, leftBack, rightBack, rightFront;

    @Override
    public void runOpMode(){

        //waitForStart("Teleop", false);
        rightBack = hardwareMap.get(DcMotorEx.class, "right_back");
        rightFront = hardwareMap.get(DcMotorEx.class, "right_front");
        leftBack = hardwareMap.get(DcMotorEx.class, "left_back");
        leftFront = hardwareMap.get(DcMotorEx.class, "left_front");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while(opModeIsActive()){

            /*------       Gamepad 1 Controls       ------*/

            /* Drivetrain Controls */
            //Gamepad 1 joysticks
            //D-pad controls for slower movement


            if (Math.abs(gamepad1.right_stick_y) < 0.05 && Math.abs(gamepad1.left_stick_y) < 0.05) {
                if (gamepad1.dpad_up) {
                    setLeftRightPower(0.4, 0.4);
                } else if (gamepad1.dpad_down) {
                    setLeftRightPower(-0.4, -0.4);
                } else if (gamepad1.dpad_left) {
                    setLeftRightPower(-0.4, 0.4);
                } else if (gamepad1.dpad_right) {
                    setLeftRightPower(0.4, -0.4);
                } else {
                    setLeftRightPower(0, 0);
                }
            }
            else
            {
                float leftThrottle = gamepad1.left_stick_y;
                float rightThrottle = gamepad1.right_stick_y;

                //Invert the y values
                leftThrottle = leftThrottle*-1;
                rightThrottle = rightThrottle*-1;

                // Clip the left and right throttle values so that they never exceed +/- 1.
                leftThrottle = Range.clip(leftThrottle, -1, 1);
                rightThrottle = Range.clip(rightThrottle, -1, 1);

                // Scale the throttle values to make it easier to control the robot more precisely at slower speeds.
                /*
                leftThrottle = (float) scaleInput(leftThrottle);
                rightThrottle = (float) scaleInput(rightThrottle);
                */

                leftThrottle = (float) Math.sin(((Math.PI)/2)*leftThrottle);
                rightThrottle = (float) Math.sin(((Math.PI)/2)*rightThrottle);


                setLeftRightPower(leftThrottle, rightThrottle);
            }
            updateTelemetry();
        }
        finalAction();
    }


    public void setRunMode(DcMotor.RunMode runMode){

        rightFront.setMode(runMode);
        rightBack.setMode(runMode);
        leftFront.setMode(runMode);
        leftBack.setMode(runMode);
    }

    public void setLeftRightPower(double leftPower, double rightPower){
        leftPower = Range.clip(leftPower, -1., 1.);
        rightPower = Range.clip(rightPower, -1., 1.);

        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);

    }
}
