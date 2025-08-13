package org.example.volodyanoy.RestApp.controllers;

import jakarta.validation.Valid;
import org.example.volodyanoy.RestApp.dto.MeasurementDTO;
import org.example.volodyanoy.RestApp.dto.RainyDaysCountDTO;
import org.example.volodyanoy.RestApp.models.Measurement;

import org.example.volodyanoy.RestApp.services.MeasurementsService;
import org.example.volodyanoy.RestApp.services.SensorsService;
import org.example.volodyanoy.RestApp.util.MeasurementNotAddedException;
import org.example.volodyanoy.RestApp.util.ErrorResponse;

import org.example.volodyanoy.RestApp.util.MeasurementValidator;
import org.example.volodyanoy.RestApp.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsRESTController {
    

    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final SensorsService sensorsService;

    public MeasurementsRESTController(MeasurementsService measurementsService, MeasurementValidator measurementValidator, SensorsService sensorsService) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.sensorsService = sensorsService;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(){
        return  measurementsService.findAll().stream().map(measurementsService::convertToMeasurementDTO).toList();

    }

    @GetMapping("/rainyDaysCount")
    public RainyDaysCountDTO getCountRainyDays(){
        return measurementsService.countRainyDays();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        Measurement measurement = measurementsService.convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotAddedException(errorMsg.toString());
        }

        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
      
        
    }


    @ExceptionHandler(MeasurementNotAddedException.class)
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotAddedException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
