package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;
import org.firstinspires.ftc.teamcode.teleops.autos.AutoOpMode;

import java.util.ArrayList;
import java.util.List;

/*
                _/_/_/   _/_/_/   _/_/_/   _/  _/
                 _/     _/  _/   _/       _/  _/
                _/     _/  _/   _/_/_/   _/_/_/
               _/     _/  _/   _/           _/
            _/_/     _/_/_/   _/_/_/   _/_/_/

          _/  _/  _/     _/     _/_/_/
         _/  _/  _/   _/  _/   _/
        _/  _/  _/   _/_/_/   _/_/_/
         _/  _/     _/  _/       _/
        _/  _/     _/  _/   _/_/_/

    _/  _/   _/_/_/   _/_/     _/_/_/
   _/  _/   _/       _/  _/   _/
  _/_/_/   _/_/_/   _/_/_/   _/_/_/
 _/  _/   _/       _/  _/   _/
_/  _/   _/_/_/   _/  _/   _/_/_/
 */
@TeleOp(group = "drive", name = "Auto Test")
//@Disabled
public class AutoTest extends LinearOpMode {

    protected AutoController controller;
    protected VuforiaMethods vuforia;
    protected RobotPos visionPos;


    @Override
    public void runOpMode() throws InterruptedException {
        final Sounds sounds = new Sounds(hardwareMap);

        telemetry.addLine("STARTING");
        telemetry.update();

        final BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();


        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();
        sounds.play("Megalovania");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        double t = getRuntime();
        while (!gamepad1.x) {
            Thread.sleep(10);
            t = getRuntime();

            setTarget();

            visionPos = vuforia.getPosition(visionPos);
            if (visionPos != null) controller.correctForVisionPos(visionPos);
            controller.update(t);

            telemetry.addLine("POS: "+controller.getPos().toString());
            telemetry.update();
        }
        controller.driveController.setMotors_YXR(0,0,0);
        requestOpModeStop();
    }

    private void setTarget() {
        double x = (gamepad1.dpad_left ? -1 : 0) + (gamepad1.dpad_right ? 1 : 0); x *= 24;
        double y = (gamepad1.dpad_down ? -1 : 0) + (gamepad1.dpad_up ? 1 : 0); y *= 24;
        double r = gamepad1.left_trigger * Math.PI;
        controller.setTarget(new RobotPos(x, y, r));
    }

}
