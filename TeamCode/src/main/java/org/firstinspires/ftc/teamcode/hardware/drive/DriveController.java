package org.firstinspires.ftc.teamcode.hardware.drive;

import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;

public class DriveController {
    private final DriveMotors.MotorClipMode clipMode;
    private final BotHardware hardware;

    public DriveController(DriveMotors.MotorClipMode clipMode, BotHardware hardware) {
        this.clipMode = clipMode;
        this.hardware = hardware;
    }

    protected void checkMotorsPresent() {
        if (!hardware.motors.checkMotorsPresent())
            throw new IllegalStateException("DriveController: motor(s) were unset!");
    }

    public void setMotors_YXR(double moveY, double moveX, double rotate){
        checkMotorsPresent();

        double powerFL = moveY - moveX - rotate;
        double powerFR = moveY + moveX + rotate;
        double powerBL = moveY + moveX - rotate;
        double powerBR = moveY - moveX + rotate;

        hardware.motors.setPowerClamped(powerFL,powerFR,powerBL,powerBR,clipMode);
    }
    public void setMotors_LRX(double moveL, double moveR, double moveX){
        checkMotorsPresent();

        double powerFL = moveL + moveX;
        double powerBL = moveL - moveX;
        double powerFR = moveR + moveX;
        double powerBR = moveR - moveX;

        hardware.motors.setPowerClamped(powerFL,powerFR,powerBL,powerBR,clipMode);
    }
    public void initMotors(double t) {
        hardware.motors.fl.init(t);
        hardware.motors.fr.init(t);
        hardware.motors.bl.init(t);
        hardware.motors.br.init(t);
    }
    public void updateMotors(double t) {
        hardware.motors.fl.update(t);
        hardware.motors.fr.update(t);
        hardware.motors.bl.update(t);
        hardware.motors.br.update(t);
    }

}
