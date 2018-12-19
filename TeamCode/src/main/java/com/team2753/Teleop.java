package com.team2753;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import static com.team2753.libs.MathUtil.scaleInput;


/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 10/17/2018.
 */

@TeleOp(name = "Teleop", group = "0_Main")
public class Teleop extends Team753Linear{

    boolean liftOverride = true;
    private double angle  = 0;

    @Override
    public void runOpMode(){

        waitForStart("Teleop", false);

        while(opModeIsActive()){

            /*------       Gamepad 1 Controls       ------*/

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

            if(gamepad1.left_bumper)
                Robot.getIntake().setIntakePower(-1);
            else if(gamepad1.right_bumper)
                Robot.getIntake().setIntakePower(1);
            else
                Robot.getIntake().setIntakePower(0);

            if(gamepad1.left_trigger >= 0.1){
                Robot.getIntake().setSlidePower(1);
            }
            else if(gamepad1.right_trigger >= 0.1){
                Robot.getIntake().setSlidePower(-1);
            }
            else
                Robot.getIntake().setSlidePower(0);



            /*------       Gamepad 2 Controls       ------*/

            /*Lift Controls*/


            if(Math.abs(gamepad2.left_stick_y)<= 0.05){


                if (gamepad2.dpad_up) {
                    Robot.getLift().setPower(0.65);
                } else if (gamepad2.dpad_down) {
                    Robot.getLift().setPower(-0.6);
                } else {
                    Robot.getLift().setPower(0);
                    liftOverride = false;
                }

                if(!liftOverride){
                    //autostuff
                }
            }
            else{
                liftOverride = true;
                float liftThrottle = gamepad2.left_stick_y;
                //Clip
                liftThrottle = Range.clip(liftThrottle, -1, 1);
                //Scale
                liftThrottle = (float) scaleInput(liftThrottle);
                //Invert
                liftThrottle = liftThrottle * -1;
                //Apply power to motor

                if(Robot.getLift().isLocked()) {
                    Robot.getLift().setPower(liftThrottle);
                }
            }



            if(gamepad2.right_bumper)
                Robot.getLift().lock();
            if(gamepad2.left_bumper)
                Robot.getLift().unlock();

            if(gamepad1.right_bumper)
                Robot.getMarker().retract();
            if(gamepad1.left_bumper)
                Robot.getMarker().deploy();

            if(gamepad2.a){
                angle -= 0.05;
            } else if (gamepad2.y) {
                angle += 0.05;
            }

            Robot.getIntake().setGatePosition(angle);


            updateTelemetry();
        }
        finalAction();
    }
}
