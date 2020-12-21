package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.DcMotorRampable;

public class DriveMotors {
    public final DcMotorRampable fl;
    public final DcMotorRampable bl;
    public final DcMotorRampable fr;
    public final DcMotorRampable br;

    public DriveMotors(DcMotor motor_fl, DcMotor motor_fr, DcMotor motor_bl, DcMotor motor_br) {
        fl = new DcMotorRampable(motor_fl);
        fr = new DcMotorRampable(motor_fr);
        bl = new DcMotorRampable(motor_bl);
        br = new DcMotorRampable(motor_br);
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
