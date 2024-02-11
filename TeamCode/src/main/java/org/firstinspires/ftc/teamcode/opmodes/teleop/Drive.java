package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.commands.MoveDifferential;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.OuttakeSubsystem;

@TeleOp(name = "Drive via CommandOpMode", group = "Linear OpMode")
public class Drive extends CommandOpMode {
  private final Hardware hardware = new Hardware(hardwareMap);

  @Override
  public void initialize() {
    OuttakeSubsystem outtakeSubsystem = new OuttakeSubsystem(hardware);

    schedule(new MoveDifferential(outtakeSubsystem));
    register(outtakeSubsystem);
  }
}
