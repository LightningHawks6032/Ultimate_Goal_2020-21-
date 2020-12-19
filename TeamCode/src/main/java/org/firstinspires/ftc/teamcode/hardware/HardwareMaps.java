package org.firstinspires.ftc.teamcode.hardware;

public class HardwareMaps {
    public static final MapKeys DRIVE = new MapKeys("fl","fr","bl","br","imu");

    public static class MapKeys {
        public final String motorFL;
        public final String motorFR;
        public final String motorBL;
        public final String motorBR;

        public final String imu;

        public MapKeys(String motorFL, String motorFR, String motorBL, String motorBR, String imu) {
            this.motorFL = motorFL;
            this.motorFR = motorFR;
            this.motorBL = motorBL;
            this.motorBR = motorBR;
            this.imu = imu;
        }
    }

}
