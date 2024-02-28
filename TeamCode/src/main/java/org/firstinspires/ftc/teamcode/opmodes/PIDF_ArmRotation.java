package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Config
@TeleOp
public class PIDF_ArmRotation extends OpMode {
    private PIDFController rotationController;

    public static double p3 = 0, i3 = 0, d3 = 0;
    public static double f3 = 0;

    public static int target3 = 0;

    private final double tickes_in_degree = 700 / 180.0;

    @Override
    public void init() {
        Hardware hardware = new Hardware(hardwareMap);
        rotationController = new PIDFController(p3, i3, d3, f3);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        hardware.leftArmMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        hardware.rightArmMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Turn the motor back on when we are done
        hardware.leftArmMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); // Reset the motor encoder
        hardware.rightArmMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); // Turn the motor back on when we are done
    }

    @Override
    public void loop() {
        Hardware hardware = new Hardware(hardwareMap);
        rotationController.setPIDF(p3, i3, d3, f3);
        int rotationPos = (hardware.rightArmMotor.getCurrentPosition()+hardware.leftArmMotor.getCurrentPosition())/2;
        double pid = rotationController.calculate(rotationPos, target3);
        double ff = Math.cos(Math.toRadians(target3 / tickes_in_degree)) * f3;

        double power = pid + ff;

        hardware.leftArmMotor.setPower(power);
        hardware.rightArmMotor.setPower(power);

        telemetry.addData("fr pos", rotationPos);
        telemetry.addData("target", target3);
        telemetry.update();
    }
}