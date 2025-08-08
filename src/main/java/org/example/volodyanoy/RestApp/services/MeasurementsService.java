package org.example.volodyanoy.RestApp.services;


import org.example.volodyanoy.RestApp.dto.MeasurementDTO;
import org.example.volodyanoy.RestApp.dto.RainyDaysCountDTO;
import org.example.volodyanoy.RestApp.models.Measurement;
import org.example.volodyanoy.RestApp.models.Sensor;
import org.example.volodyanoy.RestApp.repositories.MeasurementsRepository;
import org.example.volodyanoy.RestApp.util.MeasurementNotAddedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService, ModelMapper modelMapper) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public Measurement findOne(int id){
        Optional<Measurement> measurement = measurementsRepository.findById(id);
        return measurement.orElse(null);
    }


    @Transactional
    public void save(Measurement measurement){

        Optional<Sensor> sensor = sensorsService.findByName(measurement.getSensor());
        if(sensor.isPresent()){
            measurement.setSensor(sensor.get());
        }
        else {
            throw new MeasurementNotAddedException("This sensor is not exist");
        }
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }

    @Transactional
    public void update(int id, Measurement updatedMeasurement){
        updatedMeasurement.setId(id);
        measurementsRepository.save(updatedMeasurement);
    }

    @Transactional
    public void delete(int id){
        measurementsRepository.deleteById(id);
    }

    public RainyDaysCountDTO countRainyDays(){
        RainyDaysCountDTO rainyDaysCountDTO = new RainyDaysCountDTO();
        rainyDaysCountDTO.setCount(measurementsRepository.countByRainingTrue());
        return rainyDaysCountDTO;
    }

    public Measurement convertToMeasurement(MeasurementDTO MeasurementDTO){
        return modelMapper.map(MeasurementDTO, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }



}
