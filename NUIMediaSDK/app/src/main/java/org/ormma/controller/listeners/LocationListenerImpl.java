/* License (MIT)
 * Copyright (c) 2008 Nitobi
 * website: http://phonegap.com
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * “Software”), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ormma.controller.listeners;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import org.ormma.controller.OrmmaLocationController;

/**
 * The listener interface for receiving location events.
 * The class that is interested in processing a loc
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLocListener<code> method. When
 * the loc event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class LocationListenerImpl implements LocationListener {

	/**
	 * The ormma location controller.
	 */
	private OrmmaLocationController ormmaLocationController;
	
	/**
	 * The m loc man.
	 */
	private LocationManager locationManager;

	/**
	 * The m provider.
	 */
	private String provider;

	/**
	 * Instantiates a new loc listener.
	 *
	 * @param c the c
	 * @param ormmaLocationController the ormma location controller
	 * @param provider the provider
	 */
	public LocationListenerImpl(Context c, OrmmaLocationController ormmaLocationController, String provider) {
		this.ormmaLocationController = ormmaLocationController;
		this.locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		this.provider = provider;
	}

	/** {@inheritDoc} */
	@Override
	public void onProviderDisabled(String provider) {
		ormmaLocationController.fail();
	}

	/** {@inheritDoc} */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		if (status == 0) {
			ormmaLocationController.fail();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onLocationChanged(Location location) {
		ormmaLocationController.success(location);
	}

	/**
	 * Stop.
	 */
	public void stop() {
		locationManager.removeUpdates(this);
	}

	/** {@inheritDoc} */
	@Override
	public void onProviderEnabled(String provider) {
	}

	/**
	 * Start.
	 */
	public void start() {
		locationManager.requestLocationUpdates(provider, 0, 0, this);
	}

}
