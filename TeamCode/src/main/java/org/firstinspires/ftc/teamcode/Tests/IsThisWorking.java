package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "If this is here its still working", group = "Almost Useless")
public class IsThisWorking extends OpMode{
    private DcMotor daMotor;

    public void init(){
        daMotor = hardwareMap.get(DcMotor.class, "poop");
    }

    public void loop(){
        daMotor.setPower(gamepad1.left_stick_y);
        telemetry.addLine("Telemetry.");
    }
}
