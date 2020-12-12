package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Drivtrain TeleOp", group = "Iterative Opmode")
//@Disabled
public class DrivetrainTeleOp extends OpMode {
    DcMotor motorFL;
    DcMotor motorBL;
    DcMotor motorFR;
    DcMotor motorBR;


    public void init(){
        motorFL = hardwareMap.get(DcMotor.class, "fl");
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL = hardwareMap.get(DcMotor.class, "bl");
        motorBL.setDirection(DcMotor.Direction.FORWARD);

        motorFR = hardwareMap.get(DcMotor.class, "fr");
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorBR = hardwareMap.get(DcMotor.class, "br");
        motorBR.setDirection(DcMotor.Direction.REVERSE);


    }

    //The grounds for testing other things with this program


    public void loop(){
        double powerL = gamepad1.left_stick_y;
        double powerR = gamepad1.right_stick_y;
        double triggerL = gamepad1.left_trigger;
        double triggerR = gamepad1.right_trigger;

        motorFL.setPower(powerL);
        motorBL.setPower(powerL);
        motorFR.setPower(powerR);
        motorBR.setPower(powerR);

        if (triggerL < powerL) {
            motorFL.setPower(-triggerL);
            motorBL.setPower( triggerL);
            motorFR.setPower(-triggerL);
            motorBR.setPower( triggerL);
        } else if (triggerR < powerR) {
            motorFL.setPower( triggerR);
            motorBL.setPower(-triggerR);
            motorFR.setPower( triggerR);
            motorBR.setPower(-triggerR);
        }



        telemetry.update();
    }
}
