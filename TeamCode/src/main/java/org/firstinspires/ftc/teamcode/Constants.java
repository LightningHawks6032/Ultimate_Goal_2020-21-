package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {
    //public static final double MOTORRAMP_TICKSPEEDFAC = 500;
    //public static final int MOTORRAMP_MINTHRESH = 200;
    //public static final double MOTORRAMP_MAXACC = 4;

    public static final double MM_PER_IN = 25.4;

    public static final double WHEEL_DIA_MM = 96;
    public static final double TICKS_PER_REV = 145.6 * 6;
    public static final double TICKS_PER_MM = TICKS_PER_REV / (WHEEL_DIA_MM * Math.PI);
    public static final double TICKS_PER_IN = TICKS_PER_MM*MM_PER_IN;

    public static final double MOVE_SCALE_FWD = 1.5*72.0/63.75;//24in : 9in irl  9/24*4
    public static final double MOVE_SCALE_SRF = 1.16666667;//24in : 7in irl  7/24*4
    public static final double MOVE_SCALE_ROT = 0.93;//idk

    public static final double ROTPOW_TO_RAD = 1/5.5;

    public static final double MAX_MOTORACC = 4;

    public static final double MOTOR_DECELL_DIST = 15;
    public static final double MOTOR_DECELL_ROTDIST = 1.5;

    public static final double ROBOTPOS_BLEND_FAC = 0;

    public static final double OUTTAKE_POWER_FAC = 1;
    public static final double INTAKE_POWER_FAC = 0.5;

    public static final double OUTTAKE_ANGLE_MAX = 0.23;
    public static final double OUTTAKE_ANGLE_MIN = 0.15;
    public static final double OUTTAKE_ANGLE_SPEED = 0.85;

    public static final double TARGET_THRESH_LIN = 4;
    public static final double TARGET_THRESH_ROT = 0.2;
    public static final double TARGET_THRESH_VEL = 0.4;

    public static class MapKeys {
        public static final String MOTOR_FL = "fl";
        public static final String MOTOR_FR = "fr";
        public static final String MOTOR_BL = "bl";
        public static final String MOTOR_BR = "br";
        public static final String MOTOR_INTAKE = "intake";
        public static final String MOTOR_OUTTAKE_FWD = "outtake-fwd";
        public static final String MOTOR_OUTTAKE_BAK = "outtake-bak";
        public static final String IMU = "imu";
        public static final String GYRO = "gyro";
        public static final String MOTOR_WOBBLE_LIFTER = "wobble-lifter";
        public static final String SERVO_WOBBLE_GRABBER = "wobble-grabber";
        public static final String SERVO_LAUNCH = "launch-servo";

        public static final String SERVO_OUTTAKE_ANGLE = "outtake-angle";
        //public static final String SERVO_OUTTAKE_ANGLE_BAK = "outtake-angle-bak";
    }
    public static class MotorDirections {
        public static final DcMotorSimple.Direction DRIVE_FL = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction DRIVE_BL = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction DRIVE_FR = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction DRIVE_BR = DcMotorSimple.Direction.REVERSE;

        public static final DcMotorSimple.Direction INTAKE = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction OUTTAKE_FWD = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction OUTTAKE_BAK = DcMotorSimple.Direction.REVERSE;

        public static final DcMotorSimple.Direction WOBBLE_LIFTER = DcMotorSimple.Direction.FORWARD;

        public static final Servo.Direction LAUNCH_SERVO = Servo.Direction.FORWARD;

        public static final Servo.Direction OUTTAKE_ANGLE = Servo.Direction.FORWARD;
        public static final Servo.Direction WOBBLE_GRABBER_ANGLE = Servo.Direction.FORWARD;
        //public static final Servo.Direction OUTTAKE_ANGLE_BAK = Servo.Direction.REVERSE;
    }
}
