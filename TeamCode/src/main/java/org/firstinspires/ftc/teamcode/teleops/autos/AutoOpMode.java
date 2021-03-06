package org.firstinspires.ftc.teamcode.teleops.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotPos;
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

    @Override
    public void runOpMode() throws InterruptedException {
        final Sounds sounds = new Sounds(hardwareMap);

        telemetry.addLine("STARTING");
        telemetry.update();

        final BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();

        timeTargets.add(new TimeTarget(4,new RobotPos(0,0,0)));
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
            Thread.sleep(10);
            t = getRuntime();

            controller.setTarget(getTarget((float)t));
            tryTimeRunnable((float)t);

            visionPos = vuforia.getPosition(visionPos);
            if (visionPos != null)telemetry.addLine("VIZPOZ: "+visionPos.toString());
            if (visionPos != null) controller.correctForVisionPos(visionPos);
            controller.update(t);

            telemetry.addLine("POS: "+controller.getPos().toString());
            telemetry.update();
        }
        controller.driveController.setMotors_YXR(0,0,0);
        requestOpModeStop();
    }

    private RobotPos getTarget(float t) {
        for (int i = 0; i < timeTargets.size(); i++) {
            TimeTarget tt = timeTargets.get(i);
            if (t > tt.time) return tt.target;
        }
        return null;
    }
    private void tryTimeRunnable(float t) {
        for (TimeAction ta : timeActions)
            ta.tryRun(t);
    }

    public class TimeTarget {
        public final float time;
        public final RobotPos target;
        public TimeTarget(float time, RobotPos target) {
            this.time = time;
            this.target = target;
        }
    }
    public class TimeAction {
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
