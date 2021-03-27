package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

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

        waitForStart();
        sounds.play("b1");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        new Thread(new Runnable() { @SuppressWarnings("BusyWait") @Override public void run() {
            double ts = getRuntime(), t = 0;
            while (t < 29.95 && !done) { //30 seconds
                try { Thread.sleep(10); } catch (InterruptedException e) { break; }
                t = getRuntime()-ts;

                visionPos = vuforia.getPosition(visionPos);
                if (visionPos != null) telemetry.addLine("VIZPOZ: "+visionPos.toString());
                if (visionPos != null) telemetry.addLine("DIFF: "+(visionPos.x-controller.getPos().x)+","+(visionPos.y-controller.getPos().y));
                telemetry.addLine("POS: "+controller.getPos().toString());
                if (visionPos != null) controller.correctForVisionPos(visionPos);
                controller.update(t);
                telemetry.update();

                JavaHTTPServer.addPoint(controller.getPos(),visionPos);
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

        controller.goToPos(-48,-48,0);
        controller.goToPos(-48,-48,-0.7);
        Thread.sleep(1000);
        final int nRings = vuforia.getRings();
        //controller.goToPos(-28,36,0);
        Thread.sleep(1000);

        // Move depending on how many rings there are
        switch (nRings) {
            case 0:
                sounds.play("b1");
                sounds.play("b1");
                controller.goToPos(-48,0,0);
                controller.goToPos(-60,0,0);
                controller.putDownWobbleGoal();
                break;
            case 1:
                sounds.play("b1");
                sounds.play("b2");
                controller.goToPos(-48,28,0);
                controller.goToPos(-36,28,0);
                controller.putDownWobbleGoal();
                break;
            case 4:
                sounds.play("b2");
                sounds.play("b1");
                controller.goToPos(-48,48,0);
                controller.goToPos(-60,48,0);
                controller.putDownWobbleGoal();
                break;
        }
        controller.goToPos(-48,6,0);
    }
}
