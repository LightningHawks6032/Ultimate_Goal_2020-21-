package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.drive.DriveController;

@TeleOp(group = "yeet", name = "Gyro Test")
public class GyroTestTeleOp extends OpMode {
    BNO055IMU imu;
    DriveController driveController;

    int i = 0;
    @Override
    public void init() {

        driveController = new DriveController(DriveController.MotorClipMode.CLAMP, hardwareMap);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();

        // Todo: make acceleration work

        imu.initialize(params);

    }

    @Override
    public void loop() {

        double moveX = gamepad1.right_stick_x; // +: Fwds; -: Back;
        double moveY = gamepad1.left_stick_y; // +: Right; -: Left;
        double rotate = gamepad1.left_bumper?0:gamepad1.left_stick_x; // +: CW; -: CCW

        driveController.updateMotors_YXR(moveY,moveX,rotate);

        telemetry.addLine(imu.getAngularOrientation().toString());
        telemetry.addLine(imu.getAcceleration().toString());
        telemetry.addLine(imu.getPosition().toString());
    }
}
