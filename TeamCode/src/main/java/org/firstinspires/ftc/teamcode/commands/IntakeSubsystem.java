package org.firstinspires.ftc.teamcode.commands;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class IntakeSubsystem extends SubsystemBase {
    private final CRServo mechRotation;

    public IntakeSubsystem(final Hardware hardware) {
        mechRotation = hardware.bottomDifferential;
    }

    public void grab() {
        mechRotation.setDirection(DcMotorSimple.Direction.FORWARD);
        mechRotation.setPower(0.5);
    }

    public void release() {
        //
    }

}
