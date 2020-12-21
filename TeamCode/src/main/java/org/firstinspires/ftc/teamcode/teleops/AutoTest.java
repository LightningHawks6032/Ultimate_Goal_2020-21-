package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.AutoController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;

@TeleOp(group = "yeet", name = "Auto Test")
public class AutoTest extends LinearOpMode {

    AutoController controller;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new AutoController(new BotHardware(hardwareMap));
        controller.init();

        waitForStart();

        while (getRuntime() < 5) {

        }
    }

}
