package com.wasla.onboarding.entities;

import com.google.gson.annotations.SerializedName;

import io.reactivex.Maybe;

@SuppressWarnings("WeakerAccess")
public class OnboardingData {

    @SerializedName("firstName")
    public final String firstName;
    @SerializedName("middleName")
    public final String middleName;
    @SerializedName("lastName")
    public final String lastName;
    @SerializedName("phoneNumber")
    public final String phoneNumber;

    public OnboardingData(String firstName, String middleName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Maybe<String> getFirstName() {
        return SafeMaybe.just(firstName);
    }

    public Maybe<String> getMiddleName() {
        return SafeMaybe.just(middleName);
    }

    public Maybe<String> getLastName() {
        return SafeMaybe.just(lastName);
    }

    public Maybe<String> getPhoneNumber() {
        return SafeMaybe.just(phoneNumber);
    }
}
