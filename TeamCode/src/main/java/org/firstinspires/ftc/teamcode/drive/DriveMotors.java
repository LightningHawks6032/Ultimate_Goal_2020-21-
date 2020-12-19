package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.HardwareMapKeys;

public class DriveMotors {
    public final DcMotor fl;
    public final DcMotor bl;
    public final DcMotor fr;
    public final DcMotor br;

    public DriveMotors(HardwareMap hardwareMap) {
        fl = hardwareMap.get(DcMotor.class, HardwareMapKeys.MOTOR_FL);
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl = hardwareMap.get(DcMotor.class, HardwareMapKeys.MOTOR_BL);
        bl.setDirection(DcMotor.Direction.FORWARD);

        fr = hardwareMap.get(DcMotor.class, HardwareMapKeys.MOTOR_FR);
        fr.setDirection(DcMotor.Direction.REVERSE);
        br = hardwareMap.get(DcMotor.class, HardwareMapKeys.MOTOR_BR);
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
