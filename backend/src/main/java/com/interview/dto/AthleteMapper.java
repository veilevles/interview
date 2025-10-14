package com.interview.dto;

import com.interview.model.Athlete;

/**
 * Mapper for converting between Athlete domain model and DTOs.
 */
public class AthleteMapper {

    /**
     * Converts request DTO to domain model.
     *
     * @param request the request DTO
     * @return domain model
     */
    public static Athlete toDomain(final AthleteRequest request) {
        return Athlete.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthTimestamp(request.getBirthTimestamp())
                .nationality(request.getNationality())
                .discipline(request.getDiscipline())
                .personalBest(request.getPersonalBest())
                .bio(request.getBio())
                .build();
    }

    /**
     * Converts domain model to response DTO.
     *
     * @param athlete the domain model
     * @return response DTO
     */
    public static AthleteResponse toResponse(final Athlete athlete) {
        return AthleteResponse.builder()
                .id(athlete.getId())
                .firstName(athlete.getFirstName())
                .lastName(athlete.getLastName())
                .birthTimestamp(athlete.getBirthTimestamp())
                .nationality(athlete.getNationality())
                .discipline(athlete.getDiscipline())
                .personalBest(athlete.getPersonalBest())
                .bio(athlete.getBio())
                .build();
    }

    /**
     * Updates an existing domain model with data from request DTO.
     *
     * @param athlete the existing domain model to update
     * @param request the request DTO with new data
     */
    public static void updateFromRequest(final Athlete athlete, final AthleteRequest request) {
        athlete.setFirstName(request.getFirstName());
        athlete.setLastName(request.getLastName());
        athlete.setBirthTimestamp(request.getBirthTimestamp());
        athlete.setNationality(request.getNationality());
        athlete.setDiscipline(request.getDiscipline());
        athlete.setPersonalBest(request.getPersonalBest());
        athlete.setBio(request.getBio());
    }
}