package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.groups.DriveMotors;


@TeleOp(name = "Drive", group = "drive")
//@Disabled
public class DrivetrainTeleOp extends OpMode {

    DriveController driveController;
    BotHardware hardware;

    public void init(){
        hardware = new BotHardware(hardwareMap);
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, hardware);
        driveController.updateMotors(getRuntime());
    }

    //The grounds for testing other things with this program


    public void loop(){
        double powerL = gamepad1.left_stick_y;
        double powerR = gamepad1.right_stick_y;
        double triggerL = gamepad1.left_trigger;
        double triggerR = gamepad1.right_trigger;
        double outtake = gamepad1.y?1:0;
        double intake = gamepad1.x?1:0;

        driveController.setMotors_LRX(powerL,powerR,triggerR-triggerL);
        driveController.updateMotors(getRuntime());
        hardware.outtakeMotor.setPower(Constants.OUTTAKE_POWER_FAC*outtake);
        hardware.intakeMotor.setPower(Constants.INTAKE_POWER_FAC*intake);

        telemetry.update();
    }
}
