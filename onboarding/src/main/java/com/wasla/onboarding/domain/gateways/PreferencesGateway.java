package com.wasla.onboarding.domain.gateways;

import com.wasla.onboarding.entities.OnboardingData;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * for the sake of simplicity, Preferences will be used to store the current onboarding state
 * of the user
 * <p>
 * as the task is not concerned about the on-boarding details, the objects will be saved as
 * a String in the preferences, but for production, this should be either on server, or in a
 * database with encrypted conversion (if required)
 */
public interface PreferencesGateway {

    Maybe<OnboardingData> loadOnboardingData();

    Completable saveOnboardingData(OnboardingData data);

}

