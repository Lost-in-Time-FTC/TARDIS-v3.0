package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class OuttakeSubsystem extends SubsystemBase {
    private final double defaultPower = 0.25;
    private Hardware hardware;

    public OuttakeSubsystem(Hardware hardware) {
        this.hardware = hardware;
    }

    public void tiltUp() {
        hardware.topDifferential.setPosition(-defaultPower);
        hardware.bottomDifferential.setPosition(defaultPower);
    }

    public void tiltDown() {
        hardware.topDifferential.setPosition(defaultPower);
        hardware.bottomDifferential.setPosition(-defaultPower);
    }

    public void rotateLeft() {
        hardware.topDifferential.setPosition(0);
        hardware.bottomDifferential.setPosition(0);
    }

    public void rotateRight() {
        hardware.topDifferential.setPosition(1);
        hardware.bottomDifferential.setPosition(1);
    }
}
