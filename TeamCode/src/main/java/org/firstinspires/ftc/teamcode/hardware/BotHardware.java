package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRGyro;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class BotHardware {
    private final HardwareMap hardwareMap;

    public final DriveMotors motors;

    public final BNO055IMU imu;
    public final MRGyro gyro;
    public final IMUAccelerationIntegrator accInt;

    private double motorBoost = 0.3;
    private double motorMax = 1.2;

    public BotHardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        DcMotor MOTOR_FL = getMotor(BotHardwareInfo.MapKeys.MOTOR_FL,BotHardwareInfo.MotorDirections.DRIVE_FL);
        DcMotor MOTOR_BL = getMotor(BotHardwareInfo.MapKeys.MOTOR_BL,BotHardwareInfo.MotorDirections.DRIVE_BL);
        DcMotor MOTOR_FR = getMotor(BotHardwareInfo.MapKeys.MOTOR_FR,BotHardwareInfo.MotorDirections.DRIVE_FR);
        DcMotor MOTOR_BR = getMotor(BotHardwareInfo.MapKeys.MOTOR_BR,BotHardwareInfo.MotorDirections.DRIVE_BR);
        motors = new DriveMotors(MOTOR_FL,MOTOR_FR,MOTOR_BL,MOTOR_BR,this);

        imu = hardwareMap.get(BNO055IMU.class, BotHardwareInfo.MapKeys.IMU);
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.mode = BNO055IMU.SensorMode.IMU;
        params.calibrationData = new BNO055IMU.CalibrationData();
        accInt = new IMUAccelerationIntegrator();
        imu.initialize(params);
        accInt.initialize(params,new Position(),new Velocity());


        gyro = new MRGyro(hardwareMap.get(ModernRoboticsI2cGyro.class,BotHardwareInfo.MapKeys.GYRO),true);
    }


    public DcMotor getMotor(String id, DcMotorSimple.Direction direction) {
        DcMotor motor = hardwareMap.get(DcMotor.class, id);
        motor.setDirection(direction);
        return motor;
    }

    public double getMotorBoost() { return motorBoost; }
    public double getMotorMax() { return motorMax; }
    public void setMotorPowerModifiers(double boost, double max) {
        motorBoost = boost;
        motorMax = max;
    }
}
