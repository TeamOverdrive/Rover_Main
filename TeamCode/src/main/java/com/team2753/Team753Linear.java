package com.team2753;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team2753.libs.AutoTransitioner;
import com.team2753.libs.VuMark;
import com.team2753.subsystems.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
    protected String savedVumark  = "None";

    private GoldAlignDetector detector;



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

    public enum Gold_Relative_Position{
        LEFT,
        RIGHT,
        ALIGNED,
        UNKNOWN
    }

    protected Gold_Position goldPosition  = null;

    protected static ElapsedTime runtime = new ElapsedTime();
    private boolean isAuto = false;

    /*
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    */

    private Telemetry.Item status;

    //Init  method

    public void waitForStart(String OpModeName, boolean auto){

        /*
        dashboardTelemetry.setAutoClear(true);
        status = dashboardTelemetry.addData("Status", "Initializing...");
        Telemetry.Item currentOpMode = dashboardTelemetry.addData("Running", OpModeName);
        dashboardTelemetry.update();
        */

        telemetry.setAutoClear(true);
        status = telemetry.addData("Status", "Initializing...");
        telemetry.addData("Running", OpModeName);

        Robot.init(this, auto);

        if(auto){
            isAuto = true;
            RobotLog.v("================ AutoTransitioner =============");
            AutoTransitioner.transitionOnStop(this, "Teleop"); //Auto Transitioning

            //initGoldDetector();

            //Relic Recovery Vuforia Jewel Detection removed. refer to Relic_Main for code

            //TODO: Mineral Position Loop here when SamplingOrderDetector is stablized

        }
        SetStatus("Initialized, Waiting for Start");
        //for now i have a loop to keep pinging the phone so it doesn't switch to teleop
        while(!isStarted() && !isStopRequested()){
            telemetry.update();
        }
        waitForStart();
        runtime.reset();
        SetStatus("Running OpMode");
        RobotLog.v("================ Running OpMode =============");
    }

    public void SetStatus(String update) {
        status.setValue(update);
        telemetry.update();
    }

    public void resetRuntime() {
        runtime.reset();
    }

    //Telemetry
    public void updateTelemetry() {

        if (isAuto) {
            telemetry.addData("Match Time", (int)(30 - getRuntime()));
        }
        if (!isAuto) {
            telemetry.addData("Match Time", (int)(120 - runtime.seconds()));
            if (runtime.seconds() > 90) {
                telemetry.addData("Phase", "End game");
            }
            if (runtime.seconds() > 120) {
                telemetry.addData("Phase", "Overtime");
            }
        }

        Robot.outputToTelemetry(telemetry);

        telemetry.update();
    }

    public void finalAction() {
        Robot.stop();
        threadSleep(10);
        requestOpModeStop();
    }

    public void initGoldDetector(){
        /*DogeCV*/
        //init DogeCV
        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        // Optional Tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        //TODO: tune perfectArea
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA; // Can also be PERFECT_AREA
        detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;
    }

    public void enableDetector(){
        detector.enable();
    }

    public double getGoldPos(){return detector.getXPosition();}

    public boolean goldVisible(){return detector.isFound();}

    public boolean goldAligned(){return detector.getAligned();}

    public void sampleGoldMineral(){

        int invisibleCounter = 0;
        while(!goldAligned()){
            if(!goldVisible()){
                invisibleCounter++;
                telemetry.addData("Invisiblity Count", invisibleCounter);
            }
            else if(!goldAligned()&&getGoldPos()>300){
                //turn to the right
                telemetry.addData("Turning", "Right");
            }
            else if (!goldAligned()&&getGoldPos()<300){
                //turn to the left
                telemetry.addData("Turning", "Left");
            }
        }
        //should now be aligned
        telemetry.addLine("Gold Aligned!!!");
    }

    private int vi = 0;
    public Gold_Relative_Position getGoldRelativePosition(){

        Gold_Relative_Position result = null;

        while(!goldVisible() && vi < 10) {
            if (goldAligned())
                result =  Gold_Relative_Position.ALIGNED;
            else if(!goldAligned()&&getGoldPos()>300){
                //turn to the right
                result = Gold_Relative_Position.RIGHT;
            }
            else if (!goldAligned()&&getGoldPos()<300){
                //turn to the left
                result = Gold_Relative_Position.LEFT;
            }
            else if (!goldVisible()){
                result = Gold_Relative_Position.UNKNOWN;
                vi++;
            }
        }

        return result;

    }

    public void threadSleep(long periodMs) {
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
