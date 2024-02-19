package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Config
@TeleOp
public class PIDF_IntakeExtension extends OpMode {
    private PIDFController intakeSlideController;

    public static double p = 0.02, i = 0, d = 0.0001;
    public static double f = 0.00005;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0;

    @Override
    public void init() {
        intakeSlideController = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // reset slide telemetry for more accuracy
        final Hardware hardware = new Hardware(hardwareMap);
        hardware.leftIntakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightIntakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftIntakeExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightIntakeExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        final Hardware hardware = new Hardware(hardwareMap);
        intakeSlideController.setPIDF(p, i, d, f);
        int slidePos = (hardware.leftIntakeExtension.getCurrentPosition()+hardware.rightIntakeExtension.getCurrentPosition())/2;
        double pidf = intakeSlideController.calculate(slidePos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double power = pidf + ff;

        hardware.leftIntakeExtension.setPower(power);
        hardware.rightIntakeExtension.setPower(power);

        telemetry.addData("pos ", slidePos);
        telemetry.addData("target", target);

        telemetry.update();
    }
}