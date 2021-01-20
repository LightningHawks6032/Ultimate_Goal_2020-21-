package org.firstinspires.ftc.teamcode.hardware.vision;

public class VisionPos {
    protected double x,y;
    protected String name = "";
    protected static final float mmPerInch = 25.4f;


    //Note: x and y are measured in inches
    public VisionPos(double x, double y, String name){
        this.x=x;
        this.y=y;
        this.name=name;
    }

    public VisionPos(){
        this.x=0;
        this.y=0;
        this.name="";
    }

    //The stuff stuff

    public String getName(){
        return name;
    }

    public double getX(){
        return x;
    }

    public double getXMilli(){
        return x*mmPerInch;
    }

    public double getY(){
        return y;
    }

    public double getYMilli(){
        return y*mmPerInch;
    }


}
