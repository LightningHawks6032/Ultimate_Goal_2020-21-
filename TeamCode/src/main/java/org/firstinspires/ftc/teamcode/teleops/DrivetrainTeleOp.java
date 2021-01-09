package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.DriveController;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.DriveMotors;


@TeleOp(name = "Drivtrain TeleOp", group = "Iterative Opmode")
//@Disabled
public class DrivetrainTeleOp extends OpMode {

    DriveController driveController;

    public void init(){
        driveController = new DriveController(DriveMotors.MotorClipMode.CLAMP, new BotHardware(hardwareMap));

    }

    //The grounds for testing other things with this program


    public void loop(){
        double powerL = gamepad1.left_stick_y;
        double powerR = gamepad1.right_stick_y;
        double triggerL = gamepad1.left_trigger;
        double triggerR = gamepad1.right_trigger;

        driveController.setMotors_LRX(powerL,powerR,triggerR-triggerL);

        telemetry.update();
    }
}
