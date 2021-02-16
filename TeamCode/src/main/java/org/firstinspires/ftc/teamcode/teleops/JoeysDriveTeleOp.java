package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;

@TeleOp(group = "drive", name = "JDrive")
//@Disabled
public class JoeysDriveTeleOp extends OpMode {

    DriveController driveController;
    BotHardware hardware;

    public void init(){
        hardware = new BotHardware(hardwareMap);
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, hardware);
        driveController.updateMotors(getRuntime());
    }


    public void loop() {
        double moveX = gamepad1.right_stick_x; // +: Fwds; -: Back;
        double moveY = gamepad1.left_stick_y; // +: Right; -: Left;
        double rotate = gamepad1.left_bumper?0:gamepad1.left_stick_x; // +: CW; -: CCW
        double outteik = gamepad1.left_trigger;


        driveController.setMotors_YXR(moveY,moveX,rotate);
        driveController.updateMotors(getRuntime());
        hardware.outtakeMotor.setPower(Constants.OUTTAKE_POWER_FAC*outteik);

        telemetry.update();
    }
}
