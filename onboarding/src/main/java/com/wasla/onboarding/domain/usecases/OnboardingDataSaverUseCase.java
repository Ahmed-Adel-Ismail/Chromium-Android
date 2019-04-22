package com.wasla.onboarding.domain.usecases;

import android.annotation.SuppressLint;

import com.functional.curry.RxBiFunction;
import com.functional.curry.RxFunction3;
import com.functional.curry.RxPredicate;
import com.wasla.onboarding.domain.repositories.OnboardingRepository;
import com.wasla.onboarding.entities.InternalSdk;
import com.wasla.onboarding.entities.InvalidOnboardingDataException;
import com.wasla.onboarding.entities.OnboardingData;

import androidx.annotation.RestrictTo;
import io.reactivex.Maybe;
import io.reactivex.subjects.BehaviorSubject;

@InternalSdk
public class OnboardingDataSaverUseCase implements
        RxFunction3<OnboardingRepository, BehaviorSubject<Boolean>, OnboardingData, Maybe<OnboardingData>> {

    private final RxPredicate<OnboardingData> onboardingDataValidator;


    @SuppressLint("RestrictedApi")
    public OnboardingDataSaverUseCase() {
        this(new OnboardingDataValidator());
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    OnboardingDataSaverUseCase(RxPredicate<OnboardingData> onboardingDataValidator) {
        this.onboardingDataValidator = onboardingDataValidator;
    }

    @Override
    public RxBiFunction<BehaviorSubject<Boolean>, OnboardingData, Maybe<OnboardingData>> apply(OnboardingRepository repository) {
        return progress -> data -> {

            if (progress.getValue()) return Maybe.empty();

            if (!onboardingDataValidator.test(data))
                return Maybe.error(new InvalidOnboardingDataException());

            progress.onNext(true);

            return repository.saveOnboardingData(data)
                    .toSingleDefault(data)
                    .doFinally(() -> progress.onNext(false))
                    .toMaybe();
        };
    }
}
