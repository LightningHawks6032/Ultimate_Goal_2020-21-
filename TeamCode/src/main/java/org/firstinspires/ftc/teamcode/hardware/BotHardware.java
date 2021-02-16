package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.drive.IMUAccelerationIntegrator;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.groups.MRGyro;
import org.firstinspires.ftc.teamcode.hardware.groups.TandemMotors;

public class BotHardware {
    private final HardwareMap hardwareMap;

    public final DriveMotors motors;
    public final DcMotor intakeMotor;
    public final TandemMotors outtakeMotor;

    public final BNO055IMU imu;
    public final MRGyro gyro;
    public final IMUAccelerationIntegrator accInt;

    private double motorBoost = 1;
    private double motorMax = 1.2;
    private double motorMin = 0.05;

    public BotHardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        DcMotor MOTOR_FL = getMotor(Constants.MapKeys.MOTOR_FL, Constants.MotorDirections.DRIVE_FL);
        DcMotor MOTOR_BL = getMotor(Constants.MapKeys.MOTOR_BL, Constants.MotorDirections.DRIVE_BL);
        DcMotor MOTOR_FR = getMotor(Constants.MapKeys.MOTOR_FR, Constants.MotorDirections.DRIVE_FR);
        DcMotor MOTOR_BR = getMotor(Constants.MapKeys.MOTOR_BR, Constants.MotorDirections.DRIVE_BR);
        motors = new DriveMotors(MOTOR_FL,MOTOR_FR,MOTOR_BL,MOTOR_BR,this);
        intakeMotor = getMotor(Constants.MapKeys.MOTOR_INTAKE,Constants.MotorDirections.INTAKE);
        DcMotor MOTOR_OUTTAKE_0 = getMotor(Constants.MapKeys.MOTOR_OUTTAKE_FWD,Constants.MotorDirections.OUTTAKE_FWD);
        DcMotor MOTOR_OUTTAKE_1 = getMotor(Constants.MapKeys.MOTOR_OUTTAKE_BAK,Constants.MotorDirections.OUTTAKE_BAK);
        outtakeMotor = new TandemMotors(MOTOR_OUTTAKE_1,MOTOR_OUTTAKE_0);

        imu = hardwareMap.get(BNO055IMU.class, Constants.MapKeys.IMU);
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.mode = BNO055IMU.SensorMode.IMU;
        params.calibrationData = new BNO055IMU.CalibrationData();
        accInt = new IMUAccelerationIntegrator();
        imu.initialize(params);
        accInt.initialize(params,new Position(),new Velocity());


        gyro = new MRGyro(hardwareMap.get(ModernRoboticsI2cGyro.class, Constants.MapKeys.GYRO),true);
    }


    public DcMotor getMotor(String id, DcMotorSimple.Direction direction) {
        DcMotor motor = hardwareMap.get(DcMotor.class, id);
        motor.setDirection(direction);
        return motor;
    }

    public double getMotorBoost() { return motorBoost; }
    public double getMotorMax() { return motorMax; }
    public double getMotorMin() { return motorMin; }
    public void setMotorPowerModifiers(double boost, double max, double min) {
        motorBoost = boost;
        motorMax = max;
        motorMin = min;
    }
}
