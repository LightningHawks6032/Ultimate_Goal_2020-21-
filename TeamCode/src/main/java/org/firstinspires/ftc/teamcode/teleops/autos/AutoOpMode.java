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

    private int k = 0;

    @Override
    public void runOpMode() {
        JavaHTTPServer.init();
        JavaHTTPServer.pathData.clear();
        final Sounds sounds = new Sounds(hardwareMap);

        telemetry.addLine("STARTING");
        telemetry.update();

        final BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();

        timeTargets.add(new TimeTarget(0,new RobotPos(-48,-48,0)));
        timeTargets.add(new TimeTarget(2,new RobotPos(-48,-48,-0.34)));
        timeTargets.add(new TimeTarget(4,new RobotPos(-48,-48,0)));
        // n=2 INSERT HERE //
        timeTargets.add(new TimeTarget(20,new RobotPos(-48,-63.75,0)));


        timeActions.add(new TimeAction(5.7f, new Runnable(){public void run(){
            sounds.play("skreem");
            switch (vuforia.getRings()) {
                case 0:
                    timeTargets.add(2,new TimeTarget(6,new RobotPos(-48,0,0)));
                    break;
                case 1:
                    timeTargets.add(2,new TimeTarget(6,new RobotPos(-48,24,0)));
                    timeTargets.add(3,new TimeTarget(10,new RobotPos(-48,24,-Math.PI/2)));
                    break;
                case 3:
                    timeTargets.add(2,new TimeTarget(6,new RobotPos(-48,48,0)));
                    break;
            }
        }}));

        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();
        sounds.play("Megalovania");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        double ts = getRuntime(), t = 0;
        while (t < 30) { //30 seconds
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
            t = getRuntime()-ts;

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
        telemetry.addLine(t+"s");
        for (int i = 0; i < timeTargets.size(); i++) {
            if (i == timeTargets.size()-1) return timeTargets.get(i).target;
            TimeTarget tt = timeTargets.get(i+1);
            k=i;
            if (t < tt.time) return timeTargets.get(i).target;
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
