package com.wasla.onboarding.entities;

import com.google.gson.annotations.SerializedName;

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

}
