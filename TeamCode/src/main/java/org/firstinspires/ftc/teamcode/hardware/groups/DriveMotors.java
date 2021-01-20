package org.firstinspires.ftc.teamcode.hardware.groups;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.DcMotorController;

public class DriveMotors {
    public final DcMotorController fl;
    public final DcMotorController bl;
    public final DcMotorController fr;
    public final DcMotorController br;

    public DriveMotors(DcMotor motor_fl, DcMotor motor_fr, DcMotor motor_bl, DcMotor motor_br, BotHardware hardware) {
        fl = new DcMotorController(motor_fl,hardware);
        fr = new DcMotorController(motor_fr,hardware);
        bl = new DcMotorController(motor_bl,hardware);
        br = new DcMotorController(motor_br,hardware);
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
