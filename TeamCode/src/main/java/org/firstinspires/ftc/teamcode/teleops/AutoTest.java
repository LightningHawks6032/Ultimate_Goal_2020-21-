package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

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
@Disabled
public class AutoTest extends OpMode {

    AutoController controller;
    VuforiaMethods voofinshmertsEvilIncorperated;
    RobotPos visionPos;

    @Override
    public void init() {
        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        voofinshmertsEvilIncorperated = new VuforiaMethods(hardwareMap);

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(0,0,0));
        voofinshmertsEvilIncorperated.initVuforia();
    }
    @Override
    public void loop() {
        setTarget();
        visionPos = voofinshmertsEvilIncorperated.getPosition(visionPos);
        if (voofinshmertsEvilIncorperated.targetVisible())
            controller.correctForVisionPos(visionPos);
        controller.update(getRuntime());

        telemetry.addLine(controller.getPos().toString());
        telemetry.update();
    }

    private void setTarget() {
        double x = (gamepad1.dpad_left ? -1 : 0) + (gamepad1.dpad_right ? 1 : 0); x *= 24;
        double y = (gamepad1.dpad_down ? -1 : 0) + (gamepad1.dpad_up ? 1 : 0); y *= 24;
        double r = gamepad1.left_trigger * Math.PI;
        controller.setTarget(new RobotPos(x, y, r));
    }

}
