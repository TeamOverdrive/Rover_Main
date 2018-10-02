package com.team2753.libs;

import android.graphics.Bitmap;
import android.os.Environment;

import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaRoverRuckus;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by joshua9889 on 10/14/2017.
 *
 */

public class VuMark {

    // Vuforia THings
    private String vuforiaLicenseKey ="";
    private com.team2753.libs.subsystems.ClosableVuforiaLocalizer vuforia;
    private VuforiaTrackables roverTrackables;
    private VuforiaTrackable roverTemplate;
    private String ouputVuMark = "None";
    public boolean targetVisible = false;
    VuforiaLocalizer.Parameters params;

    /*
    public enum roverVumarks{
        UNKNOWN,
        BLUE_ROVER,
        RED_FOOT,
        FRONT_CRATER,
        BACK_SPACE
    }
    */

    // Bitmap things
    private Image img = null;
    public Bitmap bm = null;

    /**
     * @param licenseKey Vuforia license key
     */
    public VuMark(String licenseKey){
        this.vuforiaLicenseKey = licenseKey;
    }


    public void setup(VuforiaLocalizer.CameraDirection cameraDirection){
        this.setup(cameraDirection, true);
    }

    /**
     * @param cameraDirection What camera to use
     *                        Front is Selfie Camera
     */
    public void setup(VuforiaLocalizer.CameraDirection cameraDirection, boolean display) {
        if(display){
            params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        } else {
            params = new VuforiaLocalizer.Parameters();
        }

        params.vuforiaLicenseKey = this.vuforiaLicenseKey;
        params.cameraDirection = cameraDirection;

        this.vuforia = new com.team2753.libs.subsystems.ClosableVuforiaLocalizer(params);
        vuforia.setFrameQueueCapacity(1);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

        /*
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        */

        roverTrackables = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = roverTrackables.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = roverTrackables.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = roverTrackables.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = roverTrackables.get(3);
        backSpace.setName("Back-Space");

        this.activateVuforia();
    }

    /**
     * Activate Vuforia Tracker
     */
    public void activateVuforia(){
        this.roverTrackables.activate();
    }

    /**
     * Disable Vuforia Tracker
     */
    public void disableVuforia(){
        this.roverTrackables.deactivate();
        if(!vuforia.closed)
            vuforia.close();
    }

    /**
     * @return output vumark
     */
    public String getOuputVuMark(){
        if(targetVisible) {
            return this.ouputVuMark;
        }
        else {
            return "UNKNOWN";
        }
    }

    /**
     * Use this method to update the current vumark
     */

    public void update(){
        // VuMark Update
        String vuMark = getOuputVuMark();
        if(vuMark != "UNKNOWN") {
            this.ouputVuMark = vuMark;
        }
    }

    /**
     * Used for Camera
     *
     * @param frame Vuforia frame
     * @param format Image type
     * @return Image
     */
    public static Image getImageFromFrame(VuforiaLocalizer.CloseableFrame frame, int format) {
        if (frame != null) {
            long numImgs = frame.getNumImages();
            for (int i = 0; i < numImgs; i++) {
                if (frame.getImage(i).getFormat() == format) {
                    return frame.getImage(i);
                }//if
                Thread.yield();
            }//for

        }
        return null;
    }

    /**
     * @param downsampling How much we should reduce the image by
     * @return The Bitmap from last Vuforia Frame
     */
    // Get Bitmap from vuforia
    public Bitmap getBm(int downsampling){
        try {
            img = getImageFromFrame(vuforia.getFrameQueue().take(), PIXEL_FORMAT.RGB565);
            Bitmap bm = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(img.getPixels());
            Bitmap scaled = Bitmap.createScaledBitmap(bm, bm.getWidth()/downsampling, bm.getHeight()/downsampling, true);
            bm.recycle();
            return scaled;
        } catch(Exception e){
            RobotLog.a("Problem with getBm");
            return null;
        }
    }

    public static int red(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    public static int green(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    public static int blue(int pixel) {
        return pixel & 0xff;
    }

    public static int gray(int pixel) {
        return (red(pixel) + green(pixel) + blue(pixel));
    }

    public static int yellow(int pixel){
        return (red(pixel) + green(pixel));
    }

    /**
     * @param finalBitmap Save Bitmap to /root/saved_images
     */
    public static void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        long n;
        n = System.currentTimeMillis();
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}