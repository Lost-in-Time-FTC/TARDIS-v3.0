package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.qualcomm.robotcore.hardware.Gamepad;

import com.qualcomm.robotcore.util.Range;

public class DriveCode extends SubsystemBase{

    public static void drive(Gamepad gamepad, Hardware hardware) {
        // Mecanum
        double drive = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;
        double strafe = gamepad.left_stick_x;

        // Strafing
        double FL = Range.clip(drive + strafe + turn, -0.5, 0.5);
        double FR = Range.clip(drive + strafe - turn, -0.5, 0.5);
        double BL = Range.clip(drive - strafe + turn, -0.5, 0.5);
        double BR = Range.clip(drive - strafe - turn, -0.5, 0.5);

        double DriveSpeed = 3.5;
        double sniperPercent = 0.5;

        // Sniper mode
        if (gamepad.left_trigger > 0) {
            hardware.leftBack.setPower(FL * DriveSpeed * sniperPercent);
            hardware.rightFront.setPower(FR * DriveSpeed * sniperPercent);
            hardware.leftBack.setPower(BL * DriveSpeed * sniperPercent);
            hardware.rightBack.setPower(BR * DriveSpeed * sniperPercent);
        }
        // Brakes
        else if (gamepad.right_trigger > 0) {
            hardware.leftFront.setPower(FL * 0);
            hardware.rightFront.setPower(FR * 0);
            hardware.leftBack.setPower(BL * 0);
            hardware.rightBack.setPower(BR * 0);

        }
        // Normal drive
        else {
            hardware.leftFront.setPower(FL * DriveSpeed);
            hardware.rightFront.setPower(FR * DriveSpeed);
            hardware.leftBack.setPower(BL * DriveSpeed);
            hardware.rightBack.setPower(BR * DriveSpeed);
        }
    }
}
