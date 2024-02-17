package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class IntakeSubsystem extends SubsystemBase {
    private final Hardware hardware;
    public IntakeSubsystem(Hardware hardware) {
        this.hardware = hardware;
    }

    public void runRoller() {
        hardware.intakeRoller.setPower(1);
    }

    public void rejectRoller() {
        hardware.intakeRoller.setPower(-1);
    }

    public void stopRoller() {
        hardware.intakeRoller.setPower(0);
    }
}
