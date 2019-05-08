package com.team2753.subsystems;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunnerquickstart.drive.DriveConstants;
import com.acmerobotics.roadrunnerquickstart.util.AxesSigns;
import com.acmerobotics.roadrunnerquickstart.util.BNO055IMUUtil;
import com.acmerobotics.roadrunnerquickstart.util.LynxOptimizedI2cFactory;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.jetbrains.annotations.NotNull;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.ExpansionHubMotor;
import org.openftc.revextensions2.RevBulkData;
import org.openftc.revextensions2.RevExtensions2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 3/5/2019.
 */
public class Drive extends DriveBase{
    private ExpansionHubEx hub;
    private List<ExpansionHubMotor> motors, leftMotors, rightMotors;
    private BNO055IMU imu;

    public Drive(HardwareMap hardwareMap) {
        super();

        RevExtensions2.init();

        // TODO: adjust the names of the following hardware devices to match your configuration
        // for simplicity, we assume that the desired IMU and drive motors are on the same hub
        // note: this strategy is still applicable even if the drive motors are split between hubs
        hub = hardwareMap.get(ExpansionHubEx.class, "Expansion Hub 2");

        imu = LynxOptimizedI2cFactory.createLynxEmbeddedImu(hub.getStandardModule(), 0);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        // TODO: if your hub is mounted vertically, remap the IMU axes so that the z-axis points
        // upward (normal to the floor) using a command like the following:
        BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPP);

        // add/remove motors depending on your robot (e.g., 6WD)
        ExpansionHubMotor leftFront = hardwareMap.get(ExpansionHubMotor.class, "left_front");
        ExpansionHubMotor leftRear = hardwareMap.get(ExpansionHubMotor.class, "left_back");
        ExpansionHubMotor rightRear = hardwareMap.get(ExpansionHubMotor.class, "right_back");
        ExpansionHubMotor rightFront = hardwareMap.get(ExpansionHubMotor.class, "right_front");

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);
        leftMotors = Arrays.asList(leftFront, leftRear);
        rightMotors = Arrays.asList(rightFront, rightRear);

        for (ExpansionHubMotor motor : motors) {
            // TODO: decide whether or not to use the built-in velocity PID
            // if you keep it, then don't tune kStatic or kA
            // otherwise, comment out the following line
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // TODO: reverse any motors using DcMotor.setDirection()
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.FORWARD);

        // TODO: set the tuned coefficients from VelocityPIDTuner if using RUN_USING_ENCODER
        setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(24,10,8));
    }

    @Override
    public PIDCoefficients getPIDCoefficients(DcMotor.RunMode runMode) {
        PIDFCoefficients coefficients = leftMotors.get(0).getPIDFCoefficients(runMode);
        return new PIDCoefficients(coefficients.p, coefficients.i, coefficients.d);
    }

    @Override
    public void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients) {
        for (ExpansionHubMotor motor : motors) {
            motor.setPIDFCoefficients(runMode, new PIDFCoefficients(
                    coefficients.kP, coefficients.kI, coefficients.kD, 1
            ));
            RobotLog.i("========================== Setting PID coefficients ==========================");
        }
    }

    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        double leftSum = 0, rightSum = 0;
        RevBulkData bulkData = hub.getBulkInputData();

        if (bulkData == null) {
            return Arrays.asList(0.0, 0.0);
        }

        for (DcMotorEx leftMotor : leftMotors) {
            leftSum += DriveConstants.encoderTicksToInches(bulkData.getMotorCurrentPosition(leftMotor));
        }
        for (DcMotorEx rightMotor : rightMotors) {
            rightSum += DriveConstants.encoderTicksToInches(bulkData.getMotorCurrentPosition(rightMotor));
        }
        return Arrays.asList(leftSum / leftMotors.size(), rightSum / rightMotors.size());
    }

    @Override
    public void setMotorPowers(double v, double v1) {
        for (ExpansionHubMotor leftMotor : leftMotors) {
            leftMotor.setPower(v);
        }
        for (ExpansionHubMotor rightMotor : rightMotors) {
            rightMotor.setPower(v1);
        }
    }

    @Override
    public double getExternalHeading() {
        return imu.getAngularOrientation().firstAngle;
    }
}
