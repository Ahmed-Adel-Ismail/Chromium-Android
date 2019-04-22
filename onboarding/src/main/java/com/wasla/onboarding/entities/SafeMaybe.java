package com.wasla.onboarding.entities;

import io.reactivex.Maybe;

@InternalSdk
public class SafeMaybe {

    public static <V> Maybe<V> just(V value) {
        if (value != null) return Maybe.just(value);
        return Maybe.empty();
    }
}
