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

            if(!Robot.getIntake().getTouchPressed()) {
                if (gamepad2.left_bumper)
                    Robot.getIntake().setIntakePower(-0.6);
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
            }
            else{
                Robot.getIntake().intakeUp();
                Robot.getIntake().setIntakePower(-0.5);
            }


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

            //state changers
            if(gamepad1.left_trigger >= 0.05 ||
                    gamepad1.right_trigger >= 0.05 ||
                    gamepad1.a ||
                    gamepad1.y ){
                liftState = manual;
            }
            else if(gamepad1.x || gamepad1.b ||
                    Robot.getLift().isBusy()){
                liftState = auto;
            }
            else if(!Robot.getLift().isBusy()){
                liftState = idle;
            }



            //state effects
            switch (liftState){
                case manual:
                    //manual control
                    if(Robot.getLift().getMode() != DcMotorEx.RunMode.RUN_USING_ENCODER){
                        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                    if(gamepad1.right_trigger<= 0.05&&gamepad1.left_trigger<= 0.05){

                        if (gamepad1.y) {
                            Robot.getLift().setPower(0.75);
                        } else if (gamepad1.a) {
                            Robot.getLift().setPower(-0.6);
                        } else {
                            Robot.getLift().setPower(0);
                        }
                    }

                    else{
                        float liftThrottle;
                        if(gamepad1.right_trigger<=0.05) {
                            liftThrottle = gamepad1.left_trigger;
                            //Clip
                            liftThrottle = Range.clip(liftThrottle, 0, 1);
                            //Scale
                            liftThrottle = (float) scaleInput(liftThrottle);
                            //Invert
                            liftThrottle = liftThrottle * -1;
                        }
                        else{
                            liftThrottle = gamepad1.right_trigger;
                            //Clip
                            liftThrottle = Range.clip(liftThrottle, 0, 1);
                            //Scale
                            liftThrottle = (float) scaleInput(liftThrottle);
                        }
                        //Apply power to motor

                        if(Robot.getLift().isLocked()) {
                            Robot.getLift().setPower(liftThrottle);
                        }
                    }
                    break;
                case auto:
                    //auto control
                    if(Robot.getLift().getMode() != DcMotorEx.RunMode.RUN_TO_POSITION){
                        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                    if(gamepad1.x){
                        Robot.getLift().setTarget(400);
                    }
                    else if (gamepad1.b){
                        Robot.getLift().setTarget(2300);
                    }
                    Robot.getLift().setPower(1);
                    break;
                case idle:
                    //stop motor
                    if(Robot.getLift().getMode() != DcMotorEx.RunMode.RUN_USING_ENCODER){
                        Robot.getLift().setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                    Robot.getLift().setPower(0);
                    break;
                default:
                    //stop motor
                    Robot.getLift().setPower(0);
                    break;
            }

/*
            if(gamepad1.right_trigger<= 0.05&&gamepad1.left_trigger<= 0.05){

                if (gamepad1.y) {
                    Robot.getLift().setPower(0.7);
                    liftOverride = true;
                } else if (gamepad1.a) {
                    Robot.getLift().setPower(-0.6);
                    liftOverride = true;
                } else if(!Robot.getLift().isBusy()){
                    Robot.getLift().setPower(0);
                    liftOverride = false;
                }

                if(!liftOverride){
                    if(gamepad1.x){
                        //liftOverride = false;
                        Robot.getLift().setTarget(400);
                    }
                    else if (gamepad1.b){
                        //liftOverride = false;
                        Robot.getLift().setTarget(2300);
                    }
                }
            }
            else{
                liftOverride = true;
                float liftThrottle;
                if(gamepad1.right_trigger<=0.05) {
                     liftThrottle = gamepad1.left_trigger;
                    //Clip
                    liftThrottle = Range.clip(liftThrottle, 0, 1);
                    //Scale
                    liftThrottle = (float) scaleInput(liftThrottle);
                    //Invert
                    liftThrottle = liftThrottle * -1;
                }
                else{
                    liftThrottle = gamepad1.right_trigger;
                    //Clip
                    liftThrottle = Range.clip(liftThrottle, 0, 1);
                    //Scale
                    liftThrottle = (float) scaleInput(liftThrottle);
                }
                //Apply power to motor

                if(Robot.getLift().isLocked()) {
                    Robot.getLift().setPower(liftThrottle);
                }
            }

            if(liftOverride){
                if(Robot.getLift().getMode() != DcMotorEx.RunMode.RUN_USING_ENCODER){
                    Robot.getLift().setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
            else {
                Robot.getLift().setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
                Robot.getLift().setPower(1);
            }

*/

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

            updateTelemetry();
        }
        finalAction();
    }
}
