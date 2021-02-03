package org.firstinspires.ftc.teamcode.hardware.vision;

public class VisionPositions {
    private final int FHEIGHT = 144;
    private final int FWIDTH = 72;
    private final int SQUARE = 24;

    public VisionPos leftGoal, rightGoal, initstack;
    //A = 0, B = 1, C = 4 in a stack
    public VisionZone targetA, targetB, targetC, launchLine;

    //The grid goes from x = -24 to x = 72 and y = -72 to y = 72
    //Remember to flip x and y and take the inverse of the x
    public VisionPositions(){
        leftGoal = new VisionPos(-2*SQUARE,-2*SQUARE, "Left WobbleGoal");
        rightGoal = new VisionPos(-SQUARE, -2*SQUARE, "Right WobbleGoal");
        initstack = new VisionPos(-36, -36, "Stack");

        targetA = new VisionZone(-48, -72, 0, 24, "Zone A");
        targetB = new VisionZone(-24, -48, 24, 48, "Zone B");
        targetC = new VisionZone(-48, -72, 48, 72, "Zone C");
        launchLine = new VisionZone(0, -72, 12, 12, "Launch Line");
    }
}
