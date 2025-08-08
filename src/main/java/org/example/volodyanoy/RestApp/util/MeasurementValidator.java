package org.example.volodyanoy.RestApp.util;

import org.example.volodyanoy.RestApp.models.Measurement;
import org.example.volodyanoy.RestApp.models.Sensor;
import org.example.volodyanoy.RestApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;

        Sensor sensor = measurement.getSensor();

        if(sensor == null || sensorsService.findByName(sensor).isEmpty()){
            errors.rejectValue("sensor", "", "This sensor is not exist");
        }

    }

}
