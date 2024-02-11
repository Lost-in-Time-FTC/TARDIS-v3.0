package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class OutakeSubsystem extends SubsystemBase {

    private Hardware hardware;

    public void rotateRight() {
        hardware.topDifferential.setPower(-0.5);
        hardware.bottomDifferential.setPower(0.5);
    }

    public void rotateLeft() {
        hardware.topDifferential.setPower(0.5);
        hardware.bottomDifferential.setPower(-0.5);
    }

    public void tiltUp() {
        hardware.topDifferential.setPower(-0.5);
        hardware.bottomDifferential.setPower(-0.5);
    }

    public void tiltDown() {
        hardware.topDifferential.setPower(0.5);
        hardware.bottomDifferential.setPower(0.5);
    }
}
