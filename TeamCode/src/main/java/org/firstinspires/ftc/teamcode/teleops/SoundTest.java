package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;

@TeleOp(name = "BEEP")
public class SoundTest extends OpMode {
    private Sounds sounds;
    private boolean a = false;

    public void init(){
        sounds = new Sounds(hardwareMap);
    }

    public void loop() {
        SoundPlayer player = SoundPlayer.getInstance();

        if (gamepad1.a && !a) sounds.playSong();
        if (gamepad1.x) sounds.stop();
        a = gamepad1.a;

        if (a) telemetry.addLine("PLAY");
        else telemetry.addLine("NOTPLAY");
        telemetry.addLine(player.getMasterVolume()+" VOL; "+player.isLocalSoundOn()+" ILSO;");
        telemetry.update();

        try { Thread.sleep(10); }
        catch (InterruptedException e) {}
    }
}
