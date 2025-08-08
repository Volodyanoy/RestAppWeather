package org.example.volodyanoy.RestApp.util;

public class MeasurementNotAddedException extends RuntimeException {
    public MeasurementNotAddedException(String message) {
        super(message);
    }
}
