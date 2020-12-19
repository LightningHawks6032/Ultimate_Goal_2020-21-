package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.HardwareMaps;

public class DriveMotors {
    public DcMotor fl = null;
    public DcMotor bl = null;
    public DcMotor fr = null;
    public DcMotor br = null;

    public DriveMotors(HardwareMap hardwareMap) {
        fl = hardwareMap.get(DcMotor.class, HardwareMaps.DRIVE.motorFL);
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl = hardwareMap.get(DcMotor.class, HardwareMaps.DRIVE.motorBL);
        bl.setDirection(DcMotor.Direction.FORWARD);

        fr = hardwareMap.get(DcMotor.class, HardwareMaps.DRIVE.motorFR);
        fr.setDirection(DcMotor.Direction.REVERSE);
        br = hardwareMap.get(DcMotor.class, HardwareMaps.DRIVE.motorBR);
        br.setDirection(DcMotor.Direction.REVERSE);
    }

    public boolean checkMotorsPresent() {
        return bl != null && br != null && fl != null && fr != null;
    }

    public void setPowerClamped(double fl, double fr, double bl, double br, MotorClipMode clipMode) {
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
            case UNRESTRAINED:
            default:
                break;
        }

        this.fl.setPower(powerFL);
        this.fr.setPower(powerFR);
        this.bl.setPower(powerBL);
        this.br.setPower(powerBR);
    }

    public enum MotorClipMode {
        UNRESTRAINED, SCALE, CLAMP
    }
}
