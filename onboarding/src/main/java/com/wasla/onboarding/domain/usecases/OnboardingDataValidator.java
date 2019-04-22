package com.wasla.onboarding.domain.usecases;

import com.functional.curry.RxPredicate;
import com.wasla.onboarding.entities.OnboardingData;

public class OnboardingDataValidator implements RxPredicate<OnboardingData> {

    @Override
    public boolean test(OnboardingData data) {
        return data.firstName != null && !data.firstName.isEmpty()
                && data.lastName != null && !data.lastName.isEmpty()
                && data.middleName != null && !data.middleName.isEmpty()
                && data.phoneNumber != null && !data.phoneNumber.isEmpty();
    }
}
