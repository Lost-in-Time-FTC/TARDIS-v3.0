package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
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

        manipulatorOp.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(outtakeSubsystem::rotateLeft));


        manipulatorOp.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(outtakeSubsystem::rotateRight));

        register(outtakeSubsystem);
        schedule();
        run();

    }
    public void runOpMode() {
        final Hardware hardware = new Hardware(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            DriveCode.drive(gamepad1, hardware);
        }
    }
}
