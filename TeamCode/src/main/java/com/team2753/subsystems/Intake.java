package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.team2753.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Intake implements Subsystem {


    private DcMotor intakeMotor;
    private DcMotor slideMotor;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {

        //TODO: add intake motors
        //TODO: add rev touch sensor

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
