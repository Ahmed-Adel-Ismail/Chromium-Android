package com.wasla.onboarding.integration;

import android.arch.lifecycle.ViewModel;

import com.wasla.onboarding.entities.InternalSdk;
import com.wasla.onboarding.presentation.OnboardingFragment;

/**
 * a holder for the fragment to retain the same instance across rotations,
 * this pattern is to re-attach the same fragment across rotations of an Activity
 */
@InternalSdk
@SuppressWarnings("WeakerAccess")
public class OnboardingHolder extends ViewModel {
    private final OnboardingFragment fragment = new OnboardingFragment();

    OnboardingFragment getFragment() {
        return fragment;
    }
}

