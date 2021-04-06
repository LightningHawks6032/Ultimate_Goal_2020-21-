package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

/*
  ___   _______ ______          __  __     __   ___ ____ ___    ___
 |  _| |__   __|  ____|   /\   |  \/  |   / /  / _ \___ \__ \  |_  |
 | |      | |  | |__     /  \  | \  / |  / /_ | | | |__) | ) |   | |
 | |      | |  |  __|   / /\ \ | |\/| | | '_ \| | | |__ < / /    | |
 | |      | |  | |____ / ____ \| |  | | | (_) | |_| |__) / /_    | |
 | |_     |_|  |______/_/    \_\_|  |_|  \___/ \___/____/____|  _| |
 |___|                                                         |___|

*/


@Autonomous(group = "autos", name = "auto")
public class AutoOpMode extends LinearOpMode {

    protected AutoController controller;
    protected VuforiaMethods vuforia;
    protected RobotPos visionPos;
    protected BotHardware hardware;
    protected Sounds sounds;

    protected boolean done = false;

    @Override
    public void runOpMode() throws InterruptedException {
        JavaHTTPServer.init();
        JavaHTTPServer.clear();

        telemetry.addLine("STARTING");
        telemetry.update();

        sounds = new Sounds(hardwareMap);
        hardware = new BotHardware(hardwareMap);
        controller = new AutoController(hardware, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();

        telemetry.addLine("READY");
        telemetry.update();
        sounds.play("e-s");

        waitForStart();
        sounds.play("e-s");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        new Thread(new Runnable() { @SuppressWarnings("BusyWait") @Override public void run() {
            double ts = getRuntime(), t = 0;
            while (t < 29.95 && !done) { //30 seconds
                try { Thread.sleep(10); } catch (InterruptedException e) { break; }
                t = getRuntime()-ts;

                visionPos = vuforia.getPosition();
                if (visionPos != null) telemetry.addLine("VIZPOZ: "+visionPos.toString());
                if (visionPos != null) telemetry.addLine("DIFF: "+(visionPos.x-controller.getPos().x)+","+(visionPos.y-controller.getPos().y));
                telemetry.addLine("POS: "+controller.getPos().toString());
                if (visionPos != null) controller.correctForVisionPos(visionPos);
                controller.update(t);
                telemetry.update();

                if (!controller.preventVisionUpdate) JavaHTTPServer.addPoint(null,controller.getPos(),visionPos);
                else JavaHTTPServer.addPoint(controller.getPos(),null,visionPos);
            }
            controller.driveController.setMotors_YXR(0,0,0);
        }}).start();

        try {
            run();
        } finally {
            done = true;
            requestOpModeStop();
        }
    }
    private void run() throws InterruptedException {
        hardware.wobbleLifter.setPower(0.2);
        hardware.wobbleLifter.setPos(150);
        hardware.wobbleGrabber.setPosition(-0.7);
        hardware.setMotorPowerModifiers(1,1.2,0.08,0.04);

        controller.goToPos(-48,-48,0,2f);
        controller.goToPos(-48,-48,-0.7,1f,1.8f);
        final int nRings = vuforia.getRings();
        sounds.play("e-r"+nRings);
        controller.goToPos(-48,-48,0,1f);
        controller.goToPos(-56,-48,0,1f);

        hardware.setAccBoost(0.5);
        controller.goToPos(-56,0,0,4f,0.4f);
        controller.goToPos(-42,0,0,2f);
        hardware.setAccBoost(1);
        controller.goToNavTarget(1);
        controller.goToPos(-42,0,0,1f);
        controller.goToPos(-48,0,0,2f);

        // Move depending on how many rings there are
        switch (nRings) {
            case 0:
                controller.goToPos(-48,0,0,2f);
                controller.goToPos(-44,0,0,2f,0f);
                controller.putDownWobbleGoal();
                controller.goToPos(-20,0,0,2f);
                break;
            case 1:
                controller.goToPos(-48,28,0,2f);
                controller.goToPos(-20,28,0,2f,0f);
                controller.putDownWobbleGoal();
                controller.goToPos(-20,28,0,2f);
                break;
            case 4:
                controller.goToPos(-48,48,0,2f);
                controller.setPos(new RobotPos(-41,47,-0.01));
                controller.goToPos(-44,48,0,2f,0f);
                controller.putDownWobbleGoal();
                controller.goToPos(-20,48,0,2f);
                break;
        }
        controller.goToPos(-20,12,0,3f,1f);

    }
}
