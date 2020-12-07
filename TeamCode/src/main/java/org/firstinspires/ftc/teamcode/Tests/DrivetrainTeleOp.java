package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Drivtrain TeleOp", group = "Iterative Opmode")
//@Disabled
public class DrivetrainTeleOp extends OpMode {
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;


    public void init(){
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft = hardwareMap.get(DcMotor.class, "bl");
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        frontRight = hardwareMap.get(DcMotor.class, "fr");
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotor.class, "br");
        backRight.setDirection(DcMotor.Direction.REVERSE);


    }

    //The grounds for testing other things with this program


    public void loop(){

        frontLeft.setPower(gamepad1.left_stick_y);
        backLeft.setPower(gamepad1.left_stick_y);
        frontRight.setPower(gamepad1.right_stick_y);
        backRight.setPower(gamepad1.right_stick_y);

        if(gamepad1.left_trigger>gamepad1.left_stick_y){
            frontLeft.setPower(-gamepad1.left_trigger);
            backLeft.setPower(gamepad1.left_trigger);
            frontRight.setPower(-gamepad1.left_trigger);
            backRight.setPower(gamepad1.left_trigger);
        }else if(gamepad1.right_trigger>gamepad1.right_stick_y){
            frontLeft.setPower(gamepad1.right_trigger);
            backLeft.setPower(-gamepad1.right_trigger);
            frontRight.setPower(gamepad1.right_trigger);
            backRight.setPower(-gamepad1.right_trigger);
        }



        //////////////////////


        telemetry.update();
    }
}
