package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.hardware.groups.MRGyro;

@TeleOp(name = "gryo working?", group = "test")
public class GyroTesting extends OpMode {
    private MRGyro gs;

    public void init(){
        gs = new MRGyro( (hardwareMap.get(ModernRoboticsI2cGyro.class, "gs")), true);
    }

    public void loop(){

    }
}
