package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/16/2018.
 */

public interface Subsystem {
    void init(LinearOpMode linearOpMode, boolean auto);
    void zeroSensors();
    void stop();
    void outputToTelemetry(Telemetry telemetry);
}
