package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.drive.PositionTracker;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;


@TeleOp(name = "FieldcentricDrive", group = "drive")
//@Disabled
public class FieldcentricDrivetrainTeleOp extends OpMode {

    DriveController driveController;
    BotHardware hardware;
    PositionTracker tracker;
    //VuforiaMethods voof;

    public void init(){
        oirj("A");
        double t = getRuntime();
        oirj("B");
        hardware = new BotHardware(hardwareMap);
        oirj("pC");
        oirj("Cb "+hardware);
        oirj("Ca "+DriveMotors.MotorClipMode.CLAMP);
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, hardware);
        oirj("D");
        driveController.updateMotors(t);
        oirj("E");
        tracker = new PositionTracker(hardware,telemetry,driveController);
        oirj("F");
        tracker.init(t);
        oirj("G");
        //voof = new VuforiaMethods(hardwareMap);
        oirj("H");
        //voof.initVuforia();
        oirj("I");

        JavaHTTPServer.init();
        oirj("J");
        JavaHTTPServer.clear();
        oirj("K");
    }
    void oirj(String z) { telemetry.addLine("D: "+z); telemetry.update(); }

    //The grounds for testing other things with this program


    public void loop(){
        double moveX = gamepad1.right_stick_x; // +: Fwds; -: Back;
        double moveY = gamepad1.left_stick_y; // +: Right; -: Left;
        double rotate = gamepad1.left_bumper?0:gamepad1.left_stick_x; // +: CW; -: CCW
        double outtake = gamepad2.y?1:0;
        double intake = gamepad2.x?1:0;
        double launch = gamepad2.right_bumper?0.45:0.15;
        double dv = (gamepad2.dpad_up?1:0)-(gamepad2.dpad_down?1:0);

        double t = getRuntime();

        double r = tracker.getPos().r;
        driveController.setMotors_YXR(moveY*Math.cos(r)+moveX*Math.sin(r),moveX*Math.cos(r)-moveY*Math.sin(r),rotate);
        driveController.updateMotors(t);
        hardware.outtakeMotor.setPower(Constants.OUTTAKE_POWER_FAC*outtake);
        hardware.intakeMotor.setPower(Constants.INTAKE_POWER_FAC*intake);
        hardware.outtakeAngle.update(t,dv);
        tracker.updatePosition(t);

        hardware.launchServo.setPosition(launch);

        RobotPos currentPos = tracker.getPos();
        if (gamepad1.x) tracker.setPos(new RobotPos(currentPos.x,currentPos.y,0));

        if (gamepad2.a) hardware.wobbleGrabber.setPosition(-0.7);
        if (gamepad2.b) hardware.wobbleGrabber.setPosition(-0.3);

        hardware.wobbleLifter.setPower(1);
        if (gamepad2.dpad_left) hardware.wobbleLifter.setPos(150);
        if (gamepad2.dpad_right) hardware.wobbleLifter.setPos(300);
        telemetry.addLine(gamepad2.dpad_left+","+gamepad2.dpad_right);

        JavaHTTPServer.addPoint(tracker.getPos());//,voof.getPosition()

        //if(voof.getPosition() != null){telemetry.addData("Voof rotation", voof.getPosition().r);}

        telemetry.addLine(tracker.getPos().toString());

        telemetry.update();
    }
}
