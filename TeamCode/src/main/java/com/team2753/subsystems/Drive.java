package com.team2753.subsystems;

import com.acmerobotics.roadrunner.drive.TankDrive;
import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.hardware.motors.NeveRest20Gearmotor;
import com.qualcomm.hardware.motors.NeveRest40Gearmotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/16/2018.
 */
public class Drive extends TankDrive implements Subsystem{


    public static final MotorConfigurationType MOTOR_CONFIG = MotorConfigurationType.getMotorType(NeveRest40Gearmotor.class);

    private static final double TICKS_PER_REV = MOTOR_CONFIG.getTicksPerRev();

    //TODO: tune this
    public static final PIDCoefficients NORMAL_VELOCITY_PID = new PIDCoefficients(20, 8, 12);

    //Drive Motors
    /*
    private DcMotor rightFront, leftFront, rightBack, leftBack = null;
    private DcMotor left, right;
    */

    private DcMotorEx leftFront, leftBack, rightBack, rightFront;
    private List<DcMotorEx> motors;

    public Drive(double trackWidth, NanoClock clock) {
        super(trackWidth, clock);
    }

    //Gyro

    public void init(LinearOpMode linearOpMode, boolean auto) {

        //super(1);

        rightBack = linearOpMode.hardwareMap.get(DcMotorEx.class, "right_back");
        rightFront = linearOpMode.hardwareMap.get(DcMotorEx.class, "right_front");
        leftBack = linearOpMode.hardwareMap.get(DcMotorEx.class, "left_back");
        leftFront = linearOpMode.hardwareMap.get(DcMotorEx.class, "left_front");


        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);


        if(!auto) {
            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }


    public void zeroSensors() {

    }


    public void stop() {
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setLeftRightPower(0,0);
    }


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

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){

        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftBack.setZeroPowerBehavior(zeroPowerBehavior);
        rightBack.setZeroPowerBehavior(zeroPowerBehavior);
    }


    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        return null;
    }

    @Override
    public void setMotorPowers(double v, double v1) {
        setLeftRightPower(v,v1);
    }
}
