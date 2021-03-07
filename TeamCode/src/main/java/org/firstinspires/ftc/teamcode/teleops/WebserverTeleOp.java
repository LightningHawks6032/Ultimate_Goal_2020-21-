package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;

@TeleOp(name="webserver")
public class WebserverTeleOp extends OpMode {

    @Override
    public void init() {
        JavaHTTPServer.init();
        JavaHTTPServer.pathData.clear();
        for (int i = 0; i < 10; i++)
            JavaHTTPServer.pathData.add(new RobotPos[] {new RobotPos(0,i,0),new RobotPos(1,1,1)});
    }

    @Override
    public void loop() {
        JavaHTTPServer.update();
    }

    @Override
    public void stop() {
        JavaHTTPServer.close();
        super.stop();
    }
}
