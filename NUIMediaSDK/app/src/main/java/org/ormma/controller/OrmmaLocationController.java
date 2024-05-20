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
import android.location.Location;
import android.location.LocationManager;
import org.ormma.controller.listeners.LocationListenerImpl;
import org.ormma.view.OrmmaView;

import java.util.Iterator;
import java.util.List;

/**
 * The Class OrmmaLocationController.  Ormma controller for interacting with lbs
 */
public class OrmmaLocationController extends OrmmaController {

	private static final String LOG_TAG = "OrmmaLocationController";
	
	private LocationManager locationManager;
	private boolean hasPermission = false;
	private LocationListenerImpl gps;
	private LocationListenerImpl network;
	private int locListenerCount;
	private boolean allowLocationServices = false;
	
	/**
	 * Instantiates a new ormma location controller.
	 *
	 * @param adView the ad view
	 * @param context the context
	 */
	public OrmmaLocationController(OrmmaView adView, Context context) {
		super(adView, context);
		try {
			locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null)
				gps = new LocationListenerImpl(context, this, LocationManager.GPS_PROVIDER);
			if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null)
				network = new LocationListenerImpl(context, this, LocationManager.NETWORK_PROVIDER);
			hasPermission = true;
		} catch (SecurityException e) {
			//Do nothing
		}
	}

	/**
	 * @param flag - Should the location services be enabled / not.
	 */
	public void allowLocationServices(boolean flag) {
		this.allowLocationServices = flag;
	}

	/**
	 * @return - allowLocationServices
	 */
	public boolean allowLocationServices() {
		return allowLocationServices;
	}	
	
	private static String formatLocation(Location loc) {
		return "{ lat: " + loc.getLatitude() + ", lon: " + loc.getLongitude() + ", acc: " + loc.getAccuracy() +"}";
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 * NOTE: This method is called by JavaScript
	 */
	@SuppressWarnings("UnusedDeclaration")
	public String getLocation() {
		Logger.d(LOG_TAG, "getLocation: hasPermission: " + hasPermission);
		if (!hasPermission) {
			return null;
		}
		List<String> providers = locationManager.getProviders(true);
		Iterator<String> provider = providers.iterator();
		Location lastKnown = null;
		while (provider.hasNext()) {
			lastKnown = locationManager.getLastKnownLocation(provider.next());
			if (lastKnown != null) {
				break;
			}
		}
		Logger.d(LOG_TAG, "getLocation: " + lastKnown);
		if (lastKnown != null) {
			return formatLocation(lastKnown);
		} else
			return null;
	}

	/**
	 * Start location listener.
	 */
	public void startLocationListener() {
		if (locListenerCount == 0) {

			if (network != null)
				network.start();
			if (gps != null)
				gps.start();
		}
		locListenerCount++;
	}

	/**
	 * Stop location listener.
	 */
	public void stopLocationListener() {
		locListenerCount--;
		if (locListenerCount == 0) {

			if (network != null)
				network.stop();
			if (gps != null)
				gps.stop();
		}
	}

	/**
	 * Success.
	 *
	 * @param loc the loc
	 */
	public void success(Location loc) {
		String script = "window.ormmaview.fireChangeEvent({ location: "+ formatLocation(loc) + "})";
		Logger.d(LOG_TAG, script);
		ormmaView.injectJavaScript(script);
	}

	/**
	 * Fail.
	 */
	public void fail() {
		Logger.e(LOG_TAG, "Location can't be determined");
		ormmaView.injectJavaScript("window.ormmaview.fireErrorEvent(\"Location cannot be identified\", \"OrmmaLocationController\")");
	}

	/** {@inheritDoc} */
	@Override
	public void stopAllListeners() {
		locListenerCount = 0;
		try {
			gps.stop();
		} catch (Exception e) {
			//Do nothing
		}
		try {
			network.stop();
		} catch (Exception e) {
			//Do nothing
		}
	}
}
