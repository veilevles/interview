package com.interview.exception;

/**
 * Thrown when athlete data violates custom business validation rules.
 */
public class InvalidAthleteDataException extends RuntimeException {
    public InvalidAthleteDataException(String reason) {
        super("Invalid athlete data: " + reason);
    }
}
