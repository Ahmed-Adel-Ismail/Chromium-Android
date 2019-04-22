package com.wasla.onboarding.presentation;

import android.arch.lifecycle.ViewModel;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class OnboardingViewModel extends ViewModel {

    final BehaviorSubject<Boolean> progress = BehaviorSubject.createDefault(false);
    final BehaviorSubject<Integer> pageIndex = BehaviorSubject.createDefault(0);
    final PublishSubject<Throwable> errorMessage = PublishSubject.create();
    final BehaviorSubject<String> firstName = BehaviorSubject.create();
    final BehaviorSubject<String> middleName = BehaviorSubject.create();
    final BehaviorSubject<String> lastName = BehaviorSubject.create();
    final BehaviorSubject<String> phoneNumber = BehaviorSubject.create();


    @Override
    protected void onCleared() {
        super.onCleared();
        pageIndex.onComplete();
        progress.onComplete();
        errorMessage.onComplete();
        firstName.onComplete();
        middleName.onComplete();
        lastName.onComplete();
        phoneNumber.onComplete();


    }
}
