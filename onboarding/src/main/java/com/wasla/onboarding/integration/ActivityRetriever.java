package com.wasla.onboarding.integration;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.functional.curry.RxFunction;

/**
 * a function that retrieves an Activity from a {@link ViewGroup}
 */
class ActivityRetriever implements RxFunction<View, FragmentActivity> {
    @Override
    public FragmentActivity apply(View viewGroup) {
        return (FragmentActivity) viewGroup.getContext();
    }
}
