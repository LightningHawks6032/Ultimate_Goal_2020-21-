package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.DriveController;

@TeleOp(group = "what does this do?", name = "Joey's Drive TeleOp")
@Disabled
public class JoeysDriveTeleOp extends OpMode {

    DriveController driveController;

    public void init(){
        driveController = new DriveController(DriveController.MotorClipMode.CLAMP, hardwareMap);
    }


    public void loop() {
        double moveX = gamepad1.right_stick_x; // +: Fwds; -: Back;
        double moveY = gamepad1.left_stick_y; // +: Right; -: Left;
        double rotate = gamepad1.left_bumper?0:gamepad1.left_stick_x; // +: CW; -: CCW

        driveController.updateMotors_YXR(moveY,moveX,rotate);

        telemetry.update();
    }
}
