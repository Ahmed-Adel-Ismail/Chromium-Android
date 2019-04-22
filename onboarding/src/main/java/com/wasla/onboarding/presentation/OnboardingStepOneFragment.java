package com.wasla.onboarding.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.functional.curry.SwapCurry;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.wasla.login.R;
import com.wasla.onboarding.entities.InternalSdk;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.wasla.onboarding.presentation.OnboardingFragment.ACTION_NEXT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@InternalSdk
public class OnboardingStepOneFragment extends Fragment {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding_step_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) disposables.add(bindViews(view, getActivity()));
    }

    private Disposable bindViews(@NonNull View view, FragmentActivity activity) {
        return Single.just(activity)
                .map(new OnboardingViewModelInjector())
                .doOnSuccess(viewModel -> disposables.add(bindFirstName(view, viewModel)))
                .doOnSuccess(viewModel -> disposables.add(bindMiddleName(view, viewModel)))
                .doOnSuccess(viewModel -> disposables.add(bindLastName(view, viewModel)))
                .subscribe(viewModel -> disposables.add(bindNextButton(view)), Throwable::printStackTrace);
    }

    @NotNull
    private Disposable bindFirstName(@NonNull View view, OnboardingViewModel viewModel) {
        return RxTextView.textChanges(view.findViewById(R.id.first_name_edit_text))
                .map(CharSequence::toString)
                .subscribe(viewModel.firstName::onNext, Throwable::printStackTrace);
    }

    @NotNull
    private Disposable bindMiddleName(@NonNull View view, OnboardingViewModel viewModel) {
        return RxTextView.textChanges(view.findViewById(R.id.middle_name_edit_text))
                .map(CharSequence::toString)
                .subscribe(viewModel.middleName::onNext, Throwable::printStackTrace);
    }

    @NotNull
    private Disposable bindLastName(@NonNull View view, OnboardingViewModel viewModel) {
        return RxTextView.textChanges(view.findViewById(R.id.last_name_edit_text))
                .map(CharSequence::toString)
                .subscribe(viewModel.lastName::onNext, Throwable::printStackTrace);
    }

    @NotNull
    private Disposable bindNextButton(@NonNull View view) {
        return RxView.clicks(view.findViewById(R.id.next_button))
                .debounce(300, MILLISECONDS)
                .filter(ignoredParameter -> getActivity() != null)
                .map(ignoredParameter -> getActivity())
                .subscribe(activity -> activity.sendBroadcast(new Intent(ACTION_NEXT)));
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
