package org.firstinspires.ftc.teamcode.vision;

public class CoordinateZone extends Coordinate{
    protected double x1,y1,x2,y2;

    //In this case, a coordinate zone's coordinate values will be equal to the center of the coordinate zone

    public CoordinateZone(double x1, double x2, double y1, double y2, String name){
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.x = (x1+x2)/2;
        this.y = (y1+y2)/2;
    }

    public CoordinateZone(Coordinate bl, Coordinate tr){
        this.x1=bl.getX();
        this.y1=bl.getY();
        this.x2=tr.getX();
        this.y2=tr.getY();
        this.x = (x1+x2)/2;
        this.y = (y1+y2)/2;
    }

    public double getLowX(){
        if(x1>x2) return x2;
        else return x1;
    }

    public double getHighX(){
        if(x1>x2) return x1;
        else return x2;
    }

    public double getLowY(){
        if(y1>y2) return y2;
        else return y1;
    }

    public double getHighY() {
        if (y1 > y2) return y1;
        else return y2;
    }
}
