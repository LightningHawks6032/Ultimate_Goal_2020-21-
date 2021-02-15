package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

@Autonomous(group = "autos", name = "move to line")
public class MoveForwards extends LinearOpMode{

    AutoController controller;
    VuforiaMethods vuforia;
    RobotPos visionPos;

    @Override
    public void runOpMode() throws InterruptedException {
        Sounds sounds = new Sounds(hardwareMap);
        sounds.play("Megalovania");

        telemetry.addLine("STARTING");
        telemetry.update();

        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();

        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(0,0,0));
        controller.setTarget(new RobotPos(0,12,0));

        while (!controller.withinThreshold(5,0.3,0.5)) {
            Thread.sleep(10);
            visionPos = vuforia.getPosition(visionPos);
            if (visionPos != null) controller.correctForVisionPos(visionPos);
            controller.update(getRuntime());

            telemetry.addLine("POS: "+controller.getPos().toString());
            telemetry.update();
        }
        controller.driveController.setMotors_YXR(0,0,0);
        requestOpModeStop();
    }

}
