package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;


@SuppressWarnings("unused")
@TeleOp(name = "DriveCodeTesting", group = "Linear OpMode")

public class DriveCodeTesting extends LinearOpMode{
    // Declare OpMode members
    private final ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware;
    public void runOpMode() {
        hardware = new Hardware(hardwareMap);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            // testing for differential (working)
            // a differential servo ranges from 1 to -1, forward to reverse, 0 being stop
            // top diff
            if (gamepad2.left_stick_y > 0.2)
            {
                hardware.topDifferential.setPower(0.5);
            }
            else if (gamepad2.left_stick_y < -0.2)
            {
                hardware.topDifferential.setPower(-0.5);
            }
            else
            {
                hardware.topDifferential.setPower(0);
            }

            // bottom diff
            if (gamepad2.right_stick_y > 0.2)
            {
                hardware.bottomDifferential.setPower(-0.5);
            }
            else if (gamepad2.right_stick_y < -0.2)
            {
                hardware.bottomDifferential.setPower(0.5);
            }
            else
            {
                hardware.bottomDifferential.setPower(0);
            }
            if (gamepad2.left_bumper) {
            }
        }
    }
}
