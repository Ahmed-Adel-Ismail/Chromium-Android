package com.wasla.onboarding.domain.gateways;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.wasla.onboarding.entities.OnboardingData;

import io.reactivex.Completable;
import io.reactivex.Maybe;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesGatewayImplementer implements PreferencesGateway {

    private static final String PREFERENCES_NAME = "ONBOARDING_PREFERENCES";
    private static final String ONBOARDING_DATA = "com.wasla.onboarding.domain.ONBOARDING_DATA";
    private final SharedPreferences sharedPreferences;

    public PreferencesGatewayImplementer(Context context) {
        this.sharedPreferences = context
                .getApplicationContext()
                .getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

    }

    @Override
    public Maybe<OnboardingData> loadOnboardingData() {
        final String value = sharedPreferences.getString(ONBOARDING_DATA, null);
        if (value == null) return Maybe.empty();
        return Maybe.just(new Gson().fromJson(value, OnboardingData.class));
    }

    @Override
    public Completable saveOnboardingData(OnboardingData data) {
        return Completable.fromAction(() -> sharedPreferences
                .edit()
                .putString(ONBOARDING_DATA, new Gson().toJson(data))
                .apply());
    }
}
