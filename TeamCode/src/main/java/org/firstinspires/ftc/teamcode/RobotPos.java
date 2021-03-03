package org.firstinspires.ftc.teamcode;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class RobotPos {
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
        double dx = dx_*c-dy_*s;
        double dy = dy_*c+dx_*s;
        return new RobotPos(dx+x,dy+y,dr+r,newAqTime);
    }
    public RobotPos getDifferenceTo(RobotPos other) {
        return new RobotPos(other.x-x,other.y-y,other.r-r);
    }
    public RobotPos correctPos(RobotPos target) {
        double k = Constants.ROBOTPOS_BLEND_FAC;
        double i = 1-k;
        double dr = (target.r-this.r)/(Math.PI*2);
        dr -= Math.floor(dr); dr -= 0.5; dr *= Math.PI*2;
        return new RobotPos(i*this.x+k*target.x,i*this.y+k*target.y,this.r+k*dr,this.aqTime);
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"[RobotPos{x: %.3f,y: %.3f,r: %.3f}]",x,y,r);
    }
}
