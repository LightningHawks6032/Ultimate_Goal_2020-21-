package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.AutoController;
import org.firstinspires.ftc.teamcode.drive.DcMotorController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;

import java.util.Random;

@TeleOp(group = "yeet", name = "Auto Test")
public class AutoTest extends LinearOpMode {

    AutoController controller;

    @Override
    public void runOpMode() throws InterruptedException {
        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh);
        waitForStart();

        controller.init(getRuntime());
        while (true) {
            double x = gamepad1.left_stick_x*12;
            double y = gamepad1.left_stick_y*12;
            controller.setTarget(new AutoController.RobotPos(x,y,0));
            controller.update(getRuntime());
            if (gamepad1.a)
                controller.resetBasePos();
        }
    }

}
