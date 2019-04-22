package com.wasla.onboarding.domain.repositories;

import com.wasla.onboarding.domain.gateways.PreferencesGateway;
import com.wasla.onboarding.entities.OnboardingData;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class OnboardingRepositoryImplementer implements OnboardingRepository {

    private final PreferencesGateway preferencesGateway;

    public OnboardingRepositoryImplementer(PreferencesGateway preferencesGateway) {
        this.preferencesGateway = preferencesGateway;
    }

    @Override
    public Maybe<OnboardingData> loadOnboardingData() {
        return preferencesGateway.loadOnboardingData();
    }

    @Override
    public Completable saveOnboardingData(OnboardingData data) {
        return preferencesGateway.saveOnboardingData(data);
    }
}
