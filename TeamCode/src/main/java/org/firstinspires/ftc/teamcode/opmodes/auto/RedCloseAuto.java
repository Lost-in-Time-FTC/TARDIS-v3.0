package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.auto.AutoCommands;
import org.firstinspires.ftc.teamcode.opmodes.auto.PropBlobDetection;

@SuppressWarnings("unused")
@Autonomous(name = "RedCloseAuto")
public class RedCloseAuto extends AutoCommands {
    private int WAIT_TIME_AFTER_PIXEL_DROP = 1000;
    private double DEFAULT_POWER = 0.4;
    private int DEGREE_90_IN_TICKS = 680;
    private int UNIVERSAL_MOVE_TO_SPIKE_TICKS = 850;
    private int UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS = 95;
    private double PARALLEL_VERTICAL_SERVO_POSITION = 0.6;
    private double ANGLED_TO_BOARD_SERVO_POSITION = 0.3;

    private void placeOnSpikeStrip(PropBlobDetection.PropBlobPosition position) {
        switch (position) {
            case LEFT:
                int rotateLeftTicks = 600;
                rotateLeft(rotateLeftTicks, DEFAULT_POWER);
                moveForward(UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS, DEFAULT_POWER);
                hardware.verticalServo.setPosition(PARALLEL_VERTICAL_SERVO_POSITION);
                hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
                sleep(WAIT_TIME_AFTER_PIXEL_DROP);
                moveBackward(UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS, DEFAULT_POWER);
                rotateRight(DEGREE_90_IN_TICKS + rotateLeftTicks, DEFAULT_POWER);
                break;
            case CENTER:
                moveForward(UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS + 10, DEFAULT_POWER);
                hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
                sleep(WAIT_TIME_AFTER_PIXEL_DROP);
                moveBackward(UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS, DEFAULT_POWER);
                rotateRight(680, DEFAULT_POWER);
                break;
            case RIGHT:
                int rotateRightTicks = 600;
                rotateRight(rotateRightTicks, DEFAULT_POWER);
                moveForward(UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS, DEFAULT_POWER);
                hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
                sleep(WAIT_TIME_AFTER_PIXEL_DROP);
                moveBackward(UNIVERSAL_CONTINUE_AFTER_SPIKE_TICKS, DEFAULT_POWER);
                rotateRight(DEGREE_90_IN_TICKS-rotateRightTicks, DEFAULT_POWER);
                break;
        }
    }

    private void placeOnBackdrop(PropBlobDetection.PropBlobPosition position) {
        switch (position) {
            case LEFT:
                strafeLeft(80, DEFAULT_POWER);
                break;
            case CENTER:
                strafeRight(250, DEFAULT_POWER);
                break;
            case RIGHT:
                strafeRight(500, DEFAULT_POWER);
                break;
        }
        // move arm, extend claw, drop pixel, retract
        hardware.armMotor.setTargetPosition(100);
        hardware.elevatorMotor.setPower(0.45);
        sleep(1000);
        hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
        sleep(1000);
        hardware.elevatorMotor.setPower(-0.45);
        sleep(1250);
        hardware.elevatorMotor.setPower(0);
    }

    private void parkInZone(PropBlobDetection.PropBlobPosition position) {
        switch (position) {
            case LEFT:
                strafeRight(300, DEFAULT_POWER);
                moveForward(75, DEFAULT_POWER);
                break;
            case CENTER:
                strafeRight(200, DEFAULT_POWER);
                moveForward(75, DEFAULT_POWER);
                break;
            case RIGHT:
                strafeRight(100, DEFAULT_POWER);
                moveForward(75, DEFAULT_POWER);
                break;
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initHardware(PropBlobDetection.AllianceColor.RED);
        setAllWheelMotorTargetPositionTolerance(50);
        stopAndResetAllWheelEncoders();
        trackTelemetryWhileNotIsStarted();
        waitForStart();

        PropBlobDetection.PropBlobPosition position = propBlobDetection.getPosition();

        closeTriadClaw();
        hardware.verticalServo.setPosition(PARALLEL_VERTICAL_SERVO_POSITION);
        moveForward(UNIVERSAL_MOVE_TO_SPIKE_TICKS, DEFAULT_POWER);
        placeOnSpikeStrip(position);
        // At this point, all positions have placed on the spike strip and are facing the board
        moveForward(1210, DEFAULT_POWER);
        placeOnBackdrop(position);
        // Park
        parkInZone(position);
    }
}
