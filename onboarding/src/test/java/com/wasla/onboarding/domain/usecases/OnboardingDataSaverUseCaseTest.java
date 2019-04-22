package com.wasla.onboarding.domain.usecases;

import com.functional.curry.RxPredicate;
import com.wasla.onboarding.domain.repositories.OnboardingRepository;
import com.wasla.onboarding.entities.InvalidOnboardingDataException;
import com.wasla.onboarding.entities.OnboardingData;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.subjects.BehaviorSubject;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class OnboardingDataSaverUseCaseTest {

    @Test
    public void applyWithTrueProgressThenReturnEmptyMaybe() {

        BehaviorSubject<Boolean> progress = BehaviorSubject.createDefault(true);
        OnboardingRepository repository = new SuccessfulRepo();
        RxPredicate<OnboardingData> validator = data -> true;

        OnboardingData result = new OnboardingDataSaverUseCase(validator)
                .apply(repository)
                .apply(progress)
                .apply(data())
                .blockingGet();

        assertNull(result);

    }

    private OnboardingData data() {
        return new OnboardingData("a", "b", "c", "d");
    }

    @Test(expected = InvalidOnboardingDataException.class)
    public void applyWithInvalidDataThenReturnErrorMaybe() {

        BehaviorSubject<Boolean> progress = BehaviorSubject.createDefault(false);
        OnboardingRepository repository = new SuccessfulRepo();
        RxPredicate<OnboardingData> validator = data -> false;

        new OnboardingDataSaverUseCase(validator)
                .apply(repository)
                .apply(progress)
                .apply(data())
                .blockingGet();


    }

    @Test
    public void applyWithValidDataThenUpdateProgressStates() {

        BehaviorSubject<Boolean> progress = BehaviorSubject.createDefault(false);
        OnboardingRepository repository = new SuccessfulRepo();
        RxPredicate<OnboardingData> validator = data -> true;
        List<Boolean> progressStates = new LinkedList<>();

        progress.share().subscribe(progressStates::add);

        new OnboardingDataSaverUseCase(validator)
                .apply(repository)
                .apply(progress)
                .apply(data())
                .blockingGet();

        assertTrue(!progressStates.get(0) && progressStates.get(1) && !progressStates.get(2));


    }

    @Test
    public void applyWithValidDataThenReturnMaybeWithSameData() {

        BehaviorSubject<Boolean> progress = BehaviorSubject.createDefault(false);
        OnboardingRepository repository = new SuccessfulRepo();
        RxPredicate<OnboardingData> validator = data -> true;
        OnboardingData data = data();

        OnboardingData result = new OnboardingDataSaverUseCase(validator)
                .apply(repository)
                .apply(progress)
                .apply(data)
                .blockingGet();

        assertEquals(result, data);

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
}


