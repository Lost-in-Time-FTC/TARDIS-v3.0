package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.DriveCode;

@SuppressWarnings("unused")
@TeleOp(name = "LiT Drive Program 2022-2023", group = "Linear OpMode")

public class LiTDrive extends LinearOpMode {

    private PIDController controller;

    public static double p = -0.007, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private PIDController slidecontroller;

    public static double p2 = 0.006, i2 = 0, d2 = 0;
    public static double f2 = 0;

    public static int target2 = 0;

    private final double tickes_in_degree = 700 / 180.0;

    enum ClawToggleTriState {
        FALSE,
        OPEN,
        WIDE_OPEN
    }

    // Declare OpMode members
    private final ElapsedTime runtime = new ElapsedTime();
    boolean clawToggle = false;
    ClawToggleTriState rightClawToggle = ClawToggleTriState.FALSE;
    ClawToggleTriState leftClawToggle = ClawToggleTriState.FALSE;
    private Hardware hardware = null;
    public void runOpMode() {
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        hardware = new Hardware(hardwareMap);

        hardware.armMotor.setDirection(DcMotor.Direction.FORWARD);
        hardware.elevatorMotor.setDirection(DcMotor.Direction.FORWARD);

        controller = new PIDController(p, i, d);
        slidecontroller = new PIDController(p2, i2, d2);

        hardware.armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        hardware.elevatorMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        hardware.armMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        hardware.elevatorMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            toggle(currentGamepad2, previousGamepad2);
            DriveCode.drive(gamepad1, hardware);
            claw();
            elevator();
            armPivot();
            telemetry.addData("slide", hardware.elevatorMotor.getCurrentPosition());
            telemetry.update();

            telemetry.addData("arm", hardware.armMotor.getCurrentPosition());
            telemetry.addData("armtarget", target);
            telemetry.addData("slidetarget", target2);

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
        slidecontroller.setPID(p2, i2, d2);
        int armPos = hardware.elevatorMotor.getCurrentPosition();
        double pid = slidecontroller.calculate(armPos, target2);
        double ff = Math.cos(Math.toRadians(target2 / tickes_in_degree)) * f;

        if (Math.abs(gamepad2.right_stick_y) < .1) {
            double power = pid + ff;
            hardware.elevatorMotor.setPower(power);
        } else {
            hardware.elevatorMotor.setPower(-gamepad2.right_stick_y);
            target2 = hardware.elevatorMotor.getCurrentPosition();
        }

        final double LEFT_CLAW_OPEN = 1;
        final double LEFT_CLAW_CLOSE = 0;
        final double RIGHT_CLAW_OPEN = 0;
        final double RIGHT_CLAW_CLOSE = 1;

        if (gamepad2.y) {
            target2 = 1000;
            switch (leftClawToggle) {
                case OPEN:
                    leftClawToggle = ClawToggleTriState.WIDE_OPEN;}
            hardware.verticalServo.setPosition(.45);

        }

        if (gamepad2.b) {
            target2 = 0;
        }


    }

    // control arm's circular motion
    public void armPivot() {
        //double armPivotSpeed = 0.85;
        //double armPower = gamepad2.left_stick_y;
        controller.setPID(p, i, d);
        int armPos = hardware.armMotor.getCurrentPosition();
        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / tickes_in_degree)) * f;

        if (Math.abs(gamepad2.left_stick_y) < .1)
        { double power = pid + ff;
            hardware.armMotor.setPower(power); }

        else {hardware.armMotor.setPower(gamepad2.left_stick_y);
            target = hardware.armMotor.getCurrentPosition();}

        if (gamepad2.y) {
            target = -550;
        }

        if (gamepad2.b) {
            target = 300;

        }
    }
}