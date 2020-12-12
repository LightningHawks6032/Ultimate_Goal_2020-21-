package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveController {
    private DcMotor motorFL = null;
    private DcMotor motorBL = null;
    private DcMotor motorFR = null;
    private DcMotor motorBR = null;
    private MotorClipMode clipMode;


    public DriveController(MotorClipMode clipMode) {
        this.clipMode = clipMode;
    }
    public DriveController() {
        this(null);
    }

    public void setMotorBL(DcMotor motorBL) { this.motorBL = motorBL; }
    public void setMotorBR(DcMotor motorBR) { this.motorBR = motorBR; }
    public void setMotorFL(DcMotor motorFL) { this.motorFL = motorFL; }
    public void setMotorFR(DcMotor motorFR) { this.motorFR = motorFR; }

    public void setClipMode(MotorClipMode clipMode) { this.clipMode = clipMode; }

    public void updateMotors(double moveY, double moveX, double rotate){
        if (motorBL == null || motorBR == null || motorFL == null || motorFR == null)
            throw new IllegalStateException("DriveController: motor(s) were unset!");

        double powerFL = moveY + moveX - rotate;
        double powerFR = moveY + moveX + rotate;
        double powerBL = moveY - moveX - rotate;
        double powerBR = moveY - moveX + rotate;

        double maxPower = Math.max(Math.max(powerBL,powerBR),Math.max(powerFL,powerFR));

        if (maxPower > 1) switch (clipMode) {
            case CLAMP:
                powerFL = Math.min(1,powerFL);
                powerBL = Math.min(1,powerBL);
                powerFR = Math.min(1,powerFR);
                powerBR = Math.min(1,powerBR);
                break;
            case SCALE:
                powerFL /= maxPower;
                powerBL /= maxPower;
                powerFR /= maxPower;
                powerBR /= maxPower;
                break;
            default:
             break;
        }

        motorFL.setPower(powerFL);
        motorFR.setPower(powerFR);
        motorBL.setPower(powerBL);
        motorBR.setPower(powerBR);
    }

    public enum MotorClipMode {
        UNRESTRAINED, SCALE, CLAMP
    }

}
