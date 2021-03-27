package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

@TeleOp(name = "VisionPos Test", group = "testing")
//@Disabled
public class VuforiaMethodsTesting extends OpMode {
    VuforiaMethods isabled = new VuforiaMethods(hardwareMap);
    RobotPos pos;

    public void init(){
        telemetry.addLine("Reafy");
        telemetry.update();
        pos = new RobotPos(0,0,0);
    }

    public void loop(){
        telemetry.addData("Target visible", isabled.targetVisible());
        if(isabled.getPosition()!=null)telemetry.addLine(""+isabled.getPosition());
        telemetry.update();
    }
}
