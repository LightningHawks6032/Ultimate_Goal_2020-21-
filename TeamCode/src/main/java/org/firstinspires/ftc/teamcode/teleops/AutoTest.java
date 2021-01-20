package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.AutoController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
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
public class AutoTest extends LinearOpMode {

    AutoController controller;

    @Override
    public void runOpMode() {
        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh,telemetry);

        waitForStart();
        controller.init(getRuntime());
        controller.resetBasePos();
        controller.setPos(new AutoController.RobotPos(0,0,0));
        while (!gamepad1.x) {
            double x = (gamepad1.dpad_left?-1:0)+(gamepad1.dpad_right?1:0);x*=24;
            double y = (gamepad1.dpad_down?-1:0)+(gamepad1.dpad_up?1:0);y*=24;
            controller.setTarget(new AutoController.RobotPos(x,y,0));
            controller.update(getRuntime());
            if (gamepad1.a) {
                controller.resetBasePos();
                controller.setPos(new AutoController.RobotPos(0,0,0));
            }
            //controller.driveController.setMotors_YXR(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
            telemetry.addLine(x+","+y);
            telemetry.addLine(controller.getPos().toString());
            telemetry.update();
            opModeIsActive();
        }
        requestOpModeStop();
    }

}
