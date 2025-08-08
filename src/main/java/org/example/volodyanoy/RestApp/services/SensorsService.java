package org.example.volodyanoy.RestApp.services;

import org.example.volodyanoy.RestApp.dto.SensorDTO;
import org.example.volodyanoy.RestApp.models.Sensor;
import org.example.volodyanoy.RestApp.repositories.SensorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }


    public List<Sensor> findAll(){
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id){
        Optional<Sensor> sensor = sensorsRepository.findById(id);
        return sensor.orElse(null);
    }

    public Optional<Sensor> findByName(Sensor sensor){
        return sensorsRepository.findByName(sensor.getName());
    }

    @Transactional
    public void save(Sensor sensor){
        sensorsRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor updatedSensor){
        updatedSensor.setId(id);
        sensorsRepository.save(updatedSensor);
    }

    @Transactional
    public void delete(int id){
        sensorsRepository.deleteById(id);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }


}
