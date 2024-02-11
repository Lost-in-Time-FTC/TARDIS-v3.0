package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class OuttakeSubsystem extends SubsystemBase {
    private final double defaultPower = 0.5;
    private Hardware hardware;

    public OuttakeSubsystem(Hardware hardware) {
        this.hardware = hardware;
    }

    public void rotateRight() {
        hardware.topDifferential.setPower(-defaultPower);
        hardware.bottomDifferential.setPower(defaultPower);
    }

    public void rotateLeft() {
        hardware.topDifferential.setPower(defaultPower);
        hardware.bottomDifferential.setPower(-defaultPower);
    }

    public void tiltUp() {
        hardware.topDifferential.setPower(-defaultPower);
        hardware.bottomDifferential.setPower(-defaultPower);
    }

    public void tiltDown() {
        hardware.topDifferential.setPower(defaultPower);
        hardware.bottomDifferential.setPower(defaultPower);
    }

    public void stop() {
        hardware.topDifferential.setPower(0);
        hardware.bottomDifferential.setPower(0);
    }
}
