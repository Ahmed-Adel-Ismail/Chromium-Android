package com.wasla.onboarding.presentation;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.functional.curry.Curry;
import com.wasla.login.R;
import com.wasla.onboarding.domain.usecases.OnboardingDataSaverUseCase;
import com.wasla.onboarding.entities.InternalSdk;
import com.wasla.onboarding.entities.OnboardingData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InternalSdk
public class OnboardingFragment extends Fragment {

    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    private static final int NEXT_PAGE_INDEX = 2;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private ViewPager viewPager;
    private final BroadcastReceiver onNextClicked = onNextClickedReceiver();
    private OnboardingViewModel viewModel;
    private final BroadcastReceiver onFinishClicked = onFinishClickedReceiver();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() == null) return;
        viewPager = viewPager(view, getActivity());
        viewModel = new OnboardingViewModelInjector().apply(getActivity());
    }

    @NotNull
    private ViewPager viewPager(@NonNull View view, @NonNull FragmentActivity activity) {
        ViewPager pager = view.findViewById(R.id.onboarding_view_pager);
        pager.setAdapter(pagerAdapter(activity));
        return pager;
    }

    private PagerAdapter pagerAdapter(@NonNull FragmentActivity activity) {
        return Single.just(activity)
                .map(ViewModelProviders::of)
                .map(provider -> provider.get(OnboardingPageAdapterHolder.class))
                .map(OnboardingPageAdapterHolder::getFragments)
                .map(Curry.toFunction(PagerAdapter::new, getFragmentManager()))
                .blockingGet();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().registerReceiver(onNextClicked, new IntentFilter(ACTION_NEXT));
            getActivity().registerReceiver(onFinishClicked, new IntentFilter(ACTION_FINISH));
        }
    }

    @Override
    public void onPause() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(onNextClicked);
            getActivity().unregisterReceiver(onFinishClicked);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager = null;
        viewModel = null;
    }


    @NotNull
    private BroadcastReceiver onNextClickedReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (viewPager != null && getActivity() != null)
                    viewPager.setCurrentItem(NEXT_PAGE_INDEX);
            }
        };
    }

    @NotNull
    private BroadcastReceiver onFinishClickedReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (viewModel != null && getActivity() != null)
                    disposables.add(dataSaverDisposable());
            }
        };
    }

    private Disposable dataSaverDisposable() {
        return new OnboardingDataSaverUseCase()
                .apply(viewModel.onboardingRepository)
                .apply(viewModel.progress)
                .apply(viewModel.onboardingData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finishOnboarding, this::displayError);
    }

    private void displayError(Throwable error) {
        if (getActivity() == null) return;
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void finishOnboarding(OnboardingData data) {
        if (getActivity() == null) return;
        Toast.makeText(getActivity(), "finished on-boarding", Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

}



