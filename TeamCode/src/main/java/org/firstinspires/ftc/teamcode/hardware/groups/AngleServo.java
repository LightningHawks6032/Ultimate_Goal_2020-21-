package org.firstinspires.ftc.teamcode.hardware.groups;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class AngleServo {
    public Servo servo;
    public TandemServos tandemServos;
    private double t;
    private double v;

    private final double s;
    private final double am;
    private final double aM;
    public AngleServo(Servo servo, double s, double am, double aM) {
        this.servo = servo;
        this.servo.scaleRange(Constants.OUTTAKE_ANGLE_MIN,Constants.OUTTAKE_ANGLE_MAX);
        this.s = s;
        this.aM = aM;
        this.am = am;
    }
    public AngleServo(TandemServos servo, double s, double am, double aM) {
        this.tandemServos = servo;
        this.tandemServos.scaleRange(Constants.OUTTAKE_ANGLE_MIN,Constants.OUTTAKE_ANGLE_MAX);
        this.s = s;
        this.aM = aM;
        this.am = am;
    }
    public void update(double t, double dv) {
        double dt = t-this.t; this.t = t;
        v += dv*dt*s;
        v = Math.max(Math.min(aM,v),am);
        if (servo != null) servo.setPosition(v);
        if (tandemServos != null) tandemServos.setTarget(v);
    }
}
