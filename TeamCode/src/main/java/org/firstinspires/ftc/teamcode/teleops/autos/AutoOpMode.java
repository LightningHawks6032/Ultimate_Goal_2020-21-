package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.auto.CompletionWait;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

import java.util.ArrayList;
import java.util.List;

@Autonomous(group = "autos", name = "auto")
public class AutoOpMode extends LinearOpMode {

    protected AutoController controller;
    protected VuforiaMethods vuforia;
    protected RobotPos visionPos;

    protected boolean done = false;

    @Override
    public void runOpMode() throws InterruptedException {
        JavaHTTPServer.init();
        JavaHTTPServer.pathData.clear();
        final Sounds sounds = new Sounds(hardwareMap);

        telemetry.addLine("STARTING");
        telemetry.update();

        final BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
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

        new Thread(new Runnable() { @Override public void run() {
            double ts = getRuntime(), t = 0;
            while (t < 29.95 && !done) { //30 seconds
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
                t = getRuntime()-ts;

                visionPos = vuforia.getPosition(visionPos);
                if (visionPos != null) telemetry.addLine("VIZPOZ: "+visionPos.toString());
                if (visionPos != null) controller.correctForVisionPos(visionPos);
                controller.update(t);

                if (visionPos != null) telemetry.addLine("DIFF: "+(visionPos.x-controller.getPos().x)+","+(visionPos.y-controller.getPos().y));
                telemetry.addLine("POS: "+controller.getPos().toString());
                telemetry.update();

                JavaHTTPServer.pathData.add(new RobotPos[]{controller.getPos(),visionPos});
            }
            controller.driveController.setMotors_YXR(0,0,0);
        }}).start();


        bh.wobbleLifter.setPower(0.2);
        bh.wobbleLifter.setPos(150);
        bh.wobbleGrabber.setPosition(-0.7);

        goToPosSync(-48,-48,0);
        goToPosSync(-48,-48,-0.7);
        final int nRings = vuforia.getRings();
        goToPosSync(-48,-48,0);

        // Move depending on how many rings there are
        switch (nRings) {
            case 0:
                sounds.play("b1");
                sounds.play("b1");
                goToPosSync(-48,-48,0.211);
                putDownWobbleGoal(bh);
                goToPosSync(-60,0,0.211);
                goToPosSync(-60,-24,0);
                break;
            case 1:
                sounds.play("b1");
                sounds.play("b2");
                goToPosSync(-48,-48,-0.141);
                putDownWobbleGoal(bh);
                goToPosSync(-36,28,-0.141);
                goToPosSync(-36,-24,0);
                break;
            case 4:
                sounds.play("b2");
                sounds.play("b1");
                goToPosSync(-48,-48,0.115);
                putDownWobbleGoal(bh);
                goToPosSync(-60,48,0.115);
                goToPosSync(-60,-24,0);
                break;
        }
        goToPosSync(-24,-24,0);
        goToPosSync(-24,0,0);

        done = true;
        requestOpModeStop();
    }

    private void putDownWobbleGoal(BotHardware bh) throws InterruptedException {
        bh.wobbleLifter.setPos(300);
        Thread.sleep(1000);
        bh.wobbleGrabber.setPosition(-0.3);
        Thread.sleep(300);
        bh.wobbleLifter.setPos(150);
        Thread.sleep(200);
        bh.wobbleGrabber.setPosition(-0.7);
    }

    private void goToPosSync(double x, double y, double r) throws InterruptedException {
        controller.setTarget(new RobotPos(x,y,r));
        CompletionWait.autoControllerReachTarget(controller);
    }

}
