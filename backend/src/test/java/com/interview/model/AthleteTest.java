package com.interview.model;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AthleteTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Valid athlete passes all validations")
    void validAthlete() {
        final var athlete = defaultAthlete().build();
        Set<ConstraintViolation<Athlete>> violations = validator.validate(athlete);
        assertThat(violations).isEmpty();
    }

    @Test
    void blankFirstName() {
        final var athlete = defaultAthlete().firstName(" ").build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("First name is required"));
    }

    @Test
    void longFirstName() {
        final var athlete = defaultAthlete().firstName("A".repeat(31)).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("First name must not exceed 30 characters"));
    }

    @Test
    void blankLastName() {
        final var athlete = defaultAthlete().lastName("").build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Last name is required"));
    }

    @Test
    void longLastName() {
        final var athlete = defaultAthlete().lastName("B".repeat(31)).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Last name must not exceed 30 characters"));
    }

    @Test
    void nullBirthTimestamp() {
        final var athlete = defaultAthlete().birthTimestamp(null).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Birth timestamp is required"));
    }

    @Test
    void negativeBirthTimestamp() {
        final var athlete = defaultAthlete().birthTimestamp(-100L).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Birth timestamp must be a positive number"));
    }

    @Test
    void blankNationality() {
        final var athlete = defaultAthlete().nationality(" ").build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Nationality is required"));
    }

    @Test
    void longNationality() {
        final var athlete = defaultAthlete().nationality("C".repeat(51)).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Nationality must not exceed 50 characters"));
    }

    @Test
    void blankDiscipline() {
        final var athlete = defaultAthlete().discipline("").build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Discipline is required"));
    }

    @Test
    void longDiscipline() {
        final var athlete = defaultAthlete().discipline("D".repeat(101)).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Discipline must not exceed 100 characters"));
    }

    @Test
    void longPersonalBest() {
        final var athlete = defaultAthlete().personalBest("E".repeat(21)).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Personal best must not exceed 20 characters"));
    }

    @Test
    void longBio() {
        final var athlete = defaultAthlete().bio("F".repeat(1001)).build();
        final var violations = validator.validate(athlete);
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Bio must not exceed 1000 characters"));
    }

    private Athlete.AthleteBuilder defaultAthlete() {
        return Athlete.builder()
                .firstName("Usain")
                .lastName("Bolt")
                .birthTimestamp(524966400000L) // 1986-08-21
                .nationality("Jamaica")
                .discipline("100m")
                .personalBest("9.58s")
                .bio("Fastest man alive.");
    }
}
