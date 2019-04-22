package com.wasla.onboarding.integration;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.functional.curry.Curry;
import com.wasla.login.R;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wasla.onboarding.presentation.OnboardingFragment.ACTION_FINISH_COMPLETE;

class OnboardingIntegration extends DefaultLifecycleCallbacks {

    private static final String TAG = "ONBOARDING_INTEGRATION";
    private final LinkedList<Activity> activities = new LinkedList<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final BroadcastReceiver onFinishCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Activity activity = activities.peek();
            if (activity == null) return;

            ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            View onboardingView = rootView.findViewById(R.id.onboarding_container);
            if (onboardingView != null) rootView.removeView(onboardingView);
        }
    };

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        final IntentFilter intentFilter = new IntentFilter(ACTION_FINISH_COMPLETE);
        activity.registerReceiver(onFinishCompleteReceiver, intentFilter);
        if (activities.isEmpty() && activity instanceof FragmentActivity) {
            activities.push(supportOnboarding(activity));
        }
    }

    private Activity supportOnboarding(Activity activity) {
        disposables.add(runOnboardingIfNotCompleted(activity));
        return activity;
    }

    private Disposable runOnboardingIfNotCompleted(Activity activity) {
        return Onboarding.isCompleted(activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::logCompleted)
                .doOnError(this::logNonComplete)
                .doOnError(Curry.toConsumer(this::attachOnboardingFragment, activity))
                .onErrorComplete()
                .subscribe();
    }

    private void logNonComplete(Throwable error) {
        Log.w(TAG, "onboarding not completed yet : " + error.getMessage());
    }

    private void logCompleted() {
        Log.w(TAG, "onboarding completed");
    }

    private void attachOnboardingFragment(Activity activity, Throwable error) {
        disposables.add(attachOnboardingFragmentDisposable(activity));

    }

    private Disposable attachOnboardingFragmentDisposable(Activity activity) {
        return Single.just(onboardingContainerLayout(activity))
                .map(Curry.toFunction(this::attachContainerToRootView, activity))
                .map(this::replaceFragment)
                .blockingGet();
    }

    @NotNull
    private ViewGroup onboardingContainerLayout(Activity activity) {
        ViewGroup container = new FrameLayout(activity);
        container.setBackgroundResource(android.R.color.white);
        container.setId(R.id.onboarding_container);
        return container;
    }

    private ViewGroup attachContainerToRootView(Activity activity, ViewGroup container) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        rootView.addView(container);
        rootView.bringChildToFront(matchParentView(container));
        return container;
    }

    private ViewGroup matchParentView(ViewGroup container) {
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        return container;
    }

    private Disposable replaceFragment(ViewGroup container) {
        return new FragmentReplacer()
                .apply(new ActivityRetriever())
                .apply(new FragmentTransaction())
                .apply(container);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activity.unregisterReceiver(onFinishCompleteReceiver);
        activities.remove(activity);
        disposables.clear();
    }

}


class DefaultLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

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

    }
}
