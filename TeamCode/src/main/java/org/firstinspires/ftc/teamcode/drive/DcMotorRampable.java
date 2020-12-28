package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;

import java.util.ArrayList;
import java.util.List;

public class DcMotorRampable {
    double lastT = 0;

    public final DcMotor motor;
    public double value;

    private final List<Point> points = new ArrayList<>();

    public DcMotorRampable(DcMotor motor) {
        this.motor = motor;
    }

    public void init(double time) {
        lastT = time;
    }
    public void update(double time) {
        double dt = time-lastT; lastT = time;

        double value = points.get(0).value;
        int target = points.get(0).target;

        int ticksOff = target - motor.getCurrentPosition();

        System.out.println(ticksOff);

        value *= Math.max(-1,Math.min(1,ticksOff/BotHardwareInfo.MOTORRAMP_TICKSPEEDFAC));
        System.out.println(value);

        if (Math.abs(ticksOff) < BotHardwareInfo.MOTORRAMP_MINTHRESH && Math.abs(motor.getPower()+value) < 0.01)
            points.remove(0);

        double maxAcc = dt*BotHardwareInfo.MOTORRAMP_MAXACC;
        value = Math.min(maxAcc,Math.max(-maxAcc,value-motor.getPower()));

        System.out.println(motor.getPower()+value);
        System.out.println("______________");

        motor.setPower(motor.getPower()+value);
    }

    public void setPower(double value) {
        this.value = value;
    }

    public List<Point> getPoints() { return this.points; }

    public void addPoint(Point p) {
        points.add(p);
    }

    public static class Point {
        public final double value;
        public final int target;

        public Point(double value, int target) {
            this.value = value;
            this.target = target;
        }
    }
}
