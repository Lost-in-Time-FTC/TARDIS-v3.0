package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
public class Hardware {
    public CRServo topDifferential;
    public CRServo bottomDifferential;

    public Hardware(HardwareMap hardwareMap) {
        topDifferential = hardwareMap.get(CRServo.class, "topDiff");
        bottomDifferential = hardwareMap.get(CRServo.class, "bottomDiff");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
    }
}