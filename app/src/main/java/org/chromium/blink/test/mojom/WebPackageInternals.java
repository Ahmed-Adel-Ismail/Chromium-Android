
// Copyright 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// This file is autogenerated by:
//     mojo/public/tools/bindings/mojom_bindings_generator.py
// For:
//     third_party/blink/public/mojom/web_package/web_package_internals.mojom
//

package org.chromium.blink.test.mojom;

import org.chromium.mojo.bindings.DeserializationException;


public interface WebPackageInternals extends org.chromium.mojo.bindings.Interface {



    public interface Proxy extends WebPackageInternals, org.chromium.mojo.bindings.Interface.Proxy {
    }

    Manager<WebPackageInternals, WebPackageInternals.Proxy> MANAGER = WebPackageInternals_Internal.MANAGER;


    void setSignedExchangeVerificationTime(
org.chromium.mojo_base.mojom.Time time, 
SetSignedExchangeVerificationTimeResponse callback);

    interface SetSignedExchangeVerificationTimeResponse extends org.chromium.mojo.bindings.Callbacks.Callback0 { }


}