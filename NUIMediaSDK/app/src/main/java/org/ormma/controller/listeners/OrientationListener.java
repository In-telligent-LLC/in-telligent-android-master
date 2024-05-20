/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package org.ormma.controller.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.ormma.controller.OrmmaDisplayController;

public class OrientationListener extends BroadcastReceiver {

	private OrmmaDisplayController displayController;
	
	/**
	 * The last orientation.
	 */
	private int lastOrientation;

	/**
	 * Instantiates a new ormma configuration broadcast receiver.
	 *
	 * @param ormmaDisplayController the ormma display controller
	 */
	public OrientationListener(OrmmaDisplayController ormmaDisplayController) {
		displayController = ormmaDisplayController;
		lastOrientation = displayController.getOrientation();
	}

	/** {@inheritDoc} */
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
			int orientation = displayController.getOrientation();
			if (orientation != lastOrientation) {
				lastOrientation = orientation;
				displayController.onOrientationChanged(lastOrientation);
			}
		}
	}
}
