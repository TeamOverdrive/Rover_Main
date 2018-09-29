package com.team2753.other.test;

import android.graphics.Bitmap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

@TeleOp
@Config
public class VuforiaStreamExampleOpMode extends LinearOpMode {

    public static final String VUFORIA_LICENSE_KEY = "AeUsQDb/////AAAAGXsDAQwNS0SWopXJpAHyRntcnTcoWD8Tns" +
            "R6PWGX9OwmlIhNxQgn8RX/1cH2RXXTsuSkHh6OjfMoCuHt35rhumaUsLnk8MZZJ7P9PEu+uSsUbH1hHcnnB" +
            "6GzJnX/FqlZJX5HWWfeQva5s4OHJEwSbPR2zxhkRxntAjeuIPGVSHeIseAikPB0NF0SqEiPZea+PWrxpryP" +
            "/bxKqy7VA77krKFtgDi6amam+vWvBCqyIo6tXxbo0w8q/HCXo4v/4UYyoFLRx1l1d2Wya5an5SwFfU3eKxy" +
            "0BYc3tnsaaDJww59RNJ6IK9D3PZM+oPDrmF9ukQrc/jw+u+6Zm4wQHieHt9urSwLR7dgz0V3aatDx1V7y";

    // adjust these parameters to suit your needs
    public static int MIN_LOOP_TIME = 100;
    public static int QUALITY = 25;
    public static double SCALE = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        msStuckDetectStop = 2500;

        FtcDashboard dashboard = FtcDashboard.getInstance();

        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(1);

        BlockingQueue<VuforiaLocalizer.CloseableFrame> frameQueue = vuforia.getFrameQueue();

        waitForStart();

        while (opModeIsActive()) {
            if (!frameQueue.isEmpty()) {
                long start = System.nanoTime();

                VuforiaLocalizer.CloseableFrame vuforiaFrame = null;
                try {
                    vuforiaFrame = frameQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (vuforiaFrame == null) {
                    continue;
                }

                for (int i = 0; i < vuforiaFrame.getNumImages(); i++) {
                    Image image = vuforiaFrame.getImage(i);
                    if (image.getFormat() == PIXEL_FORMAT.RGB565) {
                        int imageWidth = image.getWidth(), imageHeight = image.getHeight();
                        ByteBuffer byteBuffer = image.getPixels();

                        Bitmap original = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.RGB_565);
                        original.copyPixelsFromBuffer(byteBuffer);
                        Bitmap scaled = Bitmap.createScaledBitmap(original, (int) (SCALE * imageWidth), (int) (SCALE * imageHeight), false);

                        //dashboard.setImageQuality(QUALITY);
                        //dashboard.sendImage(scaled);
                    }
                }

                vuforiaFrame.close();

                long ms = (System.nanoTime() - start) / 1_000_000;
                long sleepTime = MIN_LOOP_TIME - ms;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}