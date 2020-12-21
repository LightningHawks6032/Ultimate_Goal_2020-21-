package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.hardware.IMUAccelerationIntegrator;
import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;

import java.util.ArrayList;
import java.util.List;

public class AutoController {

    final List<Position> waypoints;
    IMUAccelerationIntegrator accInt;

    final BotHardware hardware;

    public AutoController(BotHardware hardware) {
        waypoints = new ArrayList<>();
        this.hardware = hardware;
    }

    public void init() {
    }

    public void addPoint(Position pos) {
        waypoints.add(pos);
    }

    public String update(boolean e) {
        hardware.motors.fr.setTargetPosition(e ? (int) (BotHardwareInfo.TICKS_PER_REV) : 0);
        hardware.motors.fr.setPower(1f-(hardware.motors.fr.getCurrentPosition()-hardware.motors.fr.getTargetPosition())/100f);
        return ";"+hardware.motors.fr.getCurrentPosition()+";"+hardware.motors.fr.getTargetPosition();
    }
}
