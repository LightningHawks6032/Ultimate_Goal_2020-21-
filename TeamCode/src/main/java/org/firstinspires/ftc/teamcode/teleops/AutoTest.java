package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.AutoController;

@TeleOp(group = "yeet", name = "Auto Test")
public class AutoTest extends OpMode {

    AutoController controller;

    @Override
    public void init() {
        controller = new AutoController(hardwareMap);
        controller.init();
    }

    @Override
    public void loop() {
        telemetry.addLine(controller.update(gamepad1.a));
    }
}
