package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.teamcode.hardware.IMUAccelerationIntegrator;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;

public class AutoController {

    RobotPos target = null;
    RobotPos pos = null;
    IMUAccelerationIntegrator accInt;

    final BotHardware hardware;

    public AutoController(BotHardware hardware) {
        this.hardware = hardware;
    }

    public void init(double t) {
        hardware.motors.fl.init(t);
        hardware.motors.fr.init(t);
        hardware.motors.bl.init(t);
        hardware.motors.br.init(t);
        pos = new RobotPos(t);
    }

    public void setTarget(RobotPos target) { this.target = target; }

    public void update(double t) {
        if (target == null) {
            hardware.motors.fl.update(t);
            hardware.motors.fl.setPower(0);
            hardware.motors.bl.update(t);
            hardware.motors.bl.setPower(0);
            hardware.motors.fr.update(t);
            hardware.motors.fr.setPower(0);
            hardware.motors.br.update(t);
            hardware.motors.br.setPower(0);
            return;
        }



        hardware.motors.fl.update(t);
        hardware.motors.fr.update(t);
        hardware.motors.bl.update(t);
        hardware.motors.br.update(t);

        /*

        double powerFL = moveY + moveX - rotate;
        double powerFR = moveY + moveX + rotate;
        double powerBL = moveY - moveX - rotate;
        double powerBR = moveY - moveX + rotate;
         */
        double dmFL = hardware.motors.fl.currentDist - hardware.motors.fl.lastDist;
        double dmFR = hardware.motors.fr.currentDist - hardware.motors.fr.lastDist;
        double dmBL = hardware.motors.bl.currentDist - hardware.motors.bl.lastDist;
        double dmBR = hardware.motors.br.currentDist - hardware.motors.br.lastDist;


        double dY = dmFL+dmFR+dmBL+dmBR;
        double dX = dmFL+dmFR-(dmBL+dmBR);
        double dR = dmFR+dmBR-(dmFL+dmBL);

        target = pos.integrateRelFwd(dY,dX,dR,t);
    }

    public void resetBasePos() {
        setBaseDist(0);
    }
    public void setBaseDist(double dist) {
        hardware.motors.fl.adjustBaseDist(hardware.motors.fl.getDist()+dist);
        hardware.motors.bl.adjustBaseDist(hardware.motors.bl.getDist()+dist);
        hardware.motors.fr.adjustBaseDist(hardware.motors.fr.getDist()+dist);
        hardware.motors.br.adjustBaseDist(hardware.motors.br.getDist()+dist);
    }

    // INCHES
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

        public double getAqTime() { return aqTime; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getR() { return r; }

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
    }
}
