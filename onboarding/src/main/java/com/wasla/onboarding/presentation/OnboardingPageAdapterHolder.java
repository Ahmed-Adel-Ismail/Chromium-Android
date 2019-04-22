package com.wasla.onboarding.presentation;

import android.arch.lifecycle.ViewModel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class OnboardingPageAdapterHolder extends ViewModel {

    private final ArrayList<Fragment> fragments = new ArrayList<>();

    public OnboardingPageAdapterHolder() {
        fragments.add(new OnboardingStepOneFragment());
        fragments.add(new OnboardingStepTwoFragment());
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }
}

class PagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments;

    PagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        try{
            super.restoreState(state, loader);
        }catch (NullPointerException e){
            // eat exception to avoid crash while restoring state
            // there is no state required for this adapter
        }
    }
}

