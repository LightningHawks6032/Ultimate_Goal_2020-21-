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

        while (true) {
            telemetry.addLine("YEET");
            telemetry.update();
            while (!gamepad1.a) Thread.sleep(10);

            Random r = new Random();

            int dist = r.nextBoolean() ? 0 : 12;
            telemetry.addLine(dist+"in");
            telemetry.update();

            bh.motors.fl.init(getRuntime());
            bh.motors.fr.init(getRuntime());
            bh.motors.bl.init(getRuntime());
            bh.motors.br.init(getRuntime());

            bh.motors.fl.setTargetDist(dist);
            bh.motors.fr.setTargetDist(dist);
            bh.motors.bl.setTargetDist(dist);
            bh.motors.br.setTargetDist(dist);

            while (true) {
                Thread.sleep(10);
                bh.motors.fl.update(getRuntime());
                bh.motors.fr.update(getRuntime());
                bh.motors.bl.update(getRuntime());
                bh.motors.br.update(getRuntime());
                int count = 0;
                if (bh.motors.fl.getDone()) count++;
                if (bh.motors.fr.getDone()) count++;
                if (bh.motors.bl.getDone()) count++;
                if (bh.motors.br.getDone()) count++;
                if (count == 4) break;
            }
        }
    }

}
