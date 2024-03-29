package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.drive.IMUAccelerationIntegrator;
import org.firstinspires.ftc.teamcode.hardware.groups.AngleDcMotor;
import org.firstinspires.ftc.teamcode.hardware.groups.AngleServo;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.groups.MRGyro;
import org.firstinspires.ftc.teamcode.hardware.groups.TandemMotors;
import org.firstinspires.ftc.teamcode.hardware.groups.TandemServos;

public class BotHardware {
    private final HardwareMap hardwareMap;

    public final DriveMotors motors;
    public final DcMotor intakeMotor;
    public final TandemMotors outtakeMotor;
    public final AngleDcMotor wobbleLifter;
    public final AngleServo outtakeAngle;
    public final CRServo wobbleGrabber;
    public final Servo launchServo;

    public final BNO055IMU imu;
    public final MRGyro gyro;
    public final IMUAccelerationIntegrator accInt;

    private double motorBoost = 1;
    private double motorMax = 1.2;
    private double motorMin = 0.05;
    private double motorPowOffset = 0.025;
    private double accBoost = 1;

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

        wobbleLifter = new AngleDcMotor(getMotor(Constants.MapKeys.MOTOR_WOBBLE_LIFTER, Constants.MotorDirections.WOBBLE_LIFTER),0);
        wobbleGrabber = getServoCont(Constants.MapKeys.SERVO_WOBBLE_GRABBER, Constants.MotorDirections.WOBBLE_GRABBER_ANGLE);

        wobbleLifter.setPower(0.2);

        outtakeAngle = new AngleServo(//new TandemServos(
                getServo(Constants.MapKeys.SERVO_OUTTAKE_ANGLE, Constants.MotorDirections.OUTTAKE_ANGLE)
                //getServo(Constants.MapKeys.SERVO_OUTTAKE_ANGLE_BAK, Constants.MotorDirections.OUTTAKE_ANGLE_BAK)
        //)
        ,Constants.OUTTAKE_ANGLE_SPEED,Constants.OUTTAKE_ANGLE_MIN,Constants.OUTTAKE_ANGLE_MAX);

        launchServo = getServo(Constants.MapKeys.SERVO_LAUNCH,Constants.MotorDirections.LAUNCH_SERVO);

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
    public Servo getServo(String id, Servo.Direction direction) {
        Servo motor = hardwareMap.get(Servo.class, id);
        motor.setDirection(direction);
        return motor;
    }
    public CRServo getServoCont(String id, CRServo.Direction direction) {
        CRServo motor = hardwareMap.get(CRServo.class, id);
        motor.setDirection(direction);
        return motor;
    }

    public double getMotorBoost() { return motorBoost; }
    public double getMotorMax() { return motorMax; }
    public double getMotorMin() { return motorMin; }
    public double getMotorPowOffset() { return motorPowOffset; }
    public double getAccBoost() { return accBoost; }
    public void setMotorPowerModifiers(double boost, double max, double min, double powOffset) {
        motorBoost = boost;
        motorMax = max;
        motorMin = min;
        motorPowOffset = powOffset;
    }
    public void setAccBoost(double accBoost) { this.accBoost = accBoost; }
}
