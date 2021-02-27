package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.PositionTracker;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;

@TeleOp(group = "drive", name = "JDrive")
//@Disabled
public class JoeysDriveTeleOp extends OpMode {

    DriveController driveController;
    BotHardware hardware;
    PositionTracker tracker;

    public void init(){
        double t = getRuntime();
        hardware = new BotHardware(hardwareMap);
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, hardware);
        driveController.updateMotors(t);
        tracker = new PositionTracker(hardware,telemetry,driveController);
        tracker.init(t);
    }


    public void loop() {
        double moveX = gamepad1.right_stick_x; // +: Fwds; -: Back;
        double moveY = gamepad1.left_stick_y; // +: Right; -: Left;
        double rotate = gamepad1.left_bumper?0:gamepad1.left_stick_x; // +: CW; -: CCW
        double outtake = gamepad1.left_trigger;
        double intake = gamepad1.left_trigger;
        double dv = (gamepad1.dpad_up?1:0)-(gamepad1.dpad_down?1:0);

        double t = getRuntime();

        driveController.setMotors_YXR(moveY,moveX,rotate);
        driveController.updateMotors(t);
        hardware.outtakeMotor.setPower(Constants.OUTTAKE_POWER_FAC*outtake);
        hardware.intakeMotor.setPower(Constants.INTAKE_POWER_FAC*intake);
        hardware.outtakeAngle.update(t,dv);
        tracker.updatePosition(t);

        telemetry.update();
    }
}
