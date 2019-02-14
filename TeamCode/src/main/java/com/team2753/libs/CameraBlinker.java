package com.team2753.libs;

import android.hardware.Camera;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * Created by joshua9889 on 3/24/2018.
 */

public class CameraBlinker {

    private Camera camera;
    private Camera.Parameters parm;

    public void on(){
        camera = Camera.open();
        parm = camera.getParameters();
        parm.setFlashMode(FLASH_MODE_TORCH);
        camera.setParameters(parm);
    }

    public void off(){
        parm.setFlashMode(FLASH_MODE_OFF);
        camera.setParameters(parm);
        camera.release();
    }
}
