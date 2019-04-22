package com.wasla.onboarding.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.functional.curry.RxFunction;
import com.wasla.onboarding.domain.gateways.PreferencesGatewayImplementer;
import com.wasla.onboarding.domain.repositories.OnboardingRepository;
import com.wasla.onboarding.domain.repositories.OnboardingRepositoryImplementer;
import com.wasla.onboarding.entities.InternalSdk;
import com.wasla.onboarding.entities.OnboardingData;

import io.reactivex.subjects.BehaviorSubject;

@InternalSdk
public class OnboardingViewModel extends ViewModel {

    final OnboardingRepository onboardingRepository;
    final BehaviorSubject<Boolean> progress = BehaviorSubject.createDefault(false);
    final BehaviorSubject<String> firstName = BehaviorSubject.create();
    final BehaviorSubject<String> middleName = BehaviorSubject.create();
    final BehaviorSubject<String> lastName = BehaviorSubject.create();
    final BehaviorSubject<String> phoneNumber = BehaviorSubject.create();

    OnboardingViewModel(OnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

    OnboardingData onboardingData() {
        return new OnboardingData(firstName.getValue(), middleName.getValue(),
                lastName.getValue(), phoneNumber.getValue());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        progress.onComplete();
        firstName.onComplete();
        middleName.onComplete();
        lastName.onComplete();
        phoneNumber.onComplete();
    }
}

class OnboardingViewModelFactory implements ViewModelProvider.Factory {

    private final OnboardingRepository repository;

    public OnboardingViewModelFactory(Context context) {
        this.repository = new OnboardingRepositoryImplementer(new PreferencesGatewayImplementer(context));
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OnboardingViewModel(repository);
    }
}

class OnboardingViewModelInjector implements RxFunction<FragmentActivity, OnboardingViewModel> {
    @Override
    public OnboardingViewModel apply(FragmentActivity activity) {
        return ViewModelProviders
                .of(activity, new OnboardingViewModelFactory(activity))
                .get(OnboardingViewModel.class);
    }
}
