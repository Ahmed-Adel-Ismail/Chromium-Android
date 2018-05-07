
// Copyright 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// This file is autogenerated by:
//     mojo/public/tools/bindings/mojom_bindings_generator.py
// For:
//     services/device/public/mojom/screen_orientation_lock_types.mojom
//

package org.chromium.device.mojom;

import org.chromium.mojo.bindings.DeserializationException;

public final class ScreenOrientationLockResult {


    public static final int SCREEN_ORIENTATION_LOCK_RESULT_SUCCESS = 0;

    public static final int SCREEN_ORIENTATION_LOCK_RESULT_ERROR_NOT_AVAILABLE = SCREEN_ORIENTATION_LOCK_RESULT_SUCCESS + 1;

    public static final int SCREEN_ORIENTATION_LOCK_RESULT_ERROR_FULLSCREEN_REQUIRED = SCREEN_ORIENTATION_LOCK_RESULT_ERROR_NOT_AVAILABLE + 1;

    public static final int SCREEN_ORIENTATION_LOCK_RESULT_ERROR_CANCELED = SCREEN_ORIENTATION_LOCK_RESULT_ERROR_FULLSCREEN_REQUIRED + 1;


    private static final boolean IS_EXTENSIBLE = false;

    public static boolean isKnownValue(int value) {
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
        }
        return false;
    }

    public static void validate(int value) {
        if (IS_EXTENSIBLE || isKnownValue(value))
            return;

        throw new DeserializationException("Invalid enum value.");
    }

    private ScreenOrientationLockResult() {}

}