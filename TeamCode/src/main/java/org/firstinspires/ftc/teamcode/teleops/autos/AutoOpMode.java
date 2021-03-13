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

        final double yoff = -8;

        timeTargets.add(new TimeTarget(0,new RobotPos(-48,-48,0)));
        timeTargets.add(new TimeTarget(2,new RobotPos(-48,-48,-0.7)));
        timeTargets.add(new TimeTarget(5,new RobotPos(-48,-48,0)));
        // n=2 INSERT HERE //
        timeTargets.add(new TimeTarget(14,new RobotPos(-24,-24,0)));
        timeTargets.add(new TimeTarget(15,new RobotPos(-24,0,0)));


        timeActions.add(new TimeAction(5f, new Runnable(){public void run(){
            switch (vuforia.getRings()) {
                case 0:
                    sounds.play("b1");
                    sounds.play("b1");
                    timeTargets.add(3,new TimeTarget(7,new RobotPos(-48,-48,0.211)));
                    timeTargets.add(4,new TimeTarget(8,new RobotPos(-60,8+yoff,0.211)));
                    timeTargets.add(5,new TimeTarget(11,new RobotPos(-60,-24+yoff,0)));
                    break;
                case 1:
                    sounds.play("b1");
                    sounds.play("b2");
                    timeTargets.add(3,new TimeTarget(7,new RobotPos(-48,-48,-0.141)));
                    timeTargets.add(4,new TimeTarget(8,new RobotPos(-36,36+yoff,-0.141)));
                    timeTargets.add(5,new TimeTarget(11,new RobotPos(-36,-24+yoff,0)));
                    break;
                case 3:
                    sounds.play("b2");
                    sounds.play("b1");
                    timeTargets.add(3,new TimeTarget(7,new RobotPos(-48,-48,0.115)));
                    timeTargets.add(4,new TimeTarget(8,new RobotPos(-60,56+yoff,0.115)));
                    timeTargets.add(5,new TimeTarget(11,new RobotPos(-60,-24+yoff,0)));
                    break;
            }
        }}));
        //timeActions.add(new TimeAction(12, new Runnable() { public void run() {
        //    bh.wobbleLifter.setPos(300);
        //}}));
        timeActions.add(new TimeAction(14, new Runnable() { public void run() {
            bh.wobbleGrabber.setPosition(-0.3);
        }}));

        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();
        bh.wobbleLifter.setPower(0*0.2);
        //bh.wobbleLifter.setPos(150);
        //bh.wobbleGrabber.setPosition(-0.7);
        sounds.play("b1");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        double ts = getRuntime(), t = 0;
        while (t < 16) { //30 seconds
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

            if (visionPos != null) telemetry.addLine("DIFF: "+(visionPos.x-controller.getPos().x)+","+(visionPos.y-controller.getPos().y));
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
