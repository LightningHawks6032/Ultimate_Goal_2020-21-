package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.AutoController;

@TeleOp(group = "yeet", name = "Auto Test")
public class AutoTest extends OpMode {

    AutoController controller;

    @Override
    public void init() {
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        controller = new AutoController(hardwareMap);
        controller.init(imu);
    }

    @Override
    public void loop() {
        telemetry.addLine(controller.update(gamepad1.a));
    }
}
