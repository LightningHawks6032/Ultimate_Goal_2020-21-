package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.drive.imu.IMUAccelerationIntegrator;
import org.firstinspires.ftc.teamcode.hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.hardware.HardwareMapKeys;

import java.util.ArrayList;
import java.util.List;

public class AutoController {

    final List<Position> waypoints;
    IMUAccelerationIntegrator accInt;

    final BNO055IMU imu;

    final DriveMotors motors;

    public AutoController(HardwareMap hardwaremap) {
        waypoints = new ArrayList<>();
        motors = new DriveMotors(hardwaremap);

        imu = hardwaremap.get(BNO055IMU.class, HardwareMapKeys.IMU);
    }

    public void init() {
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.mode = BNO055IMU.SensorMode.IMU;
        params.calibrationData = new BNO055IMU.CalibrationData();
        accInt = new IMUAccelerationIntegrator();

        imu.initialize(params);
        accInt.initialize(params,new Position(),new Velocity());
    }

    public void addPoint(Position pos) {
        waypoints.add(pos);
    }

    public String update(boolean e) {
        motors.fr.setTargetPosition(e ? (int) (HardwareConstants.TICKS_PER_REV) : 0);
        motors.fr.setPower(1f-(motors.fr.getCurrentPosition()-motors.fr.getTargetPosition())/100f);
        return ";"+motors.fr.getCurrentPosition()+";"+motors.fr.getTargetPosition();
    }
}
