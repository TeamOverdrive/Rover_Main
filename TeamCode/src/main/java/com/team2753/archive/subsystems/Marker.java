package com.team2753.archive.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.team2753.archive.Team753Linear;
import com.team2753.archive.subsystems.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/11/2018.
 */
public class Marker implements Subsystem {

    private Servo frontMarker;
    private double FRONTUP = .6;
    private double FRONTDOWN = .2;
    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {
        frontMarker = (Servo) linearOpMode.hardwareMap.get("marker");
        retract();
    }

    @Override
    public void zeroSensors() {}

    @Override
    public void stop() {}

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Team Marker Servo Position", getPosition());
    }

    public void setPosition(double position){
        frontMarker.setPosition(position);
    }

    public double getPosition(){return frontMarker.getPosition();}

    public void retract(){
        setPosition(FRONTUP);
    }

    public void deploy(){
        setPosition(FRONTDOWN);
    }
}
