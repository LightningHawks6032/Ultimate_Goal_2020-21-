package org.firstinspires.ftc.teamcode.hardware.auto;

import org.firstinspires.ftc.teamcode.Constants;

public class CompletionWait {

    public static void waitFor(CheckCallback callback, float timeout) throws InterruptedException {
        long t = System.nanoTime()+secondsToNanos(timeout);
        while (!callback.run()&&System.nanoTime()<t)
            //noinspection BusyWait
            Thread.sleep(1);
    }
    public static void waitFor(CheckCallback callback) throws InterruptedException {
        waitFor(callback, secondsToNanos(5));
    }
    public static long secondsToNanos(float seconds) {
        return (long)(1000000000f*seconds);
    }

    public static void autoControllerReachTarget(final AutoController controller,float timeout) throws InterruptedException {
        waitFor(new CheckCallback() {@Override public boolean run() {
            return controller.withinThreshold(Constants.TARGET_THRESH_LIN,Constants.TARGET_THRESH_ROT,Constants.TARGET_THRESH_VEL);
        }},timeout);
    }
    public static void autoControllerReachTarget(final AutoController controller) throws InterruptedException {
        autoControllerReachTarget(controller, 5f);
    }

    public static interface CheckCallback { boolean run(); }
}
