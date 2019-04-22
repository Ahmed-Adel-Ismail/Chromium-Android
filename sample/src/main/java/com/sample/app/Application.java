package com.sample.app;

import com.wasla.onboarding.integration.Onboarding;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Onboarding.integrateWith(this);
    }
}
