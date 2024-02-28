package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.auto.AutoCommands;
import org.firstinspires.ftc.teamcode.opmodes.auto.PropBlobDetection;

@SuppressWarnings("unused")
@Autonomous(name = "RedCloseAuto")
public class RedCloseAuto extends AutoCommands {
    @Override
    public void runOpMode() throws InterruptedException {
        final double LEFT_CLAW_OPEN = 1;
        final double LEFT_CLAW_CLOSE = 0;
        final double RIGHT_CLAW_OPEN = 0;
        final double RIGHT_CLAW_CLOSE = 1;

        initHardware(PropBlobDetection.AllianceColor.RED);
        trackTelemetryWhileNotIsStarted();
        waitForStart();

        Hardware hardware = new Hardware(hardwareMap);

        // Store detected parking position
        PropBlobDetection.PropBlobPosition position = propBlobDetection.getPosition();
        // Reset encoders at the very beginning to maintain proper position throughout
        stopAndResetAllWheelEncoders();

        // Pick up preloaded pixels and adjust arm position
        if (position == PropBlobDetection.PropBlobPosition.LEFT) {
            telemetry.addData("left red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);


        } else if (position == PropBlobDetection.PropBlobPosition.CENTER) {
            telemetry.addData("center red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);

        } else if (position == PropBlobDetection.PropBlobPosition.RIGHT) {
            telemetry.addData("right red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
        }
    }
}

