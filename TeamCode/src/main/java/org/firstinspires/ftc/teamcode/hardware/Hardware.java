package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@SuppressWarnings("unused")
public class Hardware {
    public Servo leftDifferential;
    public Servo rightDifferential;
    public Servo leftFourbar;
    public Servo rightFourbar;
    public Servo pincerLeft;
    public Servo pincerRight;
    public DcMotorEx leftBack;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx rightFront;
    public DcMotorEx intakeRoller;
    public DcMotorEx leftIntakeExtension;
    public DcMotorEx rightIntakeExtension;
    public DcMotorEx outtakeExtension;

    public Hardware(HardwareMap hardwareMap) {
        // servos
        leftDifferential = hardwareMap.get(Servo.class, "leftDiff");
        rightDifferential = hardwareMap.get(Servo.class, "rightDiff");
        leftFourbar = hardwareMap.get(Servo.class, "leftFourbar");
        rightFourbar = hardwareMap.get(Servo.class, "rightFourbar");
        pincerLeft = hardwareMap.get(Servo.class, "pincerLeft");
        pincerRight = hardwareMap.get(Servo.class, "pincerLeft");

        // motors
        intakeRoller = hardwareMap.get(DcMotorEx.class, "intakeRoller");
        leftIntakeExtension = hardwareMap.get(DcMotorEx.class, "leftIntakeExtension");
        rightIntakeExtension = hardwareMap.get(DcMotorEx.class, "rightIntakeExtension");
        outtakeExtension = hardwareMap.get(DcMotorEx.class, "outtakeExtension");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        // direction
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeExtension.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeExtension.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}