package org.firstinspires.ftc.teamcode.hardware.drive;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;

import java.util.Locale;

public class PositionTracker {

    private static RobotPos pos = null;

    public final DriveController driveController;
    final BotHardware hardware;

    final Telemetry telemetry;

    public PositionTracker(BotHardware hardware, Telemetry telemetry) {
        this.hardware = hardware;
        this.driveController = new DriveController(DriveMotors.MotorClipMode.SCALE,hardware);
        this.telemetry = telemetry;
    }
    public PositionTracker(BotHardware hardware, Telemetry telemetry, DriveController driveController) {
        this.hardware = hardware;
        this.driveController = driveController;
        this.telemetry = telemetry;
    }

    public void init(double t) {
        driveController.initMotors(t);
        if (pos != null)
            pos = new RobotPos(pos.x, pos.y, pos.r, t);
        else
            pos = new RobotPos(t);
    }
    public void init(double t, RobotPos initialPos) {
        if (pos != null)
            pos = new RobotPos(initialPos.x, initialPos.y, initialPos.r, t);
        else
            pos = new RobotPos(t);
    }

    public void correctForVisionPos(RobotPos visionPos){
        pos = pos.correctPos(visionPos);
    }

    public void updatePosition(double t) {
        double dmFL = hardware.motors.fl.getStepDisplacement();
        double dmFR = hardware.motors.fr.getStepDisplacement();
        double dmBL = hardware.motors.bl.getStepDisplacement();
        double dmBR = hardware.motors.br.getStepDisplacement();
        double dY = dmFL + dmFR + dmBL + dmBR;  dY *= -1/4.0;
        double dX = dmBL + dmBR -(dmFL + dmFR); dX *= 1/4.0;
        double dR = dmFR + dmBR -(dmFL + dmBL); dR *= Constants.ROTPOW_TO_RAD/8.0;
        telemetry.addLine(String.format(Locale.ENGLISH,"DY: %.3f; DX: %.3f; DR: %.3f;",dY,dX,dR));
        pos = pos.integrateRelFwd(dX, dY, dR, t);
    }

    public void resetBasePos() { setBaseDist(0); }
    public void setBaseDist(double dist) {
        hardware.motors.fl.adjustBaseDist(hardware.motors.fl.calculateDist()+dist);
        hardware.motors.bl.adjustBaseDist(hardware.motors.bl.calculateDist()+dist);
        hardware.motors.fr.adjustBaseDist(hardware.motors.fr.calculateDist()+dist);
        hardware.motors.br.adjustBaseDist(hardware.motors.br.calculateDist()+dist);
    }
    public void setPos(RobotPos pos) { this.pos = new RobotPos(pos); }
    public RobotPos getPos() { return pos; }
}
