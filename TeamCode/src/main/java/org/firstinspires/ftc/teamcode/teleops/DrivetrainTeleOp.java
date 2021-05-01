package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;


@TeleOp(name = "Drive", group = "drive")
//@Disabled
public class DrivetrainTeleOp extends OpMode {

    DriveController driveController;
    BotHardware hardware;
    //PositionTracker tracker;
    //VuforiaMethods voof;

    public void init(){
        oirj("A");
        double t = getRuntime();
        oirj("B");
        hardware = new BotHardware(hardwareMap);
        oirj("pC");
        oirj("Ca "+DriveMotors.MotorClipMode.CLAMP);
        oirj("Cb "+hardware);
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, hardware);
        oirj("D");
        driveController.updateMotors(t);
        oirj("E");
        //tracker = new PositionTracker(hardware,telemetry,driveController);
        oirj("F");
        //tracker.init(t);
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
        double powerL = gamepad1.left_stick_y;
        double powerR = gamepad1.right_stick_y;
        double triggerL = gamepad1.left_trigger;
        double triggerR = gamepad1.right_trigger;
        double outtake = gamepad2.y?1:0;
        double intake = gamepad2.x?1:0;
        double launch = gamepad2.right_bumper?0.45:0.15;
        double dv = (gamepad2.dpad_up?1:0)-(gamepad2.dpad_down?1:0);

        double t = getRuntime();


        driveController.setMotors_LRX(powerL,powerR,triggerL-triggerR);
        driveController.updateMotors(t);
        hardware.outtakeMotor.setPower(Constants.OUTTAKE_POWER_FAC*outtake);
        hardware.intakeMotor.setPower(Constants.INTAKE_POWER_FAC*intake);
        hardware.outtakeAngle.update(t,dv);
        //tracker.updatePosition(t);

        hardware.launchServo.setPosition(launch);

        hardware.wobbleGrabber.setPower(1f);
        if (!gamepad2.a) hardware.wobbleGrabber.setPower(-1);
        //if (gamepad2.b) hardware.wobbleGrabber.setPower(1);

        hardware.wobbleLifter.setPower(0.1f);
        int pos = hardware.wobbleLifter.getPos();
        if (gamepad2.dpad_left) hardware.wobbleLifter.setPos(pos-10);
        if (gamepad2.dpad_right) hardware.wobbleLifter.setPos(pos+10);
        telemetry.addLine(gamepad2.dpad_left+","+gamepad2.dpad_right);

        //JavaHTTPServer.addPoint(tracker.getPos(),voof.getPosition());

        //if(voof.getPosition() != null){telemetry.addData("Voof rotation", voof.getPosition().r);}

        //telemetry.addLine(tracker.getPos().toString());

        telemetry.update();
    }
}
