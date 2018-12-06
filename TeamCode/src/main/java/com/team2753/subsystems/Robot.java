package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.team2753.Team753Linear;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public class Robot {

    private static Robot mInstance = new Robot();
    public static Robot getInstance(){
        return mInstance;
    }

    private Drive mDrive = new Drive();
    private Intake mIntake = new Intake();
    private Lift mLift = new Lift();
    private Phone mPhone = new Phone();
    private Marker mMarker = new Marker();

    private List<Subsystem> subsystems = Arrays.asList(
            mDrive, mLift, mIntake, mPhone, mMarker);

    /**
     * @param telemetry Opmode Telemetry object
     */
    public void outputToTelemetry(Telemetry telemetry){
        for (Subsystem subsystem:subsystems)
            subsystem.outputToTelemetry(telemetry);
    }

    /**
     * Init all subsystems in this method.
     * @param opmode The OpMode Object
     * @param autonomous If the OpMode is for autonomous mode or not.
     */

    public void init(Team753Linear opmode, boolean autonomous) {
        // Init all subsystems
        RobotLog.v("================ Robot Subsystems Init Loop Started =============");
        for (Subsystem subsystem:subsystems){
            RobotLog.v("================ Robot Subsystem " + subsystem.toString() +
                    " Init Started =============");
            subsystem.init(opmode, autonomous);
        }
        RobotLog.v("================ Robot Subsystems Init Loop Finished =============");
    }

    /**
     * Stop all subsystems with this method.
     */
    public void stop() {
        //try {
            for (Subsystem subsystem:subsystems)
                subsystem.stop();
        //} catch (Exception e){}
    }


    /**
     * Reset all subsystems with this method.
     */
    public void zeroSensors() {
        try {
            for (Subsystem subsystem:subsystems)
                subsystem.zeroSensors();
        } catch (Exception e){}
    }


    /**
     * Get Drivetrain object
     * @return mDrive
     */


    public Drive getDrive(){
        return mDrive;
    }

    public Lift getLift() {
        return mLift;
    }

    public Intake getIntake() {
        return mIntake;
    }

    public Phone getPhone() {return mPhone;}

    public Marker getMarker(){return mMarker;}
}
