// SPDX-License-Identifier: Apache-2.0
package com.interview.controller;

import com.interview.dto.PagedResponse;
import com.interview.model.Athlete;
import com.interview.repository.AthleteSpecification;
import com.interview.service.AthleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for athlete resources.
 */
@RestController
@RequestMapping("/api/v1/athletes")
@RequiredArgsConstructor
@Tag(name = "Athletes", description = "Athlete management API with filtering, pagination, and sorting")
public class AthleteController {

    private static final int MAX_SIZE = 100;
    private static final String DEFAULT_SORT_FIELD = "id";
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "firstName", "lastName", "nationality", "discipline");

    private final AthleteService service;

    @Operation(
            summary = "Get all athletes",
            description = "Retrieves a paginated list of athletes with optional filtering and sorting. "
                    + "Supports filtering by nationality, discipline, and name search.")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved athletes",
            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
    @GetMapping
    public ResponseEntity<?> getAllAthletes(
            @Parameter(description = "Filter by nationality (case-insensitive partial match)", example = "USA")
                    @RequestParam(required = false)
                    String nationality,
            @Parameter(description = "Filter by discipline (case-insensitive partial match)", example = "100m")
                    @RequestParam(required = false)
                    String discipline,
            @Parameter(description = "Search by first or last name (case-insensitive partial match)", example = "bolt")
                    @RequestParam(required = false)
                    String search,
            @Parameter(description = "Page number (0-based)", example = "0")
                    @RequestParam(defaultValue = "0")
                    @PositiveOrZero
                    int page,
            @Parameter(description = "Page size (1–100)", example = "10")
                    @RequestParam(defaultValue = "10")
                    @Positive
                    @Max(MAX_SIZE)
                    int size,
            @Parameter(description = "Sort field", example = "lastName")
                    @RequestParam(defaultValue = DEFAULT_SORT_FIELD)
                    String sortBy,
            @Parameter(description = "Sort direction", example = "ASC") @RequestParam(defaultValue = "ASC")
                    String direction) {

        Specification<Athlete> spec = buildSpecification(nationality, discipline, search);

        // Validate sort field
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : DEFAULT_SORT_FIELD;
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<Athlete> athletePage = service.findAll(spec, pageable);

        return ResponseEntity.ok(toPagedResponse(athletePage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthleteById(@PathVariable Long id) {
        Athlete athlete = service.findById(id); // May throw AthleteNotFoundException
        return ResponseEntity.ok(athlete);
    }

    @PostMapping
    public ResponseEntity<Athlete> createAthlete(@RequestBody Athlete athlete) {
        return ResponseEntity.ok(service.save(athlete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(@PathVariable Long id, @RequestBody Athlete updatedAthlete) {
        Athlete existing = service.findById(id); // May throw AthleteNotFoundException

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

    // --- Private helper methods ---

    private Specification<Athlete> buildSpecification(
            final String nationality, final String discipline, final String search) {
        Specification<Athlete> spec = (root, query, cb) -> cb.conjunction();

        if (nationality != null && !nationality.isBlank()) {
            spec = spec.and(AthleteSpecification.hasNationality(nationality));
        }
        if (discipline != null && !discipline.isBlank()) {
            spec = spec.and(AthleteSpecification.hasDiscipline(discipline));
        }
        if (search != null && !search.isBlank()) {
            spec = spec.and(AthleteSpecification.nameContains(search));
        }

        return spec;
    }

    private PagedResponse<Athlete> toPagedResponse(final Page<Athlete> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
