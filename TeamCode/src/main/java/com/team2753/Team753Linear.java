package com.team2753;

import android.graphics.Bitmap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team2753.libs.AutoTransitioner;
import com.team2753.libs.VuMark;
import com.team2753.subsystems.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaRoverRuckus;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public abstract class Team753Linear extends LinearOpMode{

    public Robot Robot = com.team2753.subsystems.Robot.getInstance();

    public static final String vuforiaKey = "AeUsQDb/////AAAAGXsDAQwNS0SWopXJpAHyRntcnTcoWD8Tns" +
            "R6PWGX9OwmlIhNxQgn8RX/1cH2RXXTsuSkHh6OjfMoCuHt35rhumaUsLnk8MZZJ7P9PEu+uSsUbH1hHcnnB" +
            "6GzJnX/FqlZJX5HWWfeQva5s4OHJEwSbPR2zxhkRxntAjeuIPGVSHeIseAikPB0NF0SqEiPZea+PWrxpryP" +
            "/bxKqy7VA77krKFtgDi6amam+vWvBCqyIo6tXxbo0w8q/HCXo4v/4UYyoFLRx1l1d2Wya5an5SwFfU3eKxy" +
            "0BYc3tnsaaDJww59RNJ6IK9D3PZM+oPDrmF9ukQrc/jw+u+6Zm4wQHieHt9urSwLR7dgz0V3aatDx1V7y";

    private static VuMark vumark = new VuMark(vuforiaKey);
    protected VuMark.roverVumarks savedVumark  = VuMark.roverVumarks.UNKNOWN;

    /*
    private Bitmap bm = null;
    private int redVotes = 0;
    private int blueVotes = 0;
    */

    public enum Gold_Position{
        LEFT,
        CENTER,
        RIGHT
    }

    protected Gold_Position goldPosition  = null;

    protected static ElapsedTime runtime = new ElapsedTime();
    private boolean isAuto = false;

    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    private Telemetry.Item status;

    //Init  method

    public void waitForStart(String OpModeName, boolean auto){

        dashboardTelemetry.setAutoClear(true);
        status = dashboardTelemetry.addData("Status", "Initializing...");
        Telemetry.Item currentOpMode = dashboardTelemetry.addData("Running", OpModeName);
        dashboardTelemetry.update();

        Robot.init(this, auto);

        if(auto){
            RobotLog.v("================ Start VuCam =============");
            vumark.setup(VuforiaLocalizer.CameraDirection.BACK, true);
            //FRONT is selfie camera, BACK is main camera

            RobotLog.v("================ AutoTransitioner =============");
            AutoTransitioner.transitionOnStop(this, "Teleop"); //Auto Transitioning
        }

    }

    public void resetRuntime() {
        runtime.reset();
    }

    //Telemetry
    public void updateTelemetry() {

        if (isAuto) {
            dashboardTelemetry.addData("Match Time", 30 - getRuntime());
        }
        if (!isAuto) {
            dashboardTelemetry.addData("Match Time", 120 - runtime.seconds());
            if (runtime.seconds() > 90) {
                dashboardTelemetry.addData("Phase", "End game");
            }
            if (runtime.seconds() > 120) {
                dashboardTelemetry.addData("Phase", "Overtime");
            }
        }

        Robot.outputToTelemetry(telemetry);

        dashboardTelemetry.update();
    }

    public void finalAction() {
        Robot.stop();
        requestOpModeStop();
    }

    public void waitForTick(long periodMs) {
        long remaining = periodMs - (long) runtime.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        runtime.reset();
    }

}
