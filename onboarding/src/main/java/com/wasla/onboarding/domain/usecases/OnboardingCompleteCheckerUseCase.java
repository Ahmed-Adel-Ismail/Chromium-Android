package com.wasla.onboarding.domain.usecases;

import com.functional.curry.RxFunction;
import com.wasla.onboarding.domain.repositories.OnboardingRepository;
import com.wasla.onboarding.entities.InternalSdk;
import com.wasla.onboarding.entities.OnboardingRequiredException;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;

@InternalSdk
public class OnboardingCompleteCheckerUseCase implements RxFunction<OnboardingRepository, Completable> {


    @Override
    public Completable apply(OnboardingRepository repository) {
        return repository
                .loadOnboardingData()
                .map(ignoredData -> true)
                .toSingle(false)
                .flatMapCompletable(this::toCompletable);

    }

    private CompletableSource toCompletable(Boolean completed) {
        if (completed) return Completable.complete();
        else return Completable.error(new OnboardingRequiredException());
    }
}
