package com.interview.service;

import com.interview.exception.AthleteNotFoundException;
import com.interview.model.Athlete;
import com.interview.repository.AthleteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AthleteServiceImpl implements AthleteService {

    private final AthleteRepository repository;

    @Override
    public List<Athlete> findAll() {
        return repository.findAll();
    }

    @Override
    public Athlete findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AthleteNotFoundException(id));
    }

    @Override
    public Athlete save(Athlete athlete) {
        return repository.save(athlete);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
