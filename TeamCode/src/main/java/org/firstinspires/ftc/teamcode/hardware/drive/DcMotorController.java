package org.firstinspires.ftc.teamcode.hardware.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.Constants;

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

    public void setPower(double value) {
        double pow = Math.max(Math.min(value*hardware.getMotorBoost(),hardware.getMotorMax()),-hardware.getMotorMax());
        this.motor.setPower(Math.abs(pow)<hardware.getMotorMin()?0:pow);
    }
    public double getStepDisplacement() { return stepDisplacement; }
    public double getDist() { return currentDist; }
    public void adjustBaseDist(double amtInches) { distStart += amtInches; currentDist -= amtInches; }
    public double calculateDist() { return motor.getCurrentPosition()/ Constants.TICKS_PER_IN*(motor.getDirection()==DcMotorSimple.Direction.FORWARD?1:1) - distStart; }
}