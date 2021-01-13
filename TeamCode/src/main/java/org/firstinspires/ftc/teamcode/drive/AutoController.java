package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.teamcode.hardware.BotHardwareInfo;
import org.firstinspires.ftc.teamcode.hardware.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;

public class AutoController {
    RobotPos target = null;
    RobotPos pos = null;

    final DriveController driveController;
    final BotHardware hardware;

    public AutoController(BotHardware hardware) {
        this.hardware = hardware;
        this.driveController = new DriveController(DriveMotors.MotorClipMode.SCALE,hardware);
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
        driveController.updateMotors(t);

        double dmFL = hardware.motors.fl.getStepDisplacement();
        double dmFR = hardware.motors.fr.getStepDisplacement();
        double dmBL = hardware.motors.bl.getStepDisplacement();
        double dmBR = hardware.motors.br.getStepDisplacement();
        double dY = dmFL + dmFR + dmBL + dmBR;  dY *= 1/4.0;
        double dX = dmFL + dmFR -(dmBL + dmBR); dX *= 1/4.0;
        double dR = dmFR + dmBR -(dmFL + dmBL); dR *= BotHardwareInfo.ROTPOW_TO_RAD/2.0;
        pos = pos.integrateRelFwd(dY, dX, dR, t);

        RobotPos diff = pos.getDifferenceTo(target);
        dX = diff.x; dY = diff.y; dR = diff.r;
        dX = Math.signum(dX)*Math.max(0,Math.min(12,Math.abs(dX)-0.5))/12;
        dY = Math.signum(dY)*Math.max(0,Math.min(12,Math.abs(dY)-0.5))/12;
        dR = Math.signum(dR)*Math.max(0,Math.min(1,Math.abs(dR)-0.1))/1;
        dX = Math.cos(dR)*dX+Math.sin(dR)*dY;
        dY = Math.cos(dR)*dY-Math.sin(dR)*dX;
        driveController.setMotors_YXR(dY,dX,dR);
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
            double dx = dx_*Math.cos(r)+dy_*Math.sin(r);
            double dy = dy_*Math.cos(r)-dx_*Math.sin(r);
            double s = Math.sin(dr);
            double c = Math.cos(dr);
            double xe = dx*s-dy*c;
            double ye = dy*s+dx*c;
            return new RobotPos(xe,ye,dr+r,newAqTime);
        }
        public RobotPos integrateVelRelFwd(double vx, double vy, double vr, double dt) {
            return this.integrateRelFwd(x*dt,vy*dt,  vr*dt,aqTime+dt);
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
