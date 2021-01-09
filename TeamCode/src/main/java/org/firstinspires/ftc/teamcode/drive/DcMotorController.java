package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;

public class DcMotorController {
    private double lastT = 0;
    private double distStart = 0;
    private double stepDisplacement = 0;
    private double currentDist = 0;
    public final DcMotor motor;
    public final BotHardware hardware;

    public DcMotorController(DcMotor motor, BotHardware hardware) {
        this.motor = motor;
        this.hardware = hardware;
    }

    public void init(double time) {
        lastT = time;
    }
    public void update(double time) {
        lastT = time;

        double lastDist = currentDist;
        currentDist = calculateDist();
        stepDisplacement = currentDist - lastDist;
    }

    public void setPower(double value) { this.motor.setPower(Math.max(Math.min(value*hardware.getMotorBoost(),hardware.getMotorMax()),-hardware.getMotorMax())); }
    public double getStepDisplacement() { return stepDisplacement; }
    public double getDist() { return currentDist; }
    public void adjustBaseDist(double amtInches) { distStart += amtInches; }
    public double calculateDist() { return motor.getCurrentPosition()/BotHardwareInfo.TICKS_PER_IN - distStart; }
}