package org.example.volodyanoy.RestApp.controllers;


import jakarta.validation.Valid;
import org.example.volodyanoy.RestApp.dto.SensorDTO;
import org.example.volodyanoy.RestApp.models.Sensor;
import org.example.volodyanoy.RestApp.services.SensorsService;
import org.example.volodyanoy.RestApp.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsRESTController {

    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;

    public SensorsRESTController(SensorsService sensorsService, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensor = sensorsService.convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorsService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(SensorNotCreatedException.class)
    private ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
