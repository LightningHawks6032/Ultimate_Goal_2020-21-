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

    public void init(double t) {
        hardware.motors.fr.init(t);
    }

    public void addPoint(Position pos) {
        waypoints.add(pos);
    }

    public void update(double t) {
        hardware.motors.fr.update(t);

    }
}
