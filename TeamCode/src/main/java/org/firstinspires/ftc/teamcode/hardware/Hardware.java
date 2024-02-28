package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
public class Hardware {
    public Servo leftClawServo;
    public Servo rightClawServo;
    public Servo verticalServo;
    public DcMotorEx leftBack;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx rightFront;
    public DcMotorEx elevatorMotor;
    public DcMotorEx leftArmMotor;
    public DcMotorEx rightArmMotor;
    public DcMotorEx climbMotor;

    public Hardware(HardwareMap hardwareMap) {
        //servo
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
        verticalServo = hardwareMap.get(Servo.class, "verticalServo");
        // motor
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        leftArmMotor = hardwareMap.get(DcMotorEx.class, "leftArmMotor");
        rightArmMotor = hardwareMap.get(DcMotorEx.class, "rightArmMotor");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArmMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    final double LEFT_CLAW_OPEN = 0;
    final double LEFT_CLAW_CLOSE = 1;
    final double RIGHT_CLAW_OPEN = 1;
    final double RIGHT_CLAW_CLOSE = 0;
    final double CLAW_ROTATE_UP = 1;
    final double CLAW_ROTATE_DOWN = 0;

    public void closeClaw() {
        rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
        leftClawServo.setPosition(LEFT_CLAW_CLOSE);
    }

    public void openClaw() {
        rightClawServo.setPosition(RIGHT_CLAW_OPEN);
        leftClawServo.setPosition(LEFT_CLAW_OPEN);
    }
}