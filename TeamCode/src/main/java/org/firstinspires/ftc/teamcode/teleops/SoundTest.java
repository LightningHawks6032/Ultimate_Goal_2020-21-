package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;

@TeleOp(name = "BEEP")
public class SoundTest extends OpMode {
    private Sounds sounds;

    public void init(){
        sounds = new Sounds(hardwareMap);
    }

    public void loop(){
        if (gamepad1.a){
            sounds.playSong();
        }
    }
}
