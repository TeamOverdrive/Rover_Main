package com.team2753.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.team2753.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/11/2018.
 */
public class Marker implements Subsystem{

    private Servo marker;
    private double UP = .6;
    private double DOWN = .2;

    @Override
    public void init(Team753Linear linearOpMode, boolean auto) {
        marker = (Servo) linearOpMode.hardwareMap.get("marker");
        up();
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Team Marker Servo Position", getPosition());
    }

    public void setPosition(double position){
        marker.setPosition(position);
    }

    public double getPosition(){return marker.getPosition();}

    public void up(){
        marker.setPosition(UP);
    }

    public void deploy(){
        marker.setPosition(DOWN);
    }
}
