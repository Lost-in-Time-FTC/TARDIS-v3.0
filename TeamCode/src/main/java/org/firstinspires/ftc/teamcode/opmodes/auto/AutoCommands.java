package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@SuppressWarnings("unused")
public abstract class AutoCommands extends LinearOpMode {
    // PID
    public static double speed = 1200; // Arbitrary number; static to allow for analyzing how PID performs through multiple speeds in dashboard
    public static PIDCoefficients pidCoefficients = new PIDCoefficients(0, 0, 0); // PID coefficients that need to be tuned probably through FTC dashboard
    public PIDCoefficients pidGains = new PIDCoefficients(0, 0, 0); // PID gains which we will define later in the process
    ElapsedTime PIDTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS); // Timer
    // Constants
    final double LEFT_CLAW_OPEN = 0;
    final double LEFT_CLAW_CLOSE = 1;
    final double RIGHT_CLAW_OPEN = 1;
    final double RIGHT_CLAW_CLOSE = 0;
    final double CLAW_ROTATE_UP = 1;
    final double CLAW_ROTATE_DOWN = 0;
    final double ARM_PIVOT_SPEED = 1;
    final double ARM_PIVOT_SPEED_DOWN = 1;
    final int ARM_POSITION_LOW_TOLERANCE = 10;
    final int ARM_POSITION_HIGH_TOLERANCE = 50;
    // Name of the Webcam to be set in the config
    public String webcamName = "Webcam 1";
    // Hardware
    public Hardware hardware;
    public PropBlobDetection propBlobDetection;
    public OpenCvCamera camera;

    public final void initHardware(PropBlobDetection.AllianceColor color) {
        // Generic hardware
        hardware = new Hardware(hardwareMap);

        // OpenCV camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        propBlobDetection = new PropBlobDetection(color);
        camera.setPipeline(propBlobDetection);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(544, 288, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    // move forward to bring arm over the spikestrip
//    setAllWheelMotorPower(0.6);
//    setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
//    trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
//    setAllWheelMotorPower(0);
    public final void trackTelemetryWhileNotIsStarted() {
        while (!isStarted()) {
            telemetry.addData("Prop Position: ", propBlobDetection.getPosition());
            telemetry.addData("Path", "Moving");
            telemetry.addData("fr ticks", hardware.rightFront.getCurrentPosition());
            telemetry.addData("fl ticks", hardware.leftFront.getCurrentPosition());
            telemetry.addData("br ticks", hardware.rightBack.getCurrentPosition());
            telemetry.addData("bl ticks", hardware.leftBack.getCurrentPosition());
//            telemetry.addData("arm ticks", hardware.armMotor.getCurrentPosition());
            telemetry.addData("elevator ticks", hardware.elevatorMotor.getCurrentPosition());
            telemetry.update();
        }
    }
    public final void setAllWheelMotorTargetPositionTolerance(int tolerance) {
        // Set motor tolerance on wheels (allows less precision but less pausing/thinking)
        hardware.rightFront.setTargetPositionTolerance(tolerance);
        hardware.rightBack.setTargetPositionTolerance(tolerance);
        hardware.leftFront.setTargetPositionTolerance(tolerance);
        hardware.leftBack.setTargetPositionTolerance(tolerance);
    }
    private void trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy() {
        while (hardware.rightFront.isBusy() || hardware.leftFront.isBusy() || hardware.rightBack.isBusy() || hardware.leftBack.isBusy()) {
            telemetry.addData("Path", "Moving");
            telemetry.addData("fr ticks", hardware.rightFront.getCurrentPosition());
            telemetry.addData("fl ticks", hardware.leftFront.getCurrentPosition());
            telemetry.addData("br ticks", hardware.rightBack.getCurrentPosition());
            telemetry.addData("bl ticks", hardware.leftBack.getCurrentPosition());
            telemetry.update();
        }
    }
    private void setAllWheelMotorPower(double power) {
        hardware.rightFront.setPower(power);
        hardware.leftFront.setPower(power);
        hardware.rightBack.setPower(power);
        hardware.leftBack.setPower(power);
    }
    private void setAllWheelMotorMode(DcMotor.RunMode mode) {
        hardware.rightFront.setMode(mode);
        hardware.leftFront.setMode(mode);
        hardware.rightBack.setMode(mode);
        hardware.leftBack.setMode(mode);
    }
    public void stopAndResetAllWheelEncoders() {
        hardware.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    public final void strafeLeft(int targetPosition, double power) {
        hardware.rightFront.setTargetPosition(hardware.rightFront.getCurrentPosition()+targetPosition);
        hardware.rightBack.setTargetPosition(hardware.rightBack.getCurrentPosition()-targetPosition);
        hardware.leftFront.setTargetPosition(hardware.leftFront.getCurrentPosition()-targetPosition);
        hardware.leftBack.setTargetPosition(hardware.leftBack.getCurrentPosition()+targetPosition);
        setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setAllWheelMotorPower(power);
        trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
        setAllWheelMotorPower(0);
    }
    public final void strafeRight(int targetPosition, double power) {
        hardware.rightFront.setTargetPosition(hardware.rightFront.getCurrentPosition()-targetPosition);
        hardware.rightBack.setTargetPosition(hardware.rightBack.getCurrentPosition()+targetPosition);
        hardware.leftFront.setTargetPosition(hardware.leftFront.getCurrentPosition()+targetPosition);
        hardware.leftBack.setTargetPosition(hardware.leftBack.getCurrentPosition()-targetPosition);
        setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setAllWheelMotorPower(power);
        trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
        setAllWheelMotorPower(0);
    }
    public final void rotateLeft(int targetPosition, double power) {
        hardware.rightFront.setTargetPosition(hardware.rightFront.getCurrentPosition()+targetPosition);
        hardware.rightBack.setTargetPosition(hardware.rightBack.getCurrentPosition()+targetPosition);
        hardware.leftFront.setTargetPosition(hardware.leftFront.getCurrentPosition()-targetPosition);
        hardware.leftBack.setTargetPosition(hardware.leftBack.getCurrentPosition()-targetPosition);
        setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setAllWheelMotorPower(power);
        trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
        setAllWheelMotorPower(0);
    }
    public final void rotateRight(int targetPosition, double power) {
        hardware.rightFront.setTargetPosition(hardware.rightFront.getCurrentPosition()-targetPosition);
        hardware.rightBack.setTargetPosition(hardware.rightBack.getCurrentPosition()-targetPosition);
        hardware.leftFront.setTargetPosition(hardware.leftFront.getCurrentPosition()+targetPosition);
        hardware.leftBack.setTargetPosition(hardware.leftBack.getCurrentPosition()+targetPosition);
        setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setAllWheelMotorPower(power);
        trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
        setAllWheelMotorPower(0);
    }
    public final void moveForward(int targetPosition, double power) {
        hardware.rightFront.setTargetPosition(hardware.rightFront.getCurrentPosition()+targetPosition);
        hardware.rightBack.setTargetPosition(hardware.rightBack.getCurrentPosition()+targetPosition);
        hardware.leftFront.setTargetPosition(hardware.leftFront.getCurrentPosition()+targetPosition);
        hardware.leftBack.setTargetPosition(hardware.leftBack.getCurrentPosition()+targetPosition);
        setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setAllWheelMotorPower(power);
        trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
        setAllWheelMotorPower(0);
    }
    public final void moveBackward(int targetPosition, double power) {
        hardware.rightFront.setTargetPosition(hardware.rightFront.getCurrentPosition()-targetPosition);
        hardware.rightBack.setTargetPosition(hardware.rightBack.getCurrentPosition()-targetPosition);
        hardware.leftFront.setTargetPosition(hardware.leftFront.getCurrentPosition()-targetPosition);
        hardware.leftBack.setTargetPosition(hardware.leftBack.getCurrentPosition()-targetPosition);
        setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setAllWheelMotorPower(power);
        trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
        setAllWheelMotorPower(0);
    }
}