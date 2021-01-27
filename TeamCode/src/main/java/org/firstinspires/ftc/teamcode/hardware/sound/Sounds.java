package org.firstinspires.ftc.teamcode.hardware.sound;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Sounds {
    HardwareMap hardwareMap;
    Context context;
    private int dududuID;
    boolean soundPlaying = false;




    public Sounds (HardwareMap hardwareMa){
        hardwareMap = hardwareMa;
        context = hardwareMap.appContext;
        dududuID = hardwareMap.appContext.getResources().getIdentifier("Diamond Blade R2 [10-2-2020]", "raw", hardwareMap.appContext.getPackageName());
    }



    public void playMegalovenia(){
        SoundPlayer.getInstance().stopPlayingAll();
        SoundPlayer.getInstance().startPlaying(context, dududuID);

    }
}