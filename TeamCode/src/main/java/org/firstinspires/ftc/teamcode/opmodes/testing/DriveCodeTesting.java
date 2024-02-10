package org.firstinspires.ftc.teamcode.opmodes.testing;

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
    private Hardware hardware = null;
    public void runOpMode() {
        hardware = new Hardware(hardwareMap);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
//            if (gamepad2.a) {
//                hardware.topDifferential.setPower(0.5);
//                hardware.bottomDifferential.setPower(-0.5);
//            }
//            else if (gamepad2.b) {
//                hardware.topDifferential.setPower(-0.5);
//                hardware.bottomDifferential.setPower(0.5);
//            }
//            else {
//                hardware.topDifferential.setPower(0);
//                hardware.bottomDifferential.setPower(0);
//            }
//            if (gamepad2.a)
//            {
//                hardware.bottomDifferential.setPower(1);
//            }
//            else if (gamepad2.b)
//            {
//                hardware.bottomDifferential.setPower(0);
//            }
//            else
//            {
//                hardware.topDifferential.setPower(0.5);
//            }
//
//            if (gamepad2.x)
//            {
//                hardware.bottomDifferential.setPower(1);
//            }
//            else if (gamepad2.y)
//            {
//                hardware.bottomDifferential.setPower(0);
//            }
//            else
//            {
//                hardware.bottomDifferential.setPower(0.5);
//            }
            if (gamepad2.a)
            {
                hardware.topDifferential.setPower(0);
            }
            else
            {
                hardware.topDifferential.setPower(0);
            }
        }
    }
}
