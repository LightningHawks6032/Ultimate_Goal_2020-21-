package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotPos;
import org.firstinspires.ftc.teamcode.debug.JavaHTTPServer;
import org.firstinspires.ftc.teamcode.hardware.BotHardware;
import org.firstinspires.ftc.teamcode.hardware.auto.AutoController;
import org.firstinspires.ftc.teamcode.hardware.sound.Sounds;
import org.firstinspires.ftc.teamcode.hardware.vision.VuforiaMethods;

import java.util.ArrayList;
import java.util.List;

@Autonomous(group = "autos", name = "auto")
public class AutoOpMode extends LinearOpMode {

    protected AutoController controller;
    protected VuforiaMethods vuforia;
    protected RobotPos visionPos;

    protected List<TimeTarget> timeTargets = new ArrayList<>();
    protected List<TimeAction> timeActions = new ArrayList<>();

    private int k;

    @Override
    public void runOpMode() {
        JavaHTTPServer.init();
        JavaHTTPServer.pathData.clear();
        final Sounds sounds = new Sounds(hardwareMap);

        telemetry.addLine("STARTING");

        final BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();

        timeTargets.add(new TimeTarget(0,new RobotPos(-48,-48,0)));
        timeTargets.add(new TimeTarget(3,new RobotPos(-48,-48,-0.34)));
        timeTargets.add(new TimeTarget(6,new RobotPos(-48,-48,0)));
        timeTargets.add(new TimeTarget(9,new RobotPos(-48,-12,0)));
        timeTargets.add(new TimeTarget(12,new RobotPos(-48,60,0)));
        timeTargets.add(new TimeTarget(15,new RobotPos(-48,-48,0)));
        timeTargets.add(new TimeTarget(18,new RobotPos(-48,-48,-Math.PI)));
        timeTargets.add(new TimeTarget(21,new RobotPos(-48,-48,0)));
        timeTargets.add(new TimeTarget(24,new RobotPos(-48,60,0)));
        //timeTargets.add(new TimeTarget(20,new RobotPos(-10,0,Math.PI)));

        timeActions.add(new TimeAction(5, new Runnable(){public void run(){
            sounds.play("skreem");
            bh.outtakeAngle.servo.setPosition(0.6);
            bh.wobbleLifter.setPos(0);
        }}));
        timeActions.add(new TimeAction(20, new Runnable(){public void run(){
            sounds.play("skreem");
            bh.outtakeAngle.servo.setPosition(0.8);
            bh.wobbleLifter.setPos(10000);
        }}));

        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();
        sounds.play("Megalovania");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        double t = getRuntime();
        while (t < 30) { //30 seconds
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
            t = getRuntime();

            controller.setTarget(getTarget((float)t));
            tryTimeRunnable((float)t);

            visionPos = vuforia.getPosition(visionPos);
            if (visionPos != null) telemetry.addLine("VIZPOZ: "+visionPos.toString());
            if (visionPos != null) controller.correctForVisionPos(visionPos);
            controller.update(t);

            telemetry.addLine("POS: "+controller.getPos().toString());
            telemetry.addLine("I: "+k);
            telemetry.update();

            JavaHTTPServer.pathData.add(new RobotPos[]{controller.getPos(),visionPos});
        }
        controller.driveController.setMotors_YXR(0,0,0);

        requestOpModeStop();
    }

    private RobotPos getTarget(float t) {
        for (int i = 0; i < timeTargets.size(); i++) {
            TimeTarget tt = timeTargets.get(i);
            k=i;
            if (t > tt.time) return tt.target;
        }
        return null;
    }
    private void tryTimeRunnable(float t) {
        for (TimeAction ta : timeActions)
            ta.tryRun(t);
    }

    public static class TimeTarget {
        public final float time;
        public final RobotPos target;
        public TimeTarget(float time, RobotPos target) {
            this.time = time;
            this.target = target;
        }
    }
    public static class TimeAction {
        public final float time;
        public final Runnable callback;
        public boolean ran = false;
        public TimeAction(float time, Runnable callback) {
            this.time = time;
            this.callback = callback;
        }
        public void tryRun(float t) {
            if (!ran && t > time)
                run();
        }
        private void run() {
            ran = true;
            callback.run();
        }
    }
}
