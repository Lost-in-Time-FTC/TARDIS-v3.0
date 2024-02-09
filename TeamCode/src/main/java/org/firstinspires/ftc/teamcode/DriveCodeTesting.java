package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

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
            if (gamepad2.left_stick_y > 0.1)
            {
                hardware.topDifferential.setDirection(DcMotorSimple.Direction.FORWARD);
                hardware.topDifferential.setPower(0.5);
            }
            else if (gamepad2.left_stick_y < -0.1)
            {
                hardware.topDifferential.setDirection(DcMotorSimple.Direction.REVERSE);
                hardware.topDifferential.setPower(0.5);
            }
            if (gamepad2.right_stick_y > 0.1)
            {
                hardware.bottomDifferential.setDirection(DcMotorSimple.Direction.FORWARD);
                hardware.bottomDifferential.setPower(0.5);
            }
            else if (gamepad2.right_stick_y < -0.1)
            {
                hardware.bottomDifferential.setDirection(DcMotorSimple.Direction.REVERSE);
                hardware.bottomDifferential.setPower(0.5);;
            }
        }
    }
}
