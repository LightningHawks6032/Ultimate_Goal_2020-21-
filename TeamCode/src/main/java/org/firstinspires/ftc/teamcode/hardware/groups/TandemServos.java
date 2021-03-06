package org.firstinspires.ftc.teamcode.hardware.groups;

import com.qualcomm.robotcore.hardware.Servo;

public class TandemServos {
    public final Servo[] servos;
    public TandemServos(Servo... servos) {
        this.servos = servos;
    }

    public void setTarget(double power) {
        for (Servo servo : servos) servo.setPosition(power);
    }
    public void scaleRange(double min, double max) {
        for (Servo servo : servos) servo.scaleRange(min,max);
    }
}
