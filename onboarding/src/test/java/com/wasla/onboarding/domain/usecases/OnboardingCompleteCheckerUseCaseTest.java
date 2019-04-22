package com.wasla.onboarding.domain.usecases;

import com.wasla.onboarding.domain.repositories.OnboardingRepository;
import com.wasla.onboarding.entities.OnboardingData;

import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.Maybe;

import static org.junit.Assert.*;

public class OnboardingCompleteCheckerUseCaseTest {


    @Test
    public void applyWithStoredDataThenReturnCompletedCompletable() {

        OnboardingRepository repository = new SuccessfulRepo();

        Throwable error = new OnboardingCompleteCheckerUseCase()
                .apply(repository)
                .blockingGet();

        assertNull(error);
    }

    @Test
    public void applyWithNoStoredDataThenReturnErrorCompletable() {

        OnboardingRepository repository = new FailingRepo();

        Throwable error = new OnboardingCompleteCheckerUseCase()
                .apply(repository)
                .blockingGet();

        assertNotNull(error);

    }


    private OnboardingData data() {
        return new OnboardingData("a", "b", "c", "d");
    }


    class SuccessfulRepo implements OnboardingRepository {
        @Override
        public Maybe<OnboardingData> loadOnboardingData() {
            return Maybe.just(data());
        }

        @Override
        public Completable saveOnboardingData(OnboardingData data) {
            return Completable.complete();
        }
    }


    class FailingRepo implements OnboardingRepository {
        @Override
        public Maybe<OnboardingData> loadOnboardingData() {
            return Maybe.empty();
        }

        @Override
        public Completable saveOnboardingData(OnboardingData data) {
            return Completable.complete();
        }
    }


}
