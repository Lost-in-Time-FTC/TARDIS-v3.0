package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.qualcomm.robotcore.hardware.Gamepad;

import com.qualcomm.robotcore.util.Range;

public class DriveCode extends SubsystemBase {

    public static void drive(Gamepad gamepad, Hardware hardware) {
        // Mecanum
        double drive = -gamepad.right_stick_x;
        double turn = gamepad.left_stick_y;
        double strafe = -gamepad.left_stick_x;

        // Strafing
        double FL = Range.clip(drive + strafe + turn, -0.5, 0.5);
        double FR = Range.clip(drive + strafe - turn, -0.5, 0.5);
        double BL = Range.clip(drive - strafe + turn, -0.5, 0.5);
        double BR = Range.clip(drive - strafe - turn, -0.5, 0.5);

        double DriveSpeed = 3;

        // Sniper mode
        if (gamepad.left_trigger > 0.1) {
            DriveSpeed = 0.75;
        }
        // Normal DRIVE
        hardware.leftFront.setPower(FL * DriveSpeed);
        hardware.rightFront.setPower(FR * DriveSpeed);
        hardware.leftBack.setPower(BL * DriveSpeed);
        hardware.rightBack.setPower(BR * DriveSpeed);
    }
}
