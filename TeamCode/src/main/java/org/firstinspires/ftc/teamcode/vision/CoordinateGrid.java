package org.firstinspires.ftc.teamcode.vision;

public class CoordinateGrid {
    private final int FHEIGHT = 144;
    private final int FWIDTH = 72;
    private final int SQUARE = 24;

    public Coordinate leftGoal, rightGoal, initstack;
    //A = 0, B = 1, C = 4 in a stack
    public CoordinateZone targetA, targetB, targetC, launchLine;

    //The grid goes from x = -24 to x = 72 and y = -72 to y = 72
    //Remember to flip x and y and take the inverse of the x
    public CoordinateGrid (){
        leftGoal = new Coordinate(24,-48, "Left WobbleGoal");
        rightGoal = new Coordinate(48, -48, "Right WobbleGoal");
        initstack = new Coordinate(36, -36, "Stack");

        targetA = new CoordinateZone(48, 72, 0, 24, "Zone A");
        targetB = new CoordinateZone(24, 48, 24, 48, "Zone B");
        targetC = new CoordinateZone(48, 72, 48, 72, "Zone C");
        launchLine = new CoordinateZone(0, 72, 12, 12, "Launch Line");
    }
}
