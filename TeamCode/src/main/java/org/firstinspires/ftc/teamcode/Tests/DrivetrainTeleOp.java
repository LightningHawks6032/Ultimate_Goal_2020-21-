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

import java.util.ArrayList;


@TeleOp(name = "Drivtrain TeleOp", group = "Iterative Opmode")
//@Disabled
public class DrivetrainTeleOp extends OpMode {
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;

    ArrayList<DcMotor> drivetrain = new ArrayList<DcMotor>();


    public void init(){

        drivetrain.add(hardwareMap.get(DcMotor.class, "fl"));
        drivetrain.add(hardwareMap.get(DcMotor.class, "bl"));
        drivetrain.get(0).setDirection(DcMotor.Direction.FORWARD);
        drivetrain.get(1).setDirection(DcMotor.Direction.FORWARD);

        drivetrain.add(hardwareMap.get(DcMotor.class, "fr"));
        drivetrain.add(hardwareMap.get(DcMotor.class, "br"));
        drivetrain.get(2).setDirection(DcMotor.Direction.REVERSE);
        drivetrain.get(3).setDirection(DcMotor.Direction.REVERSE);



    }

    //The grounds for testing other things with this program


    public void loop(){


        drivetrain.get(0).setPower(2*gamepad1.left_stick_y);
        drivetrain.get(1).setPower(2*gamepad1.left_stick_y);
        drivetrain.get(2).setPower(2*gamepad1.right_stick_y);
        drivetrain.get(3).setPower(2*gamepad1.right_stick_y);

        if(gamepad1.left_trigger>gamepad1.left_stick_y){
            drivetrain.get(0).setPower(-gamepad1.left_trigger);
            drivetrain.get(1).setPower(gamepad1.left_trigger);
            drivetrain.get(2).setPower(-gamepad1.left_trigger);
            drivetrain.get(3).setPower(gamepad1.left_trigger);
        }else if(gamepad1.right_trigger>gamepad1.right_stick_y){
            drivetrain.get(0).setPower(gamepad1.right_trigger);
            drivetrain.get(1).setPower(-gamepad1.right_trigger);
            drivetrain.get(2).setPower(gamepad1.right_trigger);
            drivetrain.get(3).setPower(-gamepad1.right_trigger);
        }



        //////////////////////


        telemetry.update();
    }
}
