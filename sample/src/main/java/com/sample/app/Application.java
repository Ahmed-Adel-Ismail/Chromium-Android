package com.sample.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wasla.onboarding.integration.Onboarding;
import com.wasla.onboarding.presentation.OnboardingFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                runOnboarding(activity);
            }

            private void runOnboarding(Activity activity) {
                Onboarding.isCompleted(activity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(() -> Toast.makeText(activity, "completed", Toast.LENGTH_LONG).show())
                        .doOnError(e -> Toast.makeText(activity, "not-complete", Toast.LENGTH_LONG).show())
                        .doOnError(e -> attachOnboardingFragment(activity))
                        .onErrorComplete()
                        .subscribe();
            }

            private void attachOnboardingFragment(Activity activity) {

                ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();

                ViewGroup container = new FrameLayout(activity);
                container.setBackgroundResource(android.R.color.white);
                container.setId(R.id.onboarding_container);

                rootView.addView(container);

                ViewGroup.LayoutParams params = container.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;

                rootView.bringChildToFront(container);

                Onboarding.into(container);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                removeOnboardingFragment((FragmentActivity) activity);
            }

            private void removeOnboardingFragment(FragmentActivity fragmentActivity) {

                OnboardingFragment onboardingFragment = Observable.just(fragmentActivity)
                        .map(FragmentActivity::getSupportFragmentManager)
                        .map(FragmentManager::getFragments)
                        .doOnNext(fragments -> fragments.remove(null))
                        .flatMap(Observable::fromIterable)
                        .filter(fragment -> fragment instanceof OnboardingFragment)
                        .firstElement()
                        .cast(OnboardingFragment.class)
                        .blockingGet();

                if (onboardingFragment == null) return;

                fragmentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .remove(onboardingFragment)
                        .commitAllowingStateLoss();
            }
        });
    }
}
