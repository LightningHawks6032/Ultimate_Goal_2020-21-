package org.firstinspires.ftc.teamcode.hardware.sound;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

public class Sounds {
    HardwareMap hardwareMap;
    Context context;
    private HashMap<String,Integer> soundMap = new HashMap<>();


    public Sounds (HardwareMap hardwareMa){
        hardwareMap = hardwareMa;
        context = hardwareMap.appContext;

        addSound("skreem","skreem");
        addSound("Megalovania","mega");
        addSound("IAmRecordingSound","im_recording_sound");
        addSound("e-s","beep_start");
        addSound("e-r0","beep_rings_0");
        addSound("e-r1","beep_rings_1");
        addSound("e-s4","beep_rings_2");
    }

    public void stop() {
        SoundPlayer player = SoundPlayer.getInstance();
        player.stopPlayingAll();
    }
    private void addSound(String publicId, String resId) {
        int id = hardwareMap.appContext.getResources().getIdentifier(resId, "raw", hardwareMap.appContext.getPackageName());
        soundMap.put(publicId,id);
    }
    @SuppressWarnings(value = "NullPointerException")
    public boolean play(String id){
        if (!soundMap.containsKey(id)) return false;
        int numericId = soundMap.get(id);
        SoundPlayer player = SoundPlayer.getInstance();
        player.startPlaying(context, numericId);
        return true;
    }
}