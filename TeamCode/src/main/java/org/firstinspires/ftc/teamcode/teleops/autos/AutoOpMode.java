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

    @Override
    public void runOpMode() throws InterruptedException {
        Sounds sounds = new Sounds(hardwareMap);

        telemetry.addLine("STARTING");
        telemetry.update();

        timeTargets.add(new TimeTarget(4,new RobotPos(0,0,0)));
        timeTargets.add(new TimeTarget(10,new RobotPos(-10,0,Math.PI)));

        BotHardware bh = new BotHardware(hardwareMap);
        controller = new AutoController(bh, telemetry);
        vuforia = new VuforiaMethods(hardwareMap);
        vuforia.initVuforia();

        telemetry.addLine("READY");
        telemetry.update();

        waitForStart();
        sounds.play("Megalovania");

        controller.init(getRuntime());
        controller.update(getRuntime());
        controller.resetBasePos();
        controller.setPos(new RobotPos(-48,-63.75,0));

        double t = getRuntime();
        while (t < 30) {
            //noinspection BusyWait
            Thread.sleep(10);
            t = getRuntime();

            controller.setTarget(getTarget((float)t));

            visionPos = vuforia.getPosition(visionPos);
            if (visionPos != null) controller.correctForVisionPos(visionPos);
            controller.update(t);

            telemetry.addLine("POS: "+controller.getPos().toString());
            telemetry.update();
        }
        controller.driveController.setMotors_YXR(0,0,0);
        requestOpModeStop();
    }

    private RobotPos getTarget(float t) {
        for (int i = 0; i > timeTargets.size(); i++) {
            TimeTarget tt = timeTargets.get(i);
            if (t > tt.time) return tt.target;
        }
        return null;
    }

    public class TimeTarget {
        public float time;
        public RobotPos target;
        public TimeTarget(float time, RobotPos target) {
            this.time = time;
            this.target = target;
        }

    }
}