package com.team2753;

import com.acmerobotics.dashboard.config.Config;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */

@Config
public class Constants {

    //Drivetrain Specs
    public static final double COUNTS_PER_MOTOR_REV = 1120;     // AndyMark NeveRest 40
    public static double DRIVE_GEAR_REDUCTION = 0.75 ;     // This is < 1.0 if geared UP
    public static double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference w/ wheel base
    public static double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.141592);
    //IMPORTANT Change this vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    public static double WHEEL_BASE = 12.625;
}
