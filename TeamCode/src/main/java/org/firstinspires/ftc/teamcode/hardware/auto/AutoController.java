package org.firstinspires.ftc.teamcode.hardware.auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.drive.PositionTracker;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;

import java.util.Locale;

public class AutoController {

    //Mich will add comments of his understanding of the code

    RobotPos target = null;
    RobotPos pos = null;
    RobotPos vel = null;

    private static final RobotPos[] navTargetStairingPos = new RobotPos[]{null,new RobotPos(-48,4,Math.PI/2),null};

    public boolean preventVisionUpdate = true;

    public final DriveController driveController;
    final BotHardware hardware;
    final PositionTracker posTracker;

    final Telemetry telemetry;


    //Construct the auto controller
    public AutoController(BotHardware hardware, Telemetry telemetry) {
        this.hardware = hardware;
        this.driveController = new DriveController(DriveMotors.MotorClipMode.SCALE,hardware);
        this.telemetry = telemetry;
        this.posTracker = new PositionTracker(hardware,telemetry);
    }

    //overloaded inits
    public void init(double t) {
        driveController.initMotors(t);
        posTracker.init(t);
        pos = posTracker.getPos();
    }
    public void init(double t, RobotPos initialPos) {
        driveController.initMotors(t);
        posTracker.init(t,initialPos);
        pos = posTracker.getPos();
    }

    //Sets and stores the target position
    public void setTarget(RobotPos target) { this.target = target; }

    //Brings the robot to target position
    public void update(double t) {
        if (target == null) {
            driveController.setMotors_YXR(0,0,0);
            updatePosition(t);
            return;
        }
        updatePosition(t); //make motors know what the pos is

        RobotPos diff = pos.getDifferenceTo(target);
        double dX = diff.x, dY = diff.y, dR = diff.r, len = Math.sqrt(diff.x*diff.x+diff.y*diff.y);
        double vX = Math.signum(dX)*Math.max(0,Math.min(Constants.MOTOR_DECELL_DIST*Constants.MOVE_SCALE_SRF/hardware.getAccBoost(),Math.abs(dX)))/(Constants.MOTOR_DECELL_DIST*Constants.MOVE_SCALE_SRF/hardware.getAccBoost());
        double vY = -Math.signum(dY)*Math.max(0,Math.min(Constants.MOTOR_DECELL_DIST*Constants.MOVE_SCALE_FWD/hardware.getAccBoost(),Math.abs(dY)))/(Constants.MOTOR_DECELL_DIST*Constants.MOVE_SCALE_FWD/hardware.getAccBoost()) ;

        //double vX = Math.signum(dX)*Math.min(1,len/Constants.MOTOR_DECELL_DIST)/Constants.MOVE_SCALE_SRF;
        //double vY = -Math.signum(dY)*Math.min(1,len/Constants.MOTOR_DECELL_DIST)/Constants.MOVE_SCALE_FWD;

        vX = Math.cos(pos.r)*vX-Math.sin(pos.r)*vY;
        vY = Math.cos(pos.r)*vY+Math.sin(pos.r)*vX;
        double vR = Math.signum(dR)*Math.max(0,Math.min(Constants.MOTOR_DECELL_ROTDIST/hardware.getAccBoost(),Math.abs(dR)))/Constants.MOTOR_DECELL_ROTDIST*hardware.getAccBoost();
        //double vR = Math.signum(dR)*Math.min(1,Math.abs(dR)/Constants.MOTOR_DECELL_ROTDIST)/Constants.MOVE_SCALE_ROT;

        telemetry.addLine(String.format(Locale.ENGLISH,"%.2f %.2f %.2f",vX,vY,vR));

        vel = new RobotPos(vX,vY,vR);
        driveController.setMotors_YXR(vY,vX,vR);
    }

    //Factors a small amount of the vision position values into
    public void correctForVisionPos(RobotPos visionPos){
        if (preventVisionUpdate) return;
        posTracker.correctForVisionPos(visionPos);
        pos = posTracker.getPos();
    }

    //Update the position's data to the motors and stuff
    private void updatePosition(double t) {
        driveController.updateMotors(t);
        posTracker.updatePosition(t);
        pos = posTracker.getPos();
    }

    public void resetBasePos() { setBaseDist(0); }
    public void setBaseDist(double dist) {
        hardware.motors.fl.adjustBaseDist(hardware.motors.fl.calculateDist()+dist);
        hardware.motors.bl.adjustBaseDist(hardware.motors.bl.calculateDist()+dist);
        hardware.motors.fr.adjustBaseDist(hardware.motors.fr.calculateDist()+dist);
        hardware.motors.br.adjustBaseDist(hardware.motors.br.calculateDist()+dist);
    }
    public void setPos(RobotPos pos) { posTracker.setPos(pos); this.pos = this.posTracker.getPos(); }
    public RobotPos getPos() { return posTracker.getPos(); }

    public boolean withinThreshold(double distInches, double rotThresh, double speedThresh) {
        pos = posTracker.getPos();
        if (target == null || pos == null || vel == null) return false;
        RobotPos diff = target.getDifferenceTo(pos);
        boolean close = diff.x*diff.x+diff.y*diff.y < distInches*distInches;
        close &= Math.abs(diff.r) < rotThresh;
        close &= vel.x*vel.x+vel.y*vel.y < speedThresh*speedThresh;
        close &= Math.abs(vel.r) < speedThresh*rotThresh/distInches;
        return close;
    }


    public void putDownWobbleGoal() throws InterruptedException {
        hardware.wobbleLifter.setPos(200);
        Thread.sleep(1000);
        hardware.wobbleGrabber.setPower(-1);
        Thread.sleep(300);
        hardware.wobbleLifter.setPos(100);
        Thread.sleep(200);
        hardware.wobbleGrabber.setPower(1);
        Thread.sleep(200);
    }
    public void goToPos(double x, double y, double r, float timeout, float leaveDelay) throws InterruptedException {
        goToPos(new RobotPos(x,y,r),timeout,leaveDelay);
    }
    public void goToPos(RobotPos pos, float timeout, float leaveDelay) throws InterruptedException {
        setTarget(pos);
        CompletionWait.autoControllerReachTarget(this, timeout);
        Thread.sleep((long) (leaveDelay*1000));
    }
    public void goToPos(double x, double y, double r, float timeout) throws InterruptedException {
        goToPos(x, y, r, timeout, 0.2f);
    }

    public void goToNavTarget(int targetId) throws InterruptedException {
        goToPos(navTargetStairingPos[targetId],2f,0.5f);
        preventVisionUpdate = false;
        //setTarget(null);
        Thread.sleep(4000L);
        preventVisionUpdate = true;
    }
}
