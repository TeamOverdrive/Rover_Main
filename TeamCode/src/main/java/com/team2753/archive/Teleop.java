package com.team2753.archive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.team2753.archive.subsystems.Robot;

import static com.team2753.archive.Teleop.liftStates.auto;
import static com.team2753.archive.Teleop.liftStates.idle;
import static com.team2753.archive.Teleop.liftStates.manual;
import static com.team2753.archive.libs.MathUtil.scaleInput;


/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 10/17/2018.
 */

@TeleOp(name = "Teleop", group = "0_Main")
public class Teleop extends Team753Linear{

    boolean liftOverride = false;
    boolean intakeOverride = true;

    enum liftStates{
        manual,
        auto,
        idle
    }

    private liftStates liftState = idle;

    boolean HANGSPEED = false;
    float dumper = 0;

    @Override
    public void runOpMode(){

        waitForStart("Teleop", false);

        while(opModeIsActive()) {

            /**
             * Drive
             */

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
            } else {
                float leftThrottle = gamepad1.left_stick_y;
                float rightThrottle = gamepad1.right_stick_y;

                //Invert the y values
                leftThrottle = leftThrottle * -1;
                rightThrottle = rightThrottle * -1;

                // Clip the left and right throttle values so that they never exceed +/- 1.
                leftThrottle = Range.clip(leftThrottle, -1, 1);
                rightThrottle = Range.clip(rightThrottle, -1, 1);

                //put a curve on
                leftThrottle = (float) Math.sin(((Math.PI) / 2) * leftThrottle);
                rightThrottle = (float) Math.sin(((Math.PI) / 2) * rightThrottle);


                Robot.getDrive().setLeftRightPower(leftThrottle, rightThrottle);
            }

            /**
             * Intake
             */

            if (gamepad2.left_bumper)
                Robot.getIntake().setIntakePower(-0.75);
            else if (gamepad2.right_bumper)
                Robot.getIntake().setIntakePower(1);
            else
                Robot.getIntake().setIntakePower(0);

            //intake position
            if (gamepad2.a)
                Robot.getIntake().intakeDown();
            else if (gamepad2.y)
                Robot.getIntake().intakeUp();
            else
                Robot.getIntake().intakeCenter();


            /**
             * Intake Slide
             */

            if (Math.abs(gamepad2.left_stick_y) <= 0.05) {

                if (gamepad2.dpad_left) {
                    Robot.getIntake().setSlidePower(0.5);
                } else if (gamepad2.dpad_right) {
                    Robot.getIntake().setSlidePower(-0.5);
                } else {
                    Robot.getIntake().setSlidePower(0);
                    intakeOverride = false;
                }
            } else {
                //intakeOverride = true;
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

            /**
             * Dumper
             */

            dumper = gamepad2.right_trigger;
            if(gamepad2.right_trigger<=0.05)
                dumper =0;
            Robot.getLift().setDumperPosition(dumper*0.333);


            /**
             * shifter motors
             */
            if (Math.abs(gamepad2.right_stick_y) <= 0.05) {

                if (gamepad2.dpad_up) {
                    Robot.getLift().setPower(0.5);
                } else if (gamepad2.dpad_down) {
                    Robot.getLift().setPower(-0.5);
                } else {
                    Robot.getLift().setPower(0);
                    intakeOverride = false;
                }
            } else {
                liftOverride = true;
                float liftThrottle = gamepad2.right_stick_y;
                //Clip
                liftThrottle = Range.clip(liftThrottle, -1, 1);
                //Curve
                liftThrottle = (float) Math.sin(((Math.PI) / 2) * liftThrottle);
                //Invert
                liftThrottle = liftThrottle * -1;

                if(!HANGSPEED && liftThrottle<0) {
                    liftThrottle = (float) (liftThrottle * .75);
                }

                //Apply power to motor
                Robot.getLift().setPower(liftThrottle);
            }


            /**
             * shifter servo
             */
            if (gamepad2.x) {
                Robot.getLift().shiftToScore();
                HANGSPEED = false;
            }
            if (gamepad2.b){
                Robot.getLift().shiftToHang();
                HANGSPEED = true;
            }

            updateTelemetry();
        }
        finalAction();
    }
}
