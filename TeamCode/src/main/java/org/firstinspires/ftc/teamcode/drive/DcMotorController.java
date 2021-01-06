package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;

public class DcMotorController {
    double lastT = 0;
    double distStart = 0;
    double lastDist = 0;
    double currentDist = 0;
    public final DcMotor motor;
    //private float maxPower;
    //private double targetDist;
    //private int startTick;

    public DcMotorController(DcMotor motor) {
        this.motor = motor;
    }


    public void init(double time) {
        lastT = time;
        /*maxPower = 0;
        targetDist = 0;
        startTick = motor.getCurrentPosition();*/
    }
    public void update(double time) {
        double dt = time-lastT; lastT = time;

        lastDist = currentDist; currentDist = getDist();
        /*

        double value = maxPower;
        int target = (int) (targetDist/BotHardwareInfo.TICKS_PER_IN) - startTick;

        int ticksOff = target - motor.getCurrentPosition();

        value *= Math.max(-1,Math.min(1,ticksOff/BotHardwareInfo.MOTORRAMP_TICKSPEEDFAC));

        double maxAcc = dt*BotHardwareInfo.MOTORRAMP_MAXACC;
        value = Math.min(maxAcc,Math.max(-maxAcc,value-motor.getPower()));

        setPower(motor.getPower()+value);

        */
    }
    public void adjustBaseDist(double amtInches) {
        distStart += amtInches;
    }
    public double getDist() {
        return motor.getCurrentPosition()/BotHardwareInfo.TICKS_PER_IN - distStart;
    }

    public void setPower(double value) {
        this.motor.setPower(value);
    }
    /*
    public void resetStartTick() { startTick = motor.getCurrentPosition(); }
    public void setTargetDist(float inches) { targetDist = inches; }
    public void setMaxPower(float maxPower) { this.maxPower = maxPower; }

    public boolean getDone() {
        int target = (int) (targetDist/BotHardwareInfo.TICKS_PER_IN) - startTick;
        int ticksOff = target - motor.getCurrentPosition();
        return Math.abs(ticksOff) < BotHardwareInfo.MOTORRAMP_MINTHRESH && Math.abs(motor.getPower()) < 0.05;
    }*/

    public double getLastDist() { return lastDist; }
    public double getCurrentDist() { return currentDist; }
}
