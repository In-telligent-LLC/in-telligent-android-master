/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package org.ormma.controller.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import org.ormma.controller.OrmmaNetworkController;

/**
 * The Class OrmmaNetworkBroadcastReceiver.
 */
public class OrmmaNetworkBroadcastReceiver extends BroadcastReceiver {

	private OrmmaNetworkController ormmaNetworkController;

	/**
	 * Instantiates a new ormma network broadcast receiver.
	 *
	 * @param ormmaNetworkController the ormma network controller
	 */
	public OrmmaNetworkBroadcastReceiver(OrmmaNetworkController ormmaNetworkController) {
		this.ormmaNetworkController = ormmaNetworkController;
	}

	/** {@inheritDoc} */
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			ormmaNetworkController.onConnectionChanged();
		}
	}
}
