package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.jetbrains.annotations.NotNull;

public class IMUAccelerationIntegrator implements BNO055IMU.AccelerationIntegrator
{
    BNO055IMU.Parameters parameters;
    Acceleration acceleration;
    Position pos;
    Velocity vel;

    @Override public void initialize(@NotNull BNO055IMU.Parameters parameters, Position initialPosition, Velocity initialVelocity)
    {
        this.parameters = parameters;
        pos = initialPosition == null ? new Position() : initialPosition;
        vel = initialVelocity == null ? new Velocity() : initialVelocity;
    }

    @Override public Position getPosition() {
        if (pos != null)
            return new Position(pos.unit,pos.x,pos.y,pos.z,pos.acquisitionTime);
        else
            return new Position();
    }
    @Override public Velocity getVelocity() {
        if (vel != null)
            return new Velocity(vel.unit,vel.xVeloc,vel.yVeloc,vel.zVeloc,vel.acquisitionTime);
        else
            return new Velocity();
    }
    @Override public Acceleration getAcceleration()
    {
        return this.acceleration == null ? new Acceleration() : this.acceleration;
    }

    @Override public void update(Acceleration linearAccelerationIn)
    {
        Acceleration linearAcceleration = linearAccelerationIn.toUnit(DistanceUnit.METER);
        if (acceleration != null) {
            acceleration = acceleration.toUnit(DistanceUnit.METER);

            long aqT = linearAcceleration.acquisitionTime;
            double dt = (linearAcceleration.acquisitionTime - acceleration.acquisitionTime)*1e-9;
            double dtsq = 0.5*dt*dt;

            DistanceUnit posUnit = pos.unit;
            DistanceUnit velUnit = vel.unit;

            pos = pos.toUnit(DistanceUnit.METER);
            vel = vel.toUnit(DistanceUnit.METER);

            vel = new Velocity(DistanceUnit.METER,
                    vel.xVeloc+acceleration.xAccel*dt,
                    vel.yVeloc+acceleration.yAccel*dt,
                    vel.zVeloc+acceleration.zAccel*dt,
                    aqT);
            pos = new Position(DistanceUnit.METER,
                    pos.x+acceleration.xAccel*dtsq+vel.xVeloc*dt,
                    pos.y+acceleration.yAccel*dtsq+vel.yVeloc*dt,
                    pos.z+acceleration.zAccel*dtsq+vel.zVeloc*dt,
                    aqT);

            pos = pos.toUnit(posUnit);
            vel = vel.toUnit(velUnit);
        }
        acceleration = linearAcceleration;
    }
}
