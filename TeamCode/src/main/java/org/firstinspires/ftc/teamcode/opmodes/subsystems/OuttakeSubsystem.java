package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Config
public class OuttakeSubsystem extends SubsystemBase {
    private final double defaultPower = 0.25;
    private Hardware hardware;

    private PIDFController outtakeSlideController;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0;

    public OuttakeSubsystem(Hardware hardware) {
        this.hardware = hardware;
        outtakeSlideController = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void tiltUp() {
        hardware.leftDifferential.setPosition(-defaultPower);
        hardware.rightDifferential.setPosition(defaultPower);
    }

    public void tiltDown() {
        hardware.leftDifferential.setPosition(defaultPower);
        hardware.rightDifferential.setPosition(-defaultPower);
    }

    public void rotateLeft() {
        hardware.leftDifferential.setPosition(0);
        hardware.rightDifferential.setPosition(0);
    }

    public void rotateRight() {
        hardware.leftDifferential.setPosition(1);
        hardware.rightDifferential.setPosition(1);
    }


    @Override
    public void periodic() {
        super.periodic();
        outtakeSlideController.setPIDF(p, i, d, f);
        int slidePos = hardware.outtakeExtension.getCurrentPosition();
        double pidf = outtakeSlideController.calculate(slidePos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pidf + ff;

        hardware.outtakeExtension.setPower(power);

        telemetry.addData("pos ", slidePos);
        telemetry.addData("target", target);

        telemetry.update();
    }
}
