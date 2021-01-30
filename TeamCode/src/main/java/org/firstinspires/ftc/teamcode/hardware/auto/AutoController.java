package org.firstinspires.ftc.teamcode.hardware.auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;

import java.util.Locale;

public class AutoController {
    RobotPos target = null;
    RobotPos pos = null;
    RobotPos vel = null;

    public final DriveController driveController;
    final BotHardware hardware;

    final Telemetry telemetry;

    public AutoController(BotHardware hardware, Telemetry telemetry) {
        this.hardware = hardware;
        this.driveController = new DriveController(DriveMotors.MotorClipMode.SCALE,hardware);
        this.telemetry = telemetry;
    }

    public void init(double t) {
        driveController.initMotors(t);
        pos = new RobotPos(t);
    }

    public void setTarget(RobotPos target) { this.target = target; }

    public void update(double t) {
        if (target == null) {
            driveController.setMotors_YXR(0,0,0);
            driveController.updateMotors(t);
            return;
        }
        updatePosition(t);

        RobotPos diff = pos.getDifferenceTo(target);
        double dX = diff.x, dY = diff.y, dR = diff.r;
        double vX = Math.signum(dX)*Math.max(0,Math.min(6,Math.abs(dX)))/6;
        double vY = -Math.signum(dY)*Math.max(0,Math.min(6,Math.abs(dY)))/6;
        vX = Math.cos(pos.r)*vX-Math.sin(pos.r)*vY;
        vY = Math.cos(pos.r)*vY+Math.sin(pos.r)*vX;
        final double rotInvPow = 2;
        double vR = Math.signum(dR)*Math.max(0,Math.min(rotInvPow,Math.abs(dR)))/rotInvPow;

        telemetry.addLine(String.format(Locale.ENGLISH,"%.2f %.2f %.2f",vX,vY,vR));

        vel = new RobotPos(vX,vY,vR);
        driveController.setMotors_YXR(vY,vX,vR);
    }

    public void correctForVisionPos(RobotPos visionPos){
        pos = pos.correctPos(visionPos);
    }

    private void updatePosition(double t) {
        driveController.updateMotors(t);
        double dmFL = hardware.motors.fl.getStepDisplacement();
        double dmFR = hardware.motors.fr.getStepDisplacement();
        double dmBL = hardware.motors.bl.getStepDisplacement();
        double dmBR = hardware.motors.br.getStepDisplacement();
        double dY = dmFL + dmFR + dmBL + dmBR;  dY *= -1/4.0;
        double dX = dmBL + dmBR -(dmFL + dmFR); dX *= 1/4.0;
        double dR = dmFR + dmBR -(dmFL + dmBL); dR *= Constants.ROTPOW_TO_RAD/4.0;
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

    public boolean withinThreshold(double distInches, double rotThresh, double speedThresh) {
        if (target == null || pos == null || vel == null) return false;
        RobotPos diff = target.getDifferenceTo(pos);
        boolean close = diff.x*diff.x+diff.y*diff.y < distInches*distInches;
        close &= Math.abs(diff.r) < rotThresh;
        close &= vel.x*vel.x+vel.y*vel.y < speedThresh*speedThresh;
        close &= Math.abs(vel.r) < speedThresh;
        return close;
    }
}
