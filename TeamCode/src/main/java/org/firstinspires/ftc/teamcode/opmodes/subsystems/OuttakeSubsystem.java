package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class OuttakeSubsystem extends SubsystemBase {
    private final double defaultSpeed = 0.5;
    private final Hardware hardware;

    public OuttakeSubsystem(Hardware hardware) {
        this.hardware = hardware;
    }

    public void rotateRight() {
        hardware.topDifferential.setPower(-defaultSpeed);
        hardware.bottomDifferential.setPower(defaultSpeed);
    }

    public void rotateLeft() {
        hardware.topDifferential.setPower(defaultSpeed);
        hardware.bottomDifferential.setPower(-defaultSpeed);
    }

    public void tiltUp() {
        hardware.topDifferential.setPower(-defaultSpeed);
        hardware.bottomDifferential.setPower(-defaultSpeed);
    }

    public void tiltDown() {
        hardware.topDifferential.setPower(defaultSpeed);
        hardware.bottomDifferential.setPower(defaultSpeed);
    }
}
