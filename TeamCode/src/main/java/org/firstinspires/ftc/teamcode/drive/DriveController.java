package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveController {
    private DriveMotors.MotorClipMode clipMode;
    private DriveMotors motors;

    public DriveController(DriveMotors.MotorClipMode clipMode, HardwareMap hardwareMap) {
        this.clipMode = clipMode;

    }

    protected void checkMotorsPresent() {
        if (!motors.checkMotorsPresent())
            throw new IllegalStateException("DriveController: motor(s) were unset!");
    }

    public void updateMotors_YXR(double moveY, double moveX, double rotate){
        checkMotorsPresent();

        double powerFL = moveY + moveX - rotate;
        double powerFR = moveY + moveX + rotate;
        double powerBL = moveY - moveX - rotate;
        double powerBR = moveY - moveX + rotate;

        motors.setPowerClamped(powerFL,powerFR,powerBL,powerBR,clipMode);
    }
    public void updateMotors_LRX(double moveL, double moveR, double moveX){
        checkMotorsPresent();

        double powerFL = moveL + moveX;
        double powerBL = moveL - moveX;
        double powerFR = moveR + moveX;
        double powerBR = moveR - moveX;

        motors.setPowerClamped(powerFL,powerFR,powerBL,powerBR,clipMode);
    }

}
