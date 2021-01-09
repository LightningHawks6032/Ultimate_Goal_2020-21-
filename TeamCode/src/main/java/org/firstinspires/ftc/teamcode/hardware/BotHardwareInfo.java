package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class BotHardwareInfo {
    //public static final double MOTORRAMP_TICKSPEEDFAC = 500;
    //public static final int MOTORRAMP_MINTHRESH = 200;
    //public static final double MOTORRAMP_MAXACC = 4;

    public static final double WHEEL_DIA_MM = 96;

    public static final double TICKS_PER_REV = 145.6 * 6;
    public static final double TICKS_PER_MM = TICKS_PER_REV / (WHEEL_DIA_MM * Math.PI);
    public static final double TICKS_PER_IN = TICKS_PER_MM*25.4;

    public static final double ROTPOW_TO_RAD = 1/5.5;

    public static class MapKeys {
        public static final String MOTOR_FL = "fl";
        public static final String MOTOR_FR = "fr";
        public static final String MOTOR_BL = "bl";
        public static final String MOTOR_BR = "br";
        public static final String IMU = "imu";
        public static final String GYRO = "gyro";
    }
    public static class MotorDirections {
        public static final DcMotorSimple.Direction DRIVE_FL = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction DRIVE_BL = DcMotorSimple.Direction.FORWARD;
        public static final DcMotorSimple.Direction DRIVE_FR = DcMotorSimple.Direction.REVERSE;
        public static final DcMotorSimple.Direction DRIVE_BR = DcMotorSimple.Direction.REVERSE;
    }
}
