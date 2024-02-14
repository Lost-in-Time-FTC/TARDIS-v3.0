package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.hardware.Hardware;


@SuppressWarnings("unused")
@TeleOp(name = "DriveCodeTesting", group = "Linear OpMode")
public class DriveTesting extends LinearOpMode {
    public static double tunableValue = 0.5;
    public static double tunableValue2 = 0.5;
    // Declare OpMode members
    FtcDashboard dashboard = FtcDashboard.getInstance();
    private final ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware;

    public void runOpMode() {
        hardware = new Hardware(hardwareMap);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();

            // Add tunable variable to the dashboard
            packet.put("Tunable Variable", tunableValue);

            // Send the TelemetryPacket to the dashboard
            dashboard.sendTelemetryPacket(packet);

            hardware.topDifferential.setPosition(tunableValue);
            hardware.bottomDifferential.setPosition(tunableValue2);

        }
    }
}
