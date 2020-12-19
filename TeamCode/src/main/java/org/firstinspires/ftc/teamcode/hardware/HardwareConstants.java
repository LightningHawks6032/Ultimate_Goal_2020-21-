package org.firstinspires.ftc.teamcode.hardware;

public class HardwareConstants {
    public static final double WHEEL_DIA_MM = 96;

    public static final double TICKS_PER_REV = 383.6 * 6;
    public static final double TICKS_PER_MM = TICKS_PER_REV / (WHEEL_DIA_MM * Math.PI);
}
