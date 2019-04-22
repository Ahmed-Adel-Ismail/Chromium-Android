package com.wasla.onboarding.entities;

public class InvalidOnboardingDataException extends RuntimeException {

    public InvalidOnboardingDataException() {
        super("invalid onboarding data");
    }
}
