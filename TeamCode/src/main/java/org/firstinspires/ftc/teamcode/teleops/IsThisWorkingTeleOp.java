package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

@TeleOp(name = "If this is here its still working", group = "Almost Useless")
@Disabled
public class IsThisWorkingTeleOp extends OpMode{
    VuforiaMethods voof;

    public void init(){
        voof = new VuforiaMethods(hardwareMap);
        voof.initVuforia();
    }

    public void loop(){
        RobotPos p = voof.getPosition(null);
        if (p == null)
            telemetry.addLine("non");
        else
            telemetry.addLine("YEET: "+p.toString());
    }
}
