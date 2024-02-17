package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.IntakeSubsystem;
@SuppressWarnings("unused")
@TeleOp(name = "DriveCodeTesting", group = "Linear OpMode")
public class DriveTesting extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    public void initialize() {
        telemetry.addLine("test");
    }
    public void runOpMode() {
        Hardware hardware = new Hardware(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            hardware.intakeRoller.setPower(1);
        }
    }
}
