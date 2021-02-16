package org.firstinspires.ftc.teamcode.hardware.groups;

import com.qualcomm.robotcore.hardware.DcMotor;

public class TandemMotors {
    public final DcMotor[] motors;
    public TandemMotors(DcMotor... motors) {
        this.motors = motors;
    }

    public void setPower(double power) {
        for (DcMotor m : motors) m.setPower(power);
    }
}
