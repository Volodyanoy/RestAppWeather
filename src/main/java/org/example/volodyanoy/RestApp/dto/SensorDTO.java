package org.example.volodyanoy.RestApp.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.volodyanoy.RestApp.models.Measurement;

import java.util.List;

public class SensorDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be from 3 to 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
