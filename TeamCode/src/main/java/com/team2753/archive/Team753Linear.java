package com.team2753.archive;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team2753.libs.AutoTransitioner;
import com.team2753.archive.libs.VuMark;
import com.team2753.archive.subsystems.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Point;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 */
public abstract class Team753Linear extends LinearOpMode{

    public Robot Robot = com.team2753.archive.subsystems.Robot.getInstance();

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

    protected Gold_Position goldPosition  = null;

    private static ElapsedTime timer1 = new ElapsedTime();
    private boolean isAuto = false;

    /*
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    */

    private Telemetry.Item status;

    //Init  method

    public void waitForStart(String OpModeName, boolean auto){

        telemetry.setAutoClear(true);
        status = telemetry.addData("Status", "Initializing...");
        //telemetry.addLine("Initializing...");
        telemetry.addData("Running", OpModeName);

        Robot.init(this, auto);

        if(auto){
            isAuto = true;
            RobotLog.v("================ AutoTransitioner =============");
            AutoTransitioner.transitionOnStop(this, "Teleop"); //Auto Transitioning

            initGoldDetector();

            enableDetector();
            while(!isStarted() && !isStopRequested()){
                if(goldVisible()){
                    if(goldAligned()) {
                        goldPosition = Gold_Position.LEFT;
                        telemetry.addLine("Gold Left");
                    }
                    else {
                        goldPosition = Gold_Position.CENTER;
                        telemetry.addLine("Gold Center");
                    }
                }
                else {
                    goldPosition = Gold_Position.RIGHT;
                    telemetry.addLine("Gold Right");
                }
                telemetry.update();
            }
            disableDetector();
        }
        SetStatus("Initialized, Waiting for Start");
        //for now i have a loop to keep pinging the phone so it doesn't switch to teleop
        while(!isStarted() && !isStopRequested()){
            telemetry.update();
        }
        waitForStart();

        timer1.reset();
        SetStatus("Running OpMode");
        RobotLog.v("================ Running OpMode =============");
    }

    public void SetStatus(String update) {
        status.setValue(update);
        telemetry.update();
    }

    public void resetRuntime() {
        timer1.reset();
    }

    //Telemetry
    public void updateTelemetry() {
        //TODO: make this better
        /*
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
        */

        Robot.outputToTelemetry(telemetry);

        telemetry.update();
    }

    public void finalAction() {
        Robot.stop();
        setTimer1(10);
        requestOpModeStop();
    }

    public void initGoldDetector(){
        /*DogeCV*/
        //init DogeCV
        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        // Optional Tuning
        detector.alignSize = 300; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = -160; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA; // Can also be PERFECT_AREA
        detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;

        //Cropping
        detector.cropTLCorner = new Point(0, 175); //Sets the top left corner of the new image, in pixel (x,y) coordinates
        detector.cropBRCorner = new Point(639, 200);
    }

    public void enableDetector(){
        detector.enable();
    }

    public void disableDetector(){
        detector.disable();
    }

    public double getGoldPos(){return detector.getXPosition();}

    public boolean goldVisible(){return detector.isFound();}

    public boolean goldAligned(){return detector.getAligned();}

    /*

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
    */

    public void setTimer1(long periodMs) {
        timer1.reset();
        while(timer1.milliseconds()<periodMs && opModeIsActive()){}
    }
}
