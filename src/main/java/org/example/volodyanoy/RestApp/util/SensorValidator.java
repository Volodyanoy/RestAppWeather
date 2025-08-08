package org.example.volodyanoy.RestApp.util;

import org.example.volodyanoy.RestApp.models.Sensor;
import org.example.volodyanoy.RestApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor= (Sensor) o;

        if(sensorsService.findByName(sensor).isPresent()){
            errors.rejectValue("name", "", "This sensor's name is already taken");
        }

    }
}
