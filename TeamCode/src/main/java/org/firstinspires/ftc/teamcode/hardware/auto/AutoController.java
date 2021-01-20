package org.firstinspires.ftc.teamcode.hardware.auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;

public class AutoController {
    RobotPos target = null;
    RobotPos pos = null;

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
        double vY = Math.signum(dY)*Math.max(0,Math.min(6,Math.abs(dY)))/6;
        vX = Math.cos(pos.r)*vX+Math.sin(pos.r)*vY;
        vY = Math.cos(pos.r)*vY-Math.sin(pos.r)*vX;
        double vR = Math.signum(dR)*Math.max(0,Math.min(0.5,Math.abs(dR)))/0.5;

        telemetry.addLine(String.format("%.2f %.2f %.2f",vX,vY,vR));

        driveController.setMotors_YXR(dY,0*dX,dR);
    }

    private void updatePosition(double t) {
        driveController.updateMotors(t);
        double dmFL = hardware.motors.fl.getStepDisplacement();
        double dmFR = hardware.motors.fr.getStepDisplacement();
        double dmBL = hardware.motors.bl.getStepDisplacement();
        double dmBR = hardware.motors.br.getStepDisplacement();
        double dY = dmFL + dmFR + dmBL + dmBR;  dY *= 1/4.0;
        double dX = dmBL + dmFR -(dmFL + dmBR); dX *= 1/4.0;
        double dR = dmFR + dmBR -(dmFL + dmBL); dR *= Constants.ROTPOW_TO_RAD/4.0;
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

    public static class RobotPos {
        public final double x;
        public final double y;
        public final double r;

        public final double aqTime;

        public RobotPos(double x, double y, double r, double aqTime) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.aqTime = aqTime;
        }
        public RobotPos(double aqTime) { this(0,0,0,aqTime); }
        public RobotPos(double x, double y, double r) { this(x,y,r,0); }
        public RobotPos(RobotPos pos) { this(pos.x,pos.y,pos.r,pos.aqTime); }

        public RobotPos integrateRelFwd(double dx_, double dy_, double dr, double newAqTime) {
            double s = Math.sin(r+dr);
            double c = Math.cos(r+dr);
            double dx = dx_*c+dy_*s;
            double dy = dy_*c-dx_*s;
            return new RobotPos(dx+x,dy+y,dr+r,newAqTime);
        }
        public RobotPos integrateVelRelFwd(double vx, double vy, double vr, double dt) {
            return this.integrateRelFwd(vx*dt,vy*dt,  vr*dt,aqTime+dt);
        }
        public RobotPos getDifferenceTo(RobotPos other) {
            return new RobotPos(other.x-x,other.y-y,other.r-r);
        }

        @Override
        public String toString() {
            return "[RobotPos{x:"+x+",y:"+y+",r:"+r+"}]";
        }
    }
}
