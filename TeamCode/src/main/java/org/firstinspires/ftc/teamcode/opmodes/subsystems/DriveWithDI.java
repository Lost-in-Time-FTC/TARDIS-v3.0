package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

import java.lang.annotation.*;

// Custom annotation for dependency injection
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Inject {
}

public class DriveWithDI {

    @Inject
    private Gamepad gamepad;

    @Inject
    private Hardware hardware;

    public DriveWithDI() {
    }

    public void setGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public void drive() {
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

// Container class responsible for injecting dependencies
