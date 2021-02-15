package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;

import java.util.Random;

@TeleOp(name = "BEEP")
@Disabled
public class SoundTest extends OpMode {
    private Sounds sounds;
    private boolean a = false;
    private final Random rand = new Random();

    public void init(){
        sounds = new Sounds(hardwareMap);
    }

    public void loop() {
        SoundPlayer player = SoundPlayer.getInstance();

        if (gamepad1.a && !a) {
            sounds.play((new String[]{"Megalovania", "skreem", "IAmRecordingSound"})[rand.nextInt(3)]);
        }
        if (gamepad1.x) sounds.stop();
        a = gamepad1.a;

        if (a) telemetry.addLine("PLAY");
        else telemetry.addLine("NOTPLAY");
        telemetry.addLine("VOL:"+player.getMasterVolume()+"; ILSO:"+player.isLocalSoundOn()+";");
        telemetry.update();

        try { Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
