package com.team2753.archive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import static com.team2753.archive.libs.MathUtil.scaleInput;


/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 10/17/2018.
 */

@TeleOp(name = "Teleop", group = "0_Main")
public class Teleop extends Team753Linear{

    boolean liftOverride, intakeOverride = true;

    @Override
    public void runOpMode(){

        waitForStart("Teleop", false);

        while(opModeIsActive()){

            /*------       Gamepad 1 Controls       ------*/

            /* Drivetrain Controls */
            //Gamepad 1 joysticks
            //D-pad controls for slower movement


            if (Math.abs(gamepad1.right_stick_y) < 0.05 && Math.abs(gamepad1.left_stick_y) < 0.05) {
                if (gamepad1.dpad_up) {
                    Robot.getDrive().setLeftRightPower(0.4, 0.4);
                } else if (gamepad1.dpad_down) {
                    Robot.getDrive().setLeftRightPower(-0.4, -0.4);
                } else if (gamepad1.dpad_left) {
                    Robot.getDrive().setLeftRightPower(-0.4, 0.4);
                } else if (gamepad1.dpad_right) {
                    Robot.getDrive().setLeftRightPower(0.4, -0.4);
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

            if(gamepad2.left_bumper)
                Robot.getIntake().setIntakePower(-0.6);
            else if(gamepad2.right_bumper)
                Robot.getIntake().setIntakePower(1);
            else
                Robot.getIntake().setIntakePower(0);


            /*
            if(gamepad1.left_trigger >= 0.1){
                Robot.getIntake().setSlidePower(-1);
            }
            else if(gamepad1.right_trigger >= 0.1){
                Robot.getIntake().setSlidePower(1);
            }
            else
                Robot.getIntake().setSlidePower(0);
*/


            /*------       Gamepad 2 Controls       ------*/

            /*Lift Controls*/


            if(Math.abs(gamepad2.right_stick_y)<= 0.05){

                if (gamepad2.dpad_up) {
                    Robot.getLift().setPower(0.75);
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
                float liftThrottle = gamepad2.right_stick_y;
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

            //slide
            if(Math.abs(gamepad2.left_stick_y)<= 0.05){

                if (gamepad2.dpad_left) {
                    Robot.getIntake().setSlidePower(0.5);
                } else if (gamepad2.dpad_right) {
                    Robot.getIntake().setSlidePower(-0.5);
                } else {
                    Robot.getIntake().setSlidePower(0);
                    intakeOverride = false;
                }

                if(!intakeOverride){
                    //autostuff
                }
            }
            else{
                intakeOverride = true;
                float intakeThrottle = gamepad2.left_stick_y;
                //Clip
                intakeThrottle = Range.clip(intakeThrottle, -1, 1);
                //Scale
                intakeThrottle = (float) scaleInput(intakeThrottle);
                //Invert
                intakeThrottle = intakeThrottle * -1;
                //Apply power to motor
                Robot.getIntake().setSlidePower(intakeThrottle);
            }

            //gate
            if(gamepad2.a)
                Robot.getIntake().intakeDown();
            if(gamepad2.x)
                Robot.getIntake().intakeCenter();
            if(gamepad2.y)
                Robot.getIntake().intakeUp();

            /*
            float intakeThrottle = gamepad2.left_stick_y;
            //Clip
            intakeThrottle = Range.clip(intakeThrottle, -1, 1);
            //Scale
            intakeThrottle = (float) scaleInput(intakeThrottle);
            //Invert
            intakeThrottle = intakeThrottle * -1;
            //Apply power to motor
            Robot.getIntake().setIntakePower(intakeThrottle);
            */


            if(gamepad1.right_bumper)
                Robot.getLift().lock();
            if(gamepad1.left_bumper)
                Robot.getLift().unlock();


            if(gamepad1.right_bumper)
                Robot.getMarker().retract();
            if(gamepad1.left_bumper)
                Robot.getMarker().deploy();

            updateTelemetry();
        }
        finalAction();
    }
}
