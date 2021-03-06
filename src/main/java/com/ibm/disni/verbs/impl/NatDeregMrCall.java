/*
 * DiSNI: Direct Storage and Networking Interface
 *
 * Author: Patrick Stuedi <stu@zurich.ibm.com>
 *
 * Copyright (C) 2016-2018, IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ibm.disni.verbs.impl;

import java.io.IOException;

import com.ibm.disni.verbs.IbvMr;
import com.ibm.disni.verbs.SVCDeregMr;


public class NatDeregMrCall extends SVCDeregMr {
	private NativeDispatcher nativeDispatcher;
	private RdmaVerbsNat verbs;
	
	private NatIbvMr mr;
	private boolean valid;

	public NatDeregMrCall(RdmaVerbsNat verbs, NativeDispatcher nativeDispatcher, IbvMr mr) {
		this.verbs = verbs;
		this.nativeDispatcher = nativeDispatcher;
		this.mr = (NatIbvMr) mr;
		this.valid = true;
	}

	public SVCDeregMr execute() throws IOException {
		if (!mr.isOpen()) {
			throw new IOException("Trying to deregister closed memory region.");
		}
		mr.close();
		nativeDispatcher._deregMr(mr.getObjId());
		return this;
	}
	
	public boolean isValid() {
		return valid;
	}

	public SVCDeregMr free() {
		this.valid = false;
		return this;
	}
}
