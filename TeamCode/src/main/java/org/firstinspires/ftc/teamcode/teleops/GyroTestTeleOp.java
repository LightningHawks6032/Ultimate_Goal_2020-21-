package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

@TeleOp(group = "yeet", name = "Gyro Test (broken, help)")
public class GyroTestTeleOp extends OpMode {
    GyroSensor gyro;
    BNO055IMU imu;
    int i = 0;
    @Override
    public void init() {
        gyro = hardwareMap.get(GyroSensor.class,"gyro");
        imu = hardwareMap.get(BNO055IMU.class, "imu 1");
    }

    @Override
    public void loop() {
        telemetry.addLine("TEST:" +i++);
        //telemetry.addLine("GYRO:"+gyro.getConnectionInfo()+"; "+gyro.getRotationFraction());
        telemetry.addLine("IMU:"+imu.getAngularOrientation().firstAngle);
    }
}
