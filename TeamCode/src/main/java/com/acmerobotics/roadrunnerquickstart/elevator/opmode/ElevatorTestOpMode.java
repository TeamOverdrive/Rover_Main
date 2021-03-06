package com.acmerobotics.roadrunnerquickstart.elevator.opmode;

import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.acmerobotics.roadrunnerquickstart.elevator.Elevator;

/*
 * Simple test of motion-profiled elevator autonomous operation. The elevator should move *smoothly*
 * between random heights.
 */
@Disabled
@Autonomous
public class ElevatorTestOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Elevator elevator = new Elevator(hardwareMap);
        NanoClock clock = NanoClock.system();

        waitForStart();

        if (isStopRequested()) return;

        while (!isStopRequested()) {
            elevator.setHeight(Elevator.MAX_HEIGHT * Math.random());

            double startTime = clock.seconds();
            while (!isStopRequested() && (clock.seconds() - startTime) < 5) {
                elevator.update();
            }
        }
    }
}
