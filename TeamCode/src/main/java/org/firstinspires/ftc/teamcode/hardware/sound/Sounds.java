package org.firstinspires.ftc.teamcode.hardware.sound;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Sounds {
    HardwareMap hardwareMap;
    Context context;
    private int soundId;
    boolean soundPlaying = false;


    public Sounds (HardwareMap hardwareMa){
        hardwareMap = hardwareMa;
        context = hardwareMap.appContext;
        soundId = hardwareMap.appContext.getResources().getIdentifier("song", "raw", hardwareMap.appContext.getPackageName());
    }

    public void playSong(){
        SoundPlayer.getInstance().stopPlayingAll();
        SoundPlayer.getInstance().startPlaying(context, soundId);

    }
}