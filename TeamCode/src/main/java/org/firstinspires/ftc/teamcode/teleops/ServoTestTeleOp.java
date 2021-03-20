package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.groups.AngleServo;

@TeleOp(name = "SERVO TESTER")
public class ServoTestTeleOp extends OpMode {

    BotHardware h;
    AngleServo as;
    Servo test;

    @Override
    public void init() {
        h = new BotHardware(hardwareMap);
        test = h.launchServo;
        as = new AngleServo(test,0.2,-1,1);
    }

    @Override
    public void loop() {
        as.update(getRuntime(),gamepad1.left_stick_y);

        telemetry.addLine("POS: "+test.getPosition());
        telemetry.update();
    }
}
