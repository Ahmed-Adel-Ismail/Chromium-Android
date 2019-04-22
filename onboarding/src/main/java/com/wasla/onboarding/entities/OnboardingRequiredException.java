package com.wasla.onboarding.entities;

public class OnboardingRequiredException extends RuntimeException {
    public OnboardingRequiredException() {
        super("onboarding required");
    }
}
