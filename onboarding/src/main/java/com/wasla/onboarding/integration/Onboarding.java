package com.wasla.onboarding.integration;

import android.app.Application;
import android.content.Context;

import com.wasla.onboarding.domain.gateways.PreferencesGatewayImplementer;
import com.wasla.onboarding.domain.repositories.OnboardingRepositoryImplementer;
import com.wasla.onboarding.domain.usecases.OnboardingCompleteCheckerUseCase;
import com.wasla.onboarding.entities.InternalSdk;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * a class that represents the integration point with the on-boarding feature
 */
public class Onboarding {



    /**
     * integrate the on-boarding feature with the passed application
     *
     * @param application the {@link Application} class
     */
    @SuppressWarnings({"CheckResult", "ResultOfMethodCallIgnored"})
    public static void integrateWith(Application application) {
        isCompleted(application)
                .subscribe(doNothing(), registerCallbacks(application));
    }

    /**
     * check if the on-boarding is complete or not
     *
     * @param context the {@link Context} to be used
     * @return a {@link Completable} that emits {@code onComplete()} if the on-boarding is complete,
     * or {@code onError()} if not
     */
    static Completable isCompleted(Context context) {
        return Single.just(context)
                .map(PreferencesGatewayImplementer::new)
                .map(OnboardingRepositoryImplementer::new)
                .flatMapCompletable(new OnboardingCompleteCheckerUseCase());
    }

    @NotNull
    private static Action doNothing() {
        return () -> {
        };
    }

    @NotNull
    private static Consumer<Throwable> registerCallbacks(Application application) {
        return ignoredError -> application.registerActivityLifecycleCallbacks(new OnboardingIntegration());
    }

}





