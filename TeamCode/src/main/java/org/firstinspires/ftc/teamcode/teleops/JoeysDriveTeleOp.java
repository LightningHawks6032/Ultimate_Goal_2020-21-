package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.DriveController;

@TeleOp(group = "what does this do?", name = "Joey's Drive TeleOp")
public class JoeysDriveTeleOp extends OpMode {

    DriveController driveController;

    public void init(){
        DcMotor motorFL = hardwareMap.get(DcMotor.class, "fl");
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        DcMotor motorBL = hardwareMap.get(DcMotor.class, "bl");
        motorBL.setDirection(DcMotor.Direction.FORWARD);

        DcMotor motorFR = hardwareMap.get(DcMotor.class, "fr");
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        DcMotor motorBR = hardwareMap.get(DcMotor.class, "br");
        motorBR.setDirection(DcMotor.Direction.REVERSE);

        driveController = new DriveController(DriveController.MotorClipMode.CLAMP);
        driveController.setMotorBL(motorBL);
        driveController.setMotorBR(motorBR);
        driveController.setMotorFL(motorFL);
        driveController.setMotorFR(motorFR);
    }


    public void loop() {
        double moveX = gamepad1.right_stick_x; // +: Fwds; -: Back;
        double moveY = gamepad1.left_stick_y; // +: Right; -: Left;
        double rotate = gamepad1.left_bumper?0:gamepad1.left_stick_x; // +: CW; -: CCW

        driveController.updateMotors(moveY,moveX,rotate);

        telemetry.update();
    }
}
