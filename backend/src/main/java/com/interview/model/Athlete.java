package com.interview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an athlete participating in a track and field event.
 * <p>
 * This entity holds basic personal and performance-related information
 * such as name, nationality, discipline, and personal best record.
 * </p>
 *
 * Used for storing and retrieving athlete data from the database.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 30, message = "First name must not exceed 30 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 30, message = "Last name must not exceed 30 characters")
    private String lastName;

    @NotNull(message = "Birth timestamp is required")
    @Positive(message = "Birth timestamp must be a positive number")
    private Long birthTimestamp;

    @NotBlank(message = "Nationality is required")
    @Size(max = 50, message = "Nationality must not exceed 50 characters")
    private String nationality;

    @NotBlank(message = "Discipline is required")
    @Size(max = 100, message = "Discipline must not exceed 100 characters")
    private String discipline;

    @Size(max = 20, message = "Personal best must not exceed 20 characters")
    private String personalBest;

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;
}
