package com.sample.app;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wasla.onboarding.integration.Onboarding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Onboarding.onComplete(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> Toast.makeText(this, "completed", Toast.LENGTH_LONG).show())
                .doOnError(e -> Toast.makeText(this, "not-complete", Toast.LENGTH_LONG).show())
                .doOnError(e -> Onboarding.into(findViewById(R.id.fragment_container_layout)))
                .onErrorComplete()
                .subscribe();

    }
}
