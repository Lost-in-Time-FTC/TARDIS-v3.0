package org.firstinspires.ftc.teamcode.opmodes.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.opmodes.subsystems.OuttakeSubsystem;

public class MoveDifferential extends CommandBase {

    // The subsystem the command runs on
    private OuttakeSubsystem outtakeSubsystem;

    public MoveDifferential(OuttakeSubsystem outtakeSubsystem) {
        this.outtakeSubsystem = outtakeSubsystem;
        addRequirements(outtakeSubsystem);
    }

    @Override
    public void execute() {
        outtakeSubsystem.rotateLeft();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
