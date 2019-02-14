package com.team2753.libs.subsystems;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by joshua9889 on 3/7/2018.
 */

public class MRGyro implements Gyro {

    private ModernRoboticsI2cGyro gyro;

    public MRGyro(LinearOpMode opMode, String ID, I2cAddr i2cAddr){
        gyro = opMode.hardwareMap.get(ModernRoboticsI2cGyro.class, ID);
        gyro.setI2cAddress(i2cAddr);
    }

    @Override
    public void calibrate(LinearOpMode opMode) {
        gyro.calibrate();
        while (gyro.isCalibrating() && opMode.opModeIsActive()){
            Thread.yield();
        }
    }

    @Override
    public void reset() {
        gyro.resetZAxisIntegrator();
    }

    @Override
    public double getAngleDegrees() {
        return gyro.getIntegratedZValue();
    }

    @Override
    public double getAngleRadians() {
        return Math.toRadians(getAngleDegrees());
    }
}
