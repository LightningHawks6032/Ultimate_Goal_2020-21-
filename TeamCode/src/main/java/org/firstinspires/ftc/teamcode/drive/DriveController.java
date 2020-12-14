package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveController {
    private DcMotor motorFL = null;
    private DcMotor motorBL = null;
    private DcMotor motorFR = null;
    private DcMotor motorBR = null;
    private MotorClipMode clipMode;


    public DriveController(MotorClipMode clipMode, HardwareMap hardwareMap) {
        this.clipMode = clipMode;

        motorFL = hardwareMap.get(DcMotor.class, "fl");
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL = hardwareMap.get(DcMotor.class, "bl");
        motorBL.setDirection(DcMotor.Direction.FORWARD);

        motorFR = hardwareMap.get(DcMotor.class, "fr");
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorBR = hardwareMap.get(DcMotor.class, "br");
        motorBR.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setMotorBL(DcMotor motorBL) { this.motorBL = motorBL; }
    public void setMotorBR(DcMotor motorBR) { this.motorBR = motorBR; }
    public void setMotorFL(DcMotor motorFL) { this.motorFL = motorFL; }
    public void setMotorFR(DcMotor motorFR) { this.motorFR = motorFR; }

    protected void checkMotorsPresent() {
        if (motorBL == null || motorBR == null || motorFL == null || motorFR == null)
            throw new IllegalStateException("DriveController: motor(s) were unset!");
    }

    public void updateMotors_YXR(double moveY, double moveX, double rotate){
        checkMotorsPresent();

        double powerFL = moveY + moveX - rotate;
        double powerFR = moveY + moveX + rotate;
        double powerBL = moveY - moveX - rotate;
        double powerBR = moveY - moveX + rotate;

        setMotorsClamped(powerFL,powerFR,powerBL,powerBR);
    }
    public void updateMotors_LRX(double moveL, double moveR, double moveX){
        checkMotorsPresent();

        double powerFL = moveL + moveX;
        double powerBL = moveL - moveX;
        double powerFR = moveR + moveX;
        double powerBR = moveR - moveX;

        setMotorsClamped(powerFL,powerFR,powerBL,powerBR);
    }

    private void setMotorsClamped(double fl, double fr, double bl, double br) {

        double powerFL = fl;
        double powerFR = fr;
        double powerBL = bl;
        double powerBR = br;

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
