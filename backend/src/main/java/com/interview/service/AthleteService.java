package com.interview.service;

import com.interview.model.Athlete;
import java.util.List;

public interface AthleteService {
    List<Athlete> findAll();

    Athlete findById(Long id);

    Athlete save(Athlete athlete);

    void deleteById(Long id);
}
