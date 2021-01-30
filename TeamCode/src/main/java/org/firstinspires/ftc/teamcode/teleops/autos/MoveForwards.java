package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

@TeleOp(group = "autos", name = "move to line")
public class MoveForwards extends LinearOpMode{

    AutoController controller;
    VuforiaMethods voofinshmertsEvilIncorperated;
    RobotPos visionPos;

    @Override
    public void runOpMode() throws InterruptedException {
        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        voofinshmertsEvilIncorperated = new VuforiaMethods(hardwareMap);

        controller.init(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(0,0,0));
        voofinshmertsEvilIncorperated.initVuforia();

        waitForStart();

        while (!controller.withinThreshold(5,180)) {
            Thread.sleep(10);
            setTarget();
            visionPos = voofinshmertsEvilIncorperated.getPosition(visionPos);
            if (voofinshmertsEvilIncorperated.targetVisible())
                controller.update(getRuntime(), visionPos);

            telemetry.addLine(controller.getPos().toString());
            telemetry.update();
        }
    }

    private void setTarget() {
        double r = visionPos.r;
        double y = visionPos.y + 72* Constants.MM_PER_IN;
        double x = visionPos.x;
        //double x = (gamepad1.dpad_left ? -1 : 0) + (gamepad1.dpad_right ? 1 : 0); x *= 24;
        //double y = (gamepad1.dpad_down ? -1 : 0) + (gamepad1.dpad_up ? 1 : 0); y *= 24;
        //double r = gamepad1.left_trigger * Math.PI;
        controller.setTarget(new RobotPos(x, y, r));
    }

}
