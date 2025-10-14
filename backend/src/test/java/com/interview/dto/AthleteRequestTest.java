package com.interview.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AthleteRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidAthleteRequest() {
        AthleteRequest request =
                new AthleteRequest("John", "Doe", 1000000000L, "USA", "100m Sprint", "9.99s", "Fast runner");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailWhenFirstNameIsBlank() {
        AthleteRequest request =
                new AthleteRequest("", "Doe", 1000000000L, "USA", "100m Sprint", "9.99s", "Fast runner");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("First name is required");
    }

    @Test
    void shouldFailWhenLastNameIsBlank() {
        AthleteRequest request =
                new AthleteRequest("John", "", 1000000000L, "USA", "100m Sprint", "9.99s", "Fast runner");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Last name is required");
    }

    @Test
    void shouldFailWhenBirthTimestampIsNull() {
        AthleteRequest request = new AthleteRequest("John", "Doe", null, "USA", "100m Sprint", "9.99s", "Fast runner");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Birth timestamp is required");
    }

    @Test
    void shouldFailWhenBirthTimestampIsNegative() {
        AthleteRequest request = new AthleteRequest("John", "Doe", -1L, "USA", "100m Sprint", "9.99s", "Fast runner");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Birth timestamp must be a positive number");
    }

    @Test
    void shouldFailWhenFirstNameExceedsMaxLength() {
        AthleteRequest request = new AthleteRequest(
                "ThisNameIsWayTooLongAndExceedsTheThirtyCharacterLimit",
                "Doe",
                1000000000L,
                "USA",
                "100m Sprint",
                "9.99s",
                "Fast runner");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("First name must not exceed 30 characters");
    }

    @Test
    void shouldAllowNullPersonalBest() {
        AthleteRequest request = new AthleteRequest("John", "Doe", 1000000000L, "USA", "100m Sprint", null, "Bio");

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldAllowNullBio() {
        AthleteRequest request = new AthleteRequest("John", "Doe", 1000000000L, "USA", "100m Sprint", "9.99s", null);

        Set<ConstraintViolation<AthleteRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }
}
