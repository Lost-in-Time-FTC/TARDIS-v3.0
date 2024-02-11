package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.commands.MoveDifferential;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.OuttakeSubsystem;

@TeleOp(name = "Drive via CommandOpMode", group = "Linear OpMode")
public class Drive extends CommandOpMode {

    @Override
    public void initialize() {
        final Hardware hardware = new Hardware(hardwareMap);

        GamepadEx driverOp = new GamepadEx(gamepad1);
        GamepadEx manipulatorOp = new GamepadEx(gamepad2);

        OuttakeSubsystem outtakeSubsystem = new OuttakeSubsystem(hardware);

        driverOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .toggleWhenPressed(new MoveDifferential(outtakeSubsystem), new InstantCommand(outtakeSubsystem::stop));

        register(outtakeSubsystem);
        schedule();
        run();
    }
}
