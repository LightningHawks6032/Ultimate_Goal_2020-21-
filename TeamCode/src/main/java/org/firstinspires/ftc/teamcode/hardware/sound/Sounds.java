package org.firstinspires.ftc.teamcode.hardware.sound;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.function.Consumer;

public class Sounds {
    HardwareMap hardwareMap;
    Context context;
    private int songId;
    private int skreemId;
    boolean soundPlaying = false;


    public Sounds (HardwareMap hardwareMa){
        hardwareMap = hardwareMa;
        context = hardwareMap.appContext;

        songId = hardwareMap.appContext.getResources().getIdentifier("song", "raw", hardwareMap.appContext.getPackageName());
        skreemId = hardwareMap.appContext.getResources().getIdentifier("skreem", "raw", hardwareMap.appContext.getPackageName());
    }

    public void stop() {
        SoundPlayer player = SoundPlayer.getInstance();
        player.stopPlayingAll();

    }
    public void playSong(){
        SoundPlayer player = SoundPlayer.getInstance();
        player.startPlaying(context, songId);
    }
    public void playSkreem(){
        SoundPlayer player = SoundPlayer.getInstance();
        player.startPlaying(context, skreemId);
    }
}