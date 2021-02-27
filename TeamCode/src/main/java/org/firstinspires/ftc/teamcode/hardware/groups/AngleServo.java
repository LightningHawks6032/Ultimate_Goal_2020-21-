package org.firstinspires.ftc.teamcode.hardware.groups;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class AngleServo {
    public Servo servo;
    private double t;
    private double v;
    public AngleServo(Servo servo) {
        this.servo = servo;
        this.servo.scaleRange(Constants.OUTTAKE_ANGLE_MIN,Constants.OUTTAKE_ANGLE_MAX);
    }
    public void update(double t, double dv) {
        double dt = t-this.t; this.t = t;
        v += dv*dt*Constants.OUTTAKE_ANGLE_SPEED;
        v = Math.max(Math.min(Constants.OUTTAKE_ANGLE_MAX,v),Constants.OUTTAKE_ANGLE_MIN);
        servo.setPosition(v);
    }
}
