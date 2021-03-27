package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.drive.PositionTracker;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;


@TeleOp(name = "Drive", group = "drive")
//@Disabled
public class DrivetrainTeleOp extends OpMode {

    DriveController driveController;
    BotHardware hardware;
    PositionTracker tracker;
    VuforiaMethods voof;

    public void init(){
        double t = getRuntime();
        hardware = new BotHardware(hardwareMap);
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, hardware);
        driveController.updateMotors(t);
        tracker = new PositionTracker(hardware,telemetry,driveController);
        tracker.init(t);
        voof = new VuforiaMethods(hardwareMap);
        voof.initVuforia();

        JavaHTTPServer.init();
        JavaHTTPServer.clear();
    }

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
        tracker.updatePosition(t);

        hardware.launchServo.setPosition(launch);

        if (gamepad2.a) hardware.wobbleGrabber.setPosition(-0.7);
        if (gamepad2.b) hardware.wobbleGrabber.setPosition(-0.3);

        hardware.wobbleLifter.setPower(1);
        if (gamepad2.dpad_left) hardware.wobbleLifter.setPos(150);
        if (gamepad2.dpad_right) hardware.wobbleLifter.setPos(300);
        telemetry.addLine(gamepad2.dpad_left+","+gamepad2.dpad_right);

        JavaHTTPServer.addPoint(tracker.getPos(),voof.getPosition(null));

        telemetry.addData("Voof rotation", voof.getPosition())

        telemetry.addLine(tracker.getPos().toString());

        telemetry.update();
    }
}
