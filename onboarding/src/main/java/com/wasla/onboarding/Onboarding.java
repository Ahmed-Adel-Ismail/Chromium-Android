package com.wasla.onboarding;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.functional.curry.RxBiConsumer;
import com.functional.curry.RxBiFunction;
import com.functional.curry.RxConsumer3;
import com.functional.curry.RxFunction;
import com.functional.curry.RxFunction3;
import com.wasla.onboarding.domain.gateways.PreferencesGatewayImplementer;
import com.wasla.onboarding.domain.repositories.OnboardingRepositoryImplementer;
import com.wasla.onboarding.domain.usecases.OnboardingCompleteCheckerUseCase;
import com.wasla.onboarding.presentation.OnboardingFragment;

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


/**
 * a holder for the fragment to retain the same instance across rotations,
 * this pattern is to re-attach the same fragment across rotations of an Activity
 */
class OnboardingHolder extends ViewModel {
    private final OnboardingFragment fragment = new OnboardingFragment();

    OnboardingFragment getFragment() {
        return fragment;
    }
}

/**
 * a functional class that attaches a fragment into a {@link ViewGroup}
 */
class FragmentReplacer implements RxFunction3<ActivityRetriever, FragmentTransaction, View, Disposable> {

    @Override
    public RxBiFunction<FragmentTransaction, View, Disposable> apply(ActivityRetriever retriever) {
        return transaction -> viewGroup -> Single.just(viewGroup)
                .map(retriever)
                .map(ViewModelProviders::of)
                .map(provider -> provider.get(OnboardingHolder.class))
                .map(OnboardingHolder::getFragment)
                .subscribe(transaction.apply(retriever).apply(viewGroup), Throwable::printStackTrace);
    }

}

/**
 * a functional class that retrieves an Activity from a {@link ViewGroup}
 */
class ActivityRetriever implements RxFunction<View, FragmentActivity> {
    @Override
    public FragmentActivity apply(View viewGroup) {
        return (FragmentActivity) viewGroup.getContext();
    }
}

/**
 * a functional class that executes a transaction onto the {@link FragmentManager}
 */
class FragmentTransaction implements RxConsumer3<ActivityRetriever, View, OnboardingFragment> {
    @Override
    public RxBiConsumer<View, OnboardingFragment> apply(ActivityRetriever activityRetriever) {
        return viewGroup -> fragment -> activityRetriever.apply(viewGroup)
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(viewGroup.getId(), fragment)
                .commitAllowingStateLoss();
    }
}





