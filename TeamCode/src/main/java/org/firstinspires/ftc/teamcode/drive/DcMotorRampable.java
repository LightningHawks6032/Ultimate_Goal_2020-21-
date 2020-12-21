package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DcMotorRampable {
    public final DcMotor motor;
    public float value;

    private final List<Point> points = new ArrayList<>();

    public DcMotorRampable(DcMotor motor) {
        this.motor = motor;
    }

    public void update(double time) {
        while(time > points.get(0).time) {
            value = points.get(0).value;
            points.remove(0);
        }

    }

    private class Point {
        public final double time;
        public final float value;

        private Point(double time, float value) {
            this.time = time;
            this.value = value;
        }
    }
}
