package org.example.volodyanoy.RestApp.repositories;


import org.example.volodyanoy.RestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    long countByRainingTrue();
}
