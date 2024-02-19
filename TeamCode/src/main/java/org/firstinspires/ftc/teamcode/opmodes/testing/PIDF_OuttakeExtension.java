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
public class PIDF_OuttakeExtension extends OpMode {
    private PIDFController outtakeSlideController;

    public static double p = 0.02, i = 0, d = 0.0001;
    public static double f = 0.00005;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0;

    @Override
    public void init() {
        outtakeSlideController = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //reset slide telemetry for more accuracy
        final Hardware hardware = new Hardware(hardwareMap);
        hardware.outtakeExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.outtakeExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void loop() {
        final Hardware hardware = new Hardware(hardwareMap);
        outtakeSlideController.setPIDF(p, i, d, f);
        int slidePos = hardware.outtakeExtension.getCurrentPosition();
        double pidf = outtakeSlideController.calculate(slidePos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
        double power = pidf + ff;

        hardware.outtakeExtension.setPower(power);

        if (gamepad2.right_stick_y > 0.2) {
            target = 300;
        }
        else if (gamepad2.right_stick_y < -0.2) {
            target = 800;
        }

        telemetry.addData("pos ", slidePos);
        telemetry.addData("target", target);

        telemetry.update();
    }
}
