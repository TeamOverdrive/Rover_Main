package com.team2753.archive.subsystems;

import com.team2753.archive.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/16/2018.
 */

public interface Subsystem {
    void init(Team753Linear linearOpMode, boolean auto);
    void zeroSensors();
    void stop();
    void outputToTelemetry(Telemetry telemetry);
}
