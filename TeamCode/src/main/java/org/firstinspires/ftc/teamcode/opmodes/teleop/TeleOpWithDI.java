package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.DriveWithDI;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.DependencyInjector;


@SuppressWarnings("unused")
@TeleOp(name = "TeleOp w/ DI", group = "Linear OpMode")
@Config
public class TeleOpWithDI extends LinearOpMode {
    // Hardware Setup
    private Hardware hardware = null;

    // Claw TriState
    enum ClawToggleTriState {
        FALSE,
        OPEN,
        WIDE_OPEN
    }
    boolean clawToggle = false;
    ClawToggleTriState rightClawToggle = ClawToggleTriState.FALSE;
    ClawToggleTriState leftClawToggle = ClawToggleTriState.FALSE;

    // PID Constants
    final double ticks_in_degree = 700 / 180.0;

    // Rotation PIDF Setup
    private PIDFController rotationController;
    public static double rotationP = 0.007, rotationI = 0.0, rotationD = 0.0;
    public static double rotationF = 0.0002;
    int rotationTarget = 0;

    // OpMode Config
    private final ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        hardware = new Hardware(hardwareMap);

        DriveWithDI driveSubsystem = new DriveWithDI();

        try {
            DependencyInjector.injectDependencies(driveSubsystem, gamepad1, hardware);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            telemetry.addData("Error", "Dependency injection failed");
            telemetry.update();
            requestOpModeStop();
        }

        hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rotationController = new PIDFController(rotationP, rotationI, rotationD, rotationF);

//        hardware.armMotor.setDirection(DcMotor.Direction.FORWARD);
        hardware.elevatorMotor.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            toggle(currentGamepad2, previousGamepad2);
            driveSubsystem.drive();
            claw();
            elevator();
            armPivot();
        }
    }

    // toggling system
    public void toggle(Gamepad currentGamepad2, Gamepad previousGamepad2) {
        try {
            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);
        } catch (Exception e) {
            // :)
        }

        if (currentGamepad2.a && !previousGamepad2.a) {
            clawToggle = !clawToggle;
        }

        if (currentGamepad2.left_bumper && !previousGamepad2.left_bumper) {
            switch (leftClawToggle) {
                case OPEN:
                    leftClawToggle = ClawToggleTriState.WIDE_OPEN;
                    break;
                case WIDE_OPEN:
                    leftClawToggle = ClawToggleTriState.FALSE;
                    break;
                case FALSE:
                    leftClawToggle = ClawToggleTriState.OPEN;
                    break;
            }

//            leftClawToggle = !leftClawToggle;
        }

        if (currentGamepad2.right_bumper && !previousGamepad2.right_bumper) {
            switch (rightClawToggle) {
                case OPEN:
                    rightClawToggle = ClawToggleTriState.WIDE_OPEN;
                    break;
                case WIDE_OPEN:
                    rightClawToggle = ClawToggleTriState.FALSE;
                    break;
                case FALSE:
                    rightClawToggle = ClawToggleTriState.OPEN;
                    break;
            }

//            rightClawToggle = !rightClawToggle;
        }
    }

    // control everything on the claw: pincers + flipping
    public void claw() {
        // variables for fun
        final double LEFT_CLAW_OPEN = 1;
        final double LEFT_CLAW_CLOSE = 0;
        final double RIGHT_CLAW_OPEN = 0;
        final double RIGHT_CLAW_CLOSE = 1;

        // open/close both claws at the same time for fast pickup/dropoff
        if (gamepad2.a) {
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
        }

        // open/wide-open/close the claws individually for more precise placement
        switch (rightClawToggle) {
            case OPEN:
                hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
                break;
            case WIDE_OPEN:
                hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
                break;
            case FALSE:
                hardware.rightClawServo.setPosition(0.25);
                break;
        }

        switch (leftClawToggle) {
            case OPEN:
                hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
                break;
            case WIDE_OPEN:
                hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
                break;
            case FALSE:
                hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN-0.25);
                break;
        }

        if (gamepad2.left_trigger > 0.75) {
            hardware.verticalServo.setPosition(hardware.verticalServo.getPosition()-0.01);
        }

        if (gamepad2.right_trigger > 0.75) {
            hardware.verticalServo.setPosition(hardware.verticalServo.getPosition()+0.01);
        }
    }

    // control arm extension
    public void elevator() {
        hardware.elevatorMotor.setPower(gamepad2.right_stick_y);
    }

    // control arm's circular motion
    public void armPivot() {
        rotationController.setPIDF(rotationP, rotationI, rotationD, rotationF);
        int armPos = hardware.armMotor.getCurrentPosition();
        double pid = rotationController.calculate(armPos, rotationTarget);
        double ff = Math.cos(Math.toRadians(rotationTarget / ticks_in_degree));

        if (gamepad2.left_stick_y < 0.1 && gamepad2.left_stick_y > -0.1)
        {
            rotationTarget = hardware.armMotor.getCurrentPosition();
            double power = pid + ff;
            hardware.armMotor.setPower(power);

        }
        else
        {
            hardware.armMotor.setPower(gamepad2.left_stick_y);
        }
    }
}
