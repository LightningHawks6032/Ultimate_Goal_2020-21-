package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DcMotorRampable {
    public final DcMotor motor;
    public double value;

    private final List<Point> points = new ArrayList<>();

    public DcMotorRampable(DcMotor motor) {
        this.motor = motor;
    }

    public void update(double time) {
        while(time > points.get(0).time) {
            value = points.get(0).value;
            points.remove(0);
        }


        motor.setPower(value);
    }

    public void setPower(double value) {
        this.value = value;
    }



    private static class Point {
        public final boolean useEncoderTickTarget;
        public final double time;
        public final double value;
        public final int target;

        private Point(double time, double value) {
            this.time = time;
            this.value = value;
            this.useEncoderTickTarget = false;
            this.target = 0;
        }
        private Point(double time, int target) {
            this.time = time;
            this.value = 0f;
            this.useEncoderTickTarget = true;
            this.target = target;
        }
    }
}
