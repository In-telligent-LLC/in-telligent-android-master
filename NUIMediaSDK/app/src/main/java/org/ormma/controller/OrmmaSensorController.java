/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package org.ormma.controller;

import aje.android.sdk.Logger;
import android.content.Context;
import org.ormma.controller.listeners.AccelerometerListener;
import org.ormma.view.OrmmaView;

/**
 * The Class OrmmaSensorController.  OrmmaController for interacting with sensors
 */
public class OrmmaSensorController extends OrmmaController {
	private static final String LOG_TAG = "OrmmaSensorController";
	private AccelerometerListener accelerometerListener;
	private float mLastX = 0;
	private float mLastY = 0;
	private float mLastZ = 0;

	/**
	 * Instantiates a new ormma sensor controller.
	 *
	 * @param adView the ad view
	 * @param context the context
	 */
	public OrmmaSensorController(OrmmaView adView, Context context) {
		super(adView, context);
		accelerometerListener = new AccelerometerListener(context, this);
	}

	/**
	 * Start tilt listener.
	 */
	public void startTiltListener() {
		accelerometerListener.startTrackingTilt();
	}

	/**
	 * Start shake listener.
	 */
	public void startShakeListener() {
		accelerometerListener.startTrackingShake();
	}

	/**
	 * Stop tilt listener.
	 */
	public void stopTiltListener() {
		accelerometerListener.stopTrackingTilt();
	}

	/**
	 * Stop shake listener.
	 */
	public void stopShakeListener() {
		accelerometerListener.stopTrackingShake();
	}

	/**
	 * Start heading listener.
	 */
	public void startHeadingListener() {
		accelerometerListener.startTrackingHeading();
	}

	/**
	 * Stop heading listener.
	 */
	public void stopHeadingListener() {
		accelerometerListener.stopTrackingHeading();
	}

	/**
	 * Stop.
	 */
	void stop() {
	}

	/**
	 * On shake.
	 */
	public void onShake() {
		ormmaView.injectJavaScript("Ormma.gotShake()");
	}

	/**
	 * On tilt.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void onTilt(float x, float y, float z) {
		mLastX = x;
		mLastY = y;
		mLastZ = z;
		
		String script = "window.ormmaview.fireChangeEvent({ tilt: "+ getTilt() + "})";
		Logger.d(LOG_TAG, script);
		ormmaView.injectJavaScript(script);
	}

	/**
	 * Gets the tilt.
	 *
	 * @return the tilt
	 */
	public String getTilt() {
		String tilt = "{ x : \"" + mLastX + "\", y : \"" + mLastY + "\", z : \"" + mLastZ + "\"}";
		Logger.d(LOG_TAG, "getTilt: " + tilt);
		return tilt;
	}

	/**
	 * On heading change.
	 *
	 * @param f the f
	 */
	public void onHeadingChange(float f) {
		String script = "window.ormmaview.fireChangeEvent({ heading: " + (int) (f * (180 / Math.PI)) + "});";
		Logger.d(LOG_TAG, script);
		ormmaView.injectJavaScript(script);
	}

	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	@SuppressWarnings("UnusedDeclaration")
	public float getHeading() {
		Logger.d(LOG_TAG, "getHeading: " + accelerometerListener.getHeading());
		return accelerometerListener.getHeading();
	}

	/** {@inheritDoc} */
	@Override
	public void stopAllListeners() {
		accelerometerListener.stopAllListeners();
	}
}
