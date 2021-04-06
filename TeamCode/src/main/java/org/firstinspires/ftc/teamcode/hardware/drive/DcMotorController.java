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
    private double vTarget = 0;
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
        double dt = time - lastT; lastT = time;

        double lastDist = currentDist;
        currentDist = calculateDist();
        stepDisplacement = currentDist - lastDist;

        double pow = motor.getPower();
        motor.setPower(pow+(vTarget-pow)*dt*Constants.MAX_MOTORACC*hardware.getAccBoost());
        vTarget = motor.getPower();
    }

    public void setPower(double value) {
        double pow = value * hardware.getMotorBoost();
        pow = Math.signum(pow)*Math.max(Math.min(Math.abs(value),hardware.getMotorMax()),hardware.getMotorMin());
        if (pow != 0) pow += Math.signum(pow)*hardware.getMotorPowOffset();
        vTarget = pow;
    }
    public double getStepDisplacement() { return stepDisplacement; }
    public double getDist() { return currentDist; }
    public void adjustBaseDist(double amtInches) { distStart += amtInches; currentDist -= amtInches; }
    public double calculateDist() { return motor.getCurrentPosition()/ Constants.TICKS_PER_IN*(motor.getDirection()==DcMotorSimple.Direction.FORWARD?1:1) - distStart; }
}