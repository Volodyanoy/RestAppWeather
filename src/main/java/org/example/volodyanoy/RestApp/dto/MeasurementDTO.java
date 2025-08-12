package org.example.volodyanoy.RestApp.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.example.volodyanoy.RestApp.models.Sensor;

public class MeasurementDTO {

    @DecimalMin(value = "-100", inclusive = true, message = "Value should be greater than -100")
    @DecimalMax(value = "100", inclusive = true, message = "Value should be less than 100")
    @NotNull
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
