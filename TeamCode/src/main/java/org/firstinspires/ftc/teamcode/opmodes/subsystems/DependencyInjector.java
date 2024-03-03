package org.firstinspires.ftc.teamcode.opmodes.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.subsystems.Inject;

import java.lang.reflect.Field;

public class DependencyInjector {
    public static void injectDependencies(Object object, Gamepad gamepad, Hardware hardware) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object dependency = getDependency(field.getType(), gamepad, hardware);
                field.set(object, dependency);
            }
        }
    }

    private static Object getDependency(Class<?> type, Gamepad gamepad, Hardware hardware) {
        // Implement logic to provide the appropriate dependency based on the type
        if (type == Gamepad.class) {
            return gamepad;
        } else if (type == Hardware.class) {
            return hardware;
        }
        // Add more cases as needed for other dependencies
        return null;
    }
}
