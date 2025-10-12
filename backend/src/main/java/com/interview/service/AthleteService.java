package com.interview.service;

import com.interview.model.Athlete;
import java.util.List;
import java.util.Optional;

public interface AthleteService {
    List<Athlete> findAll();
    Optional<Athlete> findById(Long id);
    Athlete save(Athlete athlete);
    void deleteById(Long id);
}
