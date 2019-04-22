package com.wasla.onboarding.integration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wasla.onboarding.domain.gateways.PreferencesGatewayImplementer;
import com.wasla.onboarding.domain.repositories.OnboardingRepositoryImplementer;
import com.wasla.onboarding.domain.usecases.OnboardingCompleteCheckerUseCase;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * a class that represents the integration point with the on-boarding feature
 */
public class Onboarding {

    /**
     * check if the on-boarding is complete or not
     *
     * @param context the {@link Context} to be used
     * @return a {@link Completable} that emits {@code onComplete()} if the on-boarding is complete,
     * or {@code onError()} if not
     */
    public static Completable onComplete(Context context) {
        return Single.just(context)
                .map(PreferencesGatewayImplementer::new)
                .map(OnboardingRepositoryImplementer::new)
                .flatMapCompletable(new OnboardingCompleteCheckerUseCase());
    }

    /**
     * attach the Onboarding fragment into the passed {@link ViewGroup}
     *
     * @param viewGroup the {@link ViewGroup} that will hold the onboarding UI
     * @return Disposable a disposable that should be disposed in {@code onDestroy()}
     * @throws ClassCastException if the passed parameter is not a {@link ViewGroup}
     */
    public static Disposable into(@NonNull View viewGroup) throws ClassCastException {
        return new FragmentReplacer()
                .apply(new ActivityRetriever())
                .apply(new FragmentTransaction())
                .apply(viewGroup);
    }

}





