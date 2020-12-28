package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.AutoController;
import org.firstinspires.ftc.teamcode.drive.DcMotorRampable;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;

@TeleOp(group = "yeet", name = "Auto Test")
public class AutoTest extends LinearOpMode {

    AutoController controller;

    @Override
    public void runOpMode() throws InterruptedException {

        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh);

        waitForStart();

        while (!gamepad1.a) Thread.sleep(10);

        int distTix = (int) (BotHardwareInfo.TICKS_PER_MM*50);

        System.out.println(BotHardwareInfo.TICKS_PER_MM);
        System.out.println("TIX"+distTix);

        bh.motors.fl.init(getRuntime());
        bh.motors.fl.addPoint(new DcMotorRampable.Point(1,distTix));
        bh.motors.fr.init(getRuntime());
        bh.motors.fr.addPoint(new DcMotorRampable.Point(1,distTix));
        bh.motors.bl.init(getRuntime());
        bh.motors.bl.addPoint(new DcMotorRampable.Point(1,distTix));
        bh.motors.br.init(getRuntime());
        bh.motors.br.addPoint(new DcMotorRampable.Point(1,distTix));
        while (true) {
            Thread.sleep(10);
            bh.motors.fl.update(getRuntime());
            bh.motors.fr.update(getRuntime());
            bh.motors.bl.update(getRuntime());
            bh.motors.br.update(getRuntime());
            if (bh.motors.fl.getPoints().size() == 0) return;
            if (bh.motors.fr.getPoints().size() == 0) return;
            if (bh.motors.bl.getPoints().size() == 0) return;
            if (bh.motors.br.getPoints().size() == 0) return;
        }
    }

}
