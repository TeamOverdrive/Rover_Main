package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Lift implements Subsystem {

    //Vars

    private DcMotor liftMotor = null;

    @Override
    public void init(LinearOpMode linearOpMode, boolean auto) {
        liftMotor = (DcMotor) linearOpMode.hardwareMap.get("lift_motor");
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {

    }
}
