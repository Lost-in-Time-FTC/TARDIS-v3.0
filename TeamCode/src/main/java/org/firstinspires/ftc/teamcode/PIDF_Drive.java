package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class PIDF_Drive extends OpMode {
    private PIDFController driveController;

    public static double p3 = 0, i3 = 0, d3 = 0;
    public static double f3 = 0;

    public static int target3 = 0;

    private final double tickes_in_degree = 700 / 180.0;

    private DcMotorEx rightFront;
    private DcMotorEx leftFront;
    private DcMotorEx rightBack;
    private DcMotorEx leftBack;
    
    @Override
    public void init() {
        driveController = new PIDFController(p3, i3, d3, f3);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        rightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        leftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        leftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        rightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        rightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        leftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        leftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        int position = rightFront.getCurrentPosition();
    }

    @Override
    public void loop() {
        driveController.setPIDF(p3, i3, d3, f3);
        int frontRightMotorPos = rightFront.getCurrentPosition();
        double pid = driveController.calculate(frontRightMotorPos, target3);
        double ff = Math.cos(Math.toRadians(target3 / tickes_in_degree)) * f3;

        double power = pid + ff;

        rightFront.setPower(power);

        telemetry.addData("fr pos", frontRightMotorPos);
        telemetry.addData("target", target3);
        telemetry.update();
    }
}