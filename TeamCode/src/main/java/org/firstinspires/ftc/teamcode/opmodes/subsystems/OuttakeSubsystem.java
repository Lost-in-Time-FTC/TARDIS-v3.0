package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class OuttakeSubsystem extends SubsystemBase {
    private final double defaultPower = 0.25;
    private final Hardware hardware;

    public OuttakeSubsystem(Hardware hardware) {
        this.hardware = hardware;
    }

    public void tiltUp() {
        hardware.leftDifferential.setPosition(-defaultPower);
        hardware.rightDifferential.setPosition(defaultPower);
    }

    public void tiltDown() {
        hardware.leftDifferential.setPosition(defaultPower);
        hardware.rightDifferential.setPosition(-defaultPower);
    }

    public void rotateLeft() {
        hardware.leftDifferential.setPosition(0);
        hardware.rightDifferential.setPosition(0);
    }

    public void rotateRight() {
        hardware.leftDifferential.setPosition(1);
        hardware.rightDifferential.setPosition(1);
    }
}
