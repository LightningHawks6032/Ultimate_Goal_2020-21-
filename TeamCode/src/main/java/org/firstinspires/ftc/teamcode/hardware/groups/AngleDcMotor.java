package org.firstinspires.ftc.teamcode.hardware.groups;

import com.qualcomm.robotcore.hardware.DcMotor;

public class AngleDcMotor {
    final int zeroPos;
    final DcMotor motor;
    public AngleDcMotor(DcMotor motor, int zeroPos) {
        this.zeroPos = zeroPos;
        this.motor = motor;
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPos(int pos) { motor.setTargetPosition(pos-zeroPos); }
    public int getPos() { return motor.getCurrentPosition()+zeroPos; }
    public void setPower(double power) { motor.setPower(power); }
}
