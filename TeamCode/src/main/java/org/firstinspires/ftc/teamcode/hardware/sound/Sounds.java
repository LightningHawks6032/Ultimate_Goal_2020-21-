package org.firstinspires.ftc.teamcode.hardware.sound;

import android.content.Context;
import android.view.SoundEffectConstants;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.function.Consumer;

public class Sounds {
    HardwareMap hardwareMap;
    Context context;
    private int soundId;
    boolean soundPlaying = false;


    public Sounds (HardwareMap hardwareMa){
        hardwareMap = hardwareMa;
        context = hardwareMap.appContext;
        soundId = hardwareMap.appContext.getResources().getIdentifier("song", "raw", hardwareMap.appContext.getPackageName());

        SoundPlayer player = SoundPlayer.getInstance();
    }

    public void stop() {
        SoundPlayer player = SoundPlayer.getInstance();
        player.stopPlayingAll();

    }
    public void playSong(){
        SoundPlayer player = SoundPlayer.getInstance();

        soundId = hardwareMap.appContext.getResources().getIdentifier("song", "raw", hardwareMap.appContext.getPackageName());
        System.out.println(player.preload(context,soundId));

        SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
        System.out.println(params.loopControl+"YEET"+params.isLooping());
        params.loopControl = 0;
        player.startPlaying(context, soundId, params,
            new Consumer<Integer>() { @Override public void accept(Integer integer){System.out.println("START");}},
            new Runnable(){@Override public void run(){System.out.println("END");}});
    }
}