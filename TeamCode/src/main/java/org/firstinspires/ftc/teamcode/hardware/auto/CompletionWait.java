package org.firstinspires.ftc.teamcode.hardware.auto;

import org.firstinspires.ftc.teamcode.Constants;

public class CompletionWait {

    public static void waitFor(CheckCallback callback) throws InterruptedException {
        while (!callback.run())
            //noinspection BusyWait
            Thread.sleep(1);
    }

    public static void autoControllerReachTarget(final AutoController controller) throws InterruptedException {
        waitFor(new CheckCallback() {@Override public boolean run() {
            return controller.withinThreshold(Constants.TARGET_THRESH_LIN,Constants.TARGET_THRESH_ROT,Constants.TARGET_THRESH_VEL);
        }});
    }

    public static interface CheckCallback { boolean run(); }
}
