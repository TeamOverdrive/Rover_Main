package com.team2753.other.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.team2753.Team753Linear;

import static com.team2753.libs.MathUtil.scaleInput;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 10/17/2018.
 */

@TeleOp(name = "Teleop Test", group = "1_test")
@Disabled
public class testTeleop extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Teleop Test", false);
        Robot.getLift().zeroSensors();
        while(opModeIsActive()){


            /* Drivetrain Controls */
            //Gamepad 1 joysticks
            //D-pad controls for slower movement



            if (Math.abs(gamepad1.right_stick_y) < 0.01 && Math.abs(gamepad1.left_stick_y) < 0.01) {
                if (gamepad1.dpad_up) {
                    Robot.getDrive().setLeftRightPower(0.3, 0.3);
                } else if (gamepad1.dpad_down) {
                    Robot.getDrive().setLeftRightPower(-0.3, -0.3);
                } else if (gamepad1.dpad_left) {
                    Robot.getDrive().setLeftRightPower(0.35, -0.35);
                } else if (gamepad1.dpad_right) {
                    Robot.getDrive().setLeftRightPower(-0.35, 0.35);
                } else {
                    Robot.getDrive().setLeftRightPower(0, 0);
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
                leftThrottle = (float) scaleInput(leftThrottle);
                rightThrottle = (float) scaleInput(rightThrottle);

                Robot.getDrive().setLeftRightPower(leftThrottle, rightThrottle);
            }

            /*------       Gamepad 2 Controls       ------*/

            /*Lift Controls*/
            //Right joystick up/down to raise/lower lift

            float liftThrottle = gamepad2.left_stick_y;
            //Clip
            liftThrottle = Range.clip(liftThrottle, -1, 1);
            //Scale
            liftThrottle = (float) scaleInput(liftThrottle);
            //Invert
            liftThrottle = liftThrottle * -1;
            //Apply power to motor
            Robot.getLift().setPower(liftThrottle);

            if(gamepad2.right_bumper)
                Robot.getLift().lock();
            if(gamepad2.left_bumper)
                Robot.getLift().unlock();

            if(gamepad1.right_bumper)
                Robot.getMarker().retract();
            if(gamepad1.left_bumper)
                Robot.getMarker().deploy();


            updateTelemetry();

        }
    }
}
