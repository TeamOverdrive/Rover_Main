package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 12/10/2017.
 */

public interface Subsystem {
    void init(LinearOpMode linearOpMode, boolean auto);
    void zeroSensors();
    void stop();
    void outputToTelemetry(Telemetry telemetry);
}