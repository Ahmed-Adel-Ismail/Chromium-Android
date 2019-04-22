package com.wasla.onboarding.entities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * an annotation that marks internal members that are not part of the API, avoid using any
 * code that is annotated with this annotation from outside this module
 */
@Retention(RetentionPolicy.SOURCE)
public @interface InternalSdk {
}
