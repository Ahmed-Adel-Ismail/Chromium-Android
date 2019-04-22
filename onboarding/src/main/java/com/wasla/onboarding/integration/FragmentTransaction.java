package com.wasla.onboarding.integration;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.functional.curry.RxBiConsumer;
import com.functional.curry.RxConsumer3;
import com.wasla.onboarding.presentation.OnboardingFragment;

/**
 * a function that executes a transaction onto the {@link FragmentManager}
 */
class FragmentTransaction implements RxConsumer3<ActivityRetriever, View, OnboardingFragment> {
    @Override
    public RxBiConsumer<View, OnboardingFragment> apply(ActivityRetriever activityRetriever) {
        return viewGroup -> fragment -> activityRetriever.apply(viewGroup)
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(viewGroup.getId(), fragment, Onboarding.FRAGMENT_TAG)
                .commitNowAllowingStateLoss();
    }
}
