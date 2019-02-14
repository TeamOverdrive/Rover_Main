package com.team2753.libs.localization;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/**
 * Created by joshua9889 on 4/16/2018.
 */

public class Vector {

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public DistanceUnit unit;

    public double distance;
    public double angle;

    /**
     * the time on the System.nanoTime() clock at which the data was acquired. If no
     * timestamp is associated with this particular set of data, this value is zero.
     */
    public long acquisitionTime;

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------


    public Vector(DistanceUnit unit, double distance, double angle, long acquisitionTime){
        this.unit = unit;
        this.distance = distance;
        this.angle = angle;
        this.acquisitionTime = acquisitionTime;
    }

    public Vector toUnit(DistanceUnit distanceUnit){
        if (distanceUnit != this.unit)
        {
            return new Vector(distanceUnit,
                    distanceUnit.fromUnit(this.unit, distance),
                    distanceUnit.fromUnit(this.unit, angle),
                    this.acquisitionTime);
        }
        else
            return this;
    }

    public double getX(){
        return distance * Math.cos(Math.toRadians(angle));
    }

    public double getY(){
        return distance * Math.sin(Math.toRadians(angle));
    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    @Override public String toString()
    {
        return String.format(Locale.getDefault(), "(%.3f %.3f %.3f)%s", distance, angle, unit.toString());
    }
}
