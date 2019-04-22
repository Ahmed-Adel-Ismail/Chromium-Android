package com.wasla.onboarding.domain.usecases;

import com.wasla.onboarding.entities.OnboardingData;

import org.junit.Test;

import static org.junit.Assert.*;

public class OnboardingDataValidatorTest {

    @Test
    public void testWithNonNullDataThenReturnTrue() {
        OnboardingData data = new OnboardingData("a", "b", "c", "d");
        boolean result = new OnboardingDataValidator().test(data);
        assertTrue(result);
    }

    @Test
    public void testWithNullDataThenReturnFalse() {
        OnboardingData data = new OnboardingData(null, null, null, null);
        boolean result = new OnboardingDataValidator().test(data);
        assertFalse(result);
    }

    @Test
    public void testWithEmptyDataThenReturnFalse() {
        OnboardingData data = new OnboardingData("", "", "", "");
        boolean result = new OnboardingDataValidator().test(data);
        assertFalse(result);
    }

}