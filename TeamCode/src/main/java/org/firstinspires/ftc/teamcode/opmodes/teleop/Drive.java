package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.DriveCode;

@TeleOp(name = "Drive via CommandOpMode", group = "Linear OpMode")
public class Drive extends CommandOpMode {

    @Override
    public void initialize() {
        final Hardware hardware = new Hardware(hardwareMap);

        GamepadEx driverOp = new GamepadEx(gamepad1);
        GamepadEx manipulatorOp = new GamepadEx(gamepad2);

        OuttakeSubsystem outtakeSubsystem = new OuttakeSubsystem(hardware);
        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(hardware);

        manipulatorOp.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(outtakeSubsystem::rotateLeft));


        manipulatorOp.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(outtakeSubsystem::rotateRight));

        register(outtakeSubsystem);
        register(intakeSubsystem);
        schedule();
        run();
    }
    public void runOpMode() {
        final Hardware hardware = new Hardware(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad2.right_bumper) {
                hardware.leftIntakeExtension.setPower(1);
                hardware.rightIntakeExtension.setPower(-1);
            }
            else if (gamepad2.left_bumper) {
                hardware.leftIntakeExtension.setPower(-1);
                hardware.rightIntakeExtension.setPower(1);
            }
            else {
                hardware.leftIntakeExtension.setPower(0);
                hardware.rightIntakeExtension.setPower(0);
            }

            if (gamepad2.a) {
                hardware.intakeRoller.setPower(1);
            }
            else if (gamepad2.b) {
                hardware.intakeRoller.setPower(-1);
            }
            else {
                hardware.intakeRoller.setPower(0);
            }

            if (gamepad2.left_trigger > 0.1) {
                hardware.rightFourbar.setPosition(1);
                hardware.leftFourbar.setPosition(0);
            }
            if (gamepad2.right_trigger > 0.1) {
                hardware.rightFourbar.setPosition(0);
                hardware.leftFourbar.setPosition(1);
            }

            DriveCode.drive(gamepad1, hardware);
        }
    }
}
