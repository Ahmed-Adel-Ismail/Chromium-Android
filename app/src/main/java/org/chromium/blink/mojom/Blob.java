
// Copyright 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// This file is autogenerated by:
//     mojo/public/tools/bindings/mojom_bindings_generator.py
// For:
//     third_party/WebKit/public/mojom/blob/blob.mojom
//

package org.chromium.blink.mojom;

import org.chromium.mojo.bindings.DeserializationException;


public interface Blob extends org.chromium.mojo.bindings.Interface {



    public interface Proxy extends Blob, org.chromium.mojo.bindings.Interface.Proxy {
    }

    Manager<Blob, Blob.Proxy> MANAGER = Blob_Internal.MANAGER;


    void clone(
org.chromium.mojo.bindings.InterfaceRequest<Blob> blob);



    void readAll(
org.chromium.mojo.system.DataPipe.ProducerHandle pipe, BlobReaderClient client);



    void readRange(
long offset, long length, org.chromium.mojo.system.DataPipe.ProducerHandle pipe, BlobReaderClient client);



    void getInternalUuid(

GetInternalUuidResponse callback);

    interface GetInternalUuidResponse extends org.chromium.mojo.bindings.Callbacks.Callback1<String> { }


}