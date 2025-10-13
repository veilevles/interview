package com.interview.controller;

import com.interview.model.Athlete;
import com.interview.service.AthleteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/athletes")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService service;

    @GetMapping
    public List<Athlete> getAllAthletes() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthleteById(@PathVariable Long id) {
        Athlete athlete = service.findById(id); // Will throw AthleteNotFoundException if not found
        return ResponseEntity.ok(athlete);
    }

    @PostMapping
    public Athlete createAthlete(@RequestBody Athlete athlete) {
        return service.save(athlete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(@PathVariable Long id, @RequestBody Athlete updatedAthlete) {
        Athlete existing = service.findById(id); // Will throw AthleteNotFoundException if not found

        existing.setFirstName(updatedAthlete.getFirstName());
        existing.setLastName(updatedAthlete.getLastName());
        existing.setBirthTimestamp(updatedAthlete.getBirthTimestamp());
        existing.setNationality(updatedAthlete.getNationality());
        existing.setDiscipline(updatedAthlete.getDiscipline());
        existing.setPersonalBest(updatedAthlete.getPersonalBest());
        existing.setBio(updatedAthlete.getBio());

        return ResponseEntity.ok(service.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
