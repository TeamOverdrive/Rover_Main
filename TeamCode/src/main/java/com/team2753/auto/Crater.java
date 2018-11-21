package com.team2753.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team2753.Team753Linear;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/3/2018.
 */

@Autonomous(name = "Crater Auto", group = "0_auto")
//@Disabled
public class Crater extends Team753Linear{
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("Crater Autonomous", true);
        while(opModeIsActive() && !isStopRequested()) {
            //Flip phone up

            //Land
            Robot.getLift().setPower(1);
            threadSleep(2000);
            Robot.getDrive().encoderTurn(5, 0.8, 5, this);
            Robot.getDrive().encoderTurn(-10, 0.8, 5, this);
            Robot.getDrive().encoderTurn(5, 0.8, 5, this);
            Robot.getDrive().encoderDrive(0.9, 3, 3, 5, this);

            //Deploy intake

            //Forward ~3 inches
            Robot.getDrive().encoderDrive(0.8, 3, 3, 5, this);
            Robot.getLift().setPower(-1);
            sleep(3000);
            Robot.getLift().brake();



            int oops = 0;
            //Align to Mineral
            while (getGoldRelativePosition() != Gold_Relative_Position.ALIGNED && oops < 10) {
                switch (getGoldRelativePosition()) {
                    case LEFT:
                        Robot.getDrive().encoderTurn(30, 0.8, 5, this);
                    case RIGHT:
                        Robot.getDrive().encoderTurn(-30, 0.8, 5, this);
                    case UNKNOWN:
                        oops++;
                }
            }

            //Collect Mineral
            Robot.getDrive().encoderDrive(0.8, 30, 30, 8, this);

            //Drive to ~~Depot~~ Crater
            Robot.getDrive().encoderDrive(0.8, 30, 30, 8, this);

            //Deposit Team Marker
            Robot.getMarker().retract();

            //Return to Crater

            finalAction();
        }
    }
}
