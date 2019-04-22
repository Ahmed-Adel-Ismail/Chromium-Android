package com.wasla.onboarding.domain.repositories;

import com.wasla.onboarding.entities.OnboardingData;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * the repository that is responsible for retrieving entities related to On-boarding
 */
public interface OnboardingRepository {

    Maybe<OnboardingData> loadOnboardingData();

    Completable saveOnboardingData(OnboardingData data);

}
