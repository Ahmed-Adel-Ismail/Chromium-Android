package com.wasla.onboarding.integration;

import android.arch.lifecycle.ViewModelProviders;
import android.view.View;
import android.view.ViewGroup;

import com.functional.curry.RxBiFunction;
import com.functional.curry.RxFunction3;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

/**
 * a function that attaches a fragment into a {@link ViewGroup}
 */
class FragmentReplacer implements RxFunction3<ActivityRetriever, FragmentTransaction, View, Disposable> {


    @Override
    public RxBiFunction<FragmentTransaction, View, Disposable> apply(ActivityRetriever retriever) {
        return transaction -> viewGroup -> Single.just(viewGroup)
                .map(retriever)
                .map(ViewModelProviders::of)
                .map(provider -> provider.get(OnboardingHolder.class))
                .map(OnboardingHolder::getFragment)
                .subscribe(transaction.apply(retriever).apply(viewGroup),Throwable::printStackTrace);
    }

}
