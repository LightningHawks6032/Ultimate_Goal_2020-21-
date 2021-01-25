package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.sound.PlaySound;

public class SoundTest extends OpMode {
    double t = 0;
    public PlaySound sound;

    @Override
    public void init() {
        sound = new PlaySound();
    }

    @Override
    public void loop() {

        if (getRuntime() > t) {
            t += 1000;
            sound.playSound();
        }
    }
}
