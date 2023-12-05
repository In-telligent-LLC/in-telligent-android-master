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
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.ormma.controller.util.OrmmaNetworkBroadcastReceiver;
import org.ormma.view.OrmmaView;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The Class OrmmaNetworkController.  OrmmaController for interacting with network states
 */
public class OrmmaNetworkController extends OrmmaController {
	private static final String LOG_TAG = "OrmmaNetworkController";
	
	private ConnectivityManager connectivityManager;
	private int networkListenerCount;
	private OrmmaNetworkBroadcastReceiver broadcastReceiver;
	private IntentFilter filter;
	private Map<String, String> requests = new HashMap<String, String>();

	/**
	 * Instantiates a new ormma network controller.
	 *
	 * @param adView the ad view
	 * @param context the context
	 */
	public OrmmaNetworkController(OrmmaView adView, Context context) {
		super(adView, context);
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * Gets the network.
	 *
	 * @return the network
	 */
	public String getNetwork() {
		NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        String networkType = "unknown";
		if (ni == null){
			networkType = "offline";
		}
		else{
			switch (ni.getState()) {
			case UNKNOWN:
				networkType = "unknown";
				break;
			case DISCONNECTED:
				networkType = "offline";
				break;
			default:
				int type = ni.getType();
				if (type == ConnectivityManager.TYPE_MOBILE)
					networkType = "cell";
				else if (type == ConnectivityManager.TYPE_WIFI)
					networkType = "wifi";
			}
		}
		Logger.d(LOG_TAG, "getNetwork: " + networkType);
		return networkType;
	}

	/**
	 * Start network listener.
	 */
	public void startNetworkListener() {
		if (networkListenerCount == 0) {
			broadcastReceiver = new OrmmaNetworkBroadcastReceiver(this);
			filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

		}
		networkListenerCount++;
		context.registerReceiver(broadcastReceiver, filter);
	}

	/**
	 * Stop network listener.
	 */
	public void stopNetworkListener() {
		networkListenerCount--;
		if (networkListenerCount == 0) {
			context.unregisterReceiver(broadcastReceiver);
			broadcastReceiver = null;
			filter = null;

		}
	}

	/**
	 * On connection changed.
	 */
	public void onConnectionChanged() {
		String script = "window.ormmaview.fireChangeEvent({ network: \'" + getNetwork() + "\'});";
		Logger.d(LOG_TAG, script);
		ormmaView.injectJavaScript(script);

		if(isOnline()) {
			Iterator<String> keys = requests.keySet().iterator();
			while (keys.hasNext()) {
				String uri = keys.next();
				String display = requests.get(uri);
				response(uri, display);
				keys.remove();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void stopAllListeners() {
		networkListenerCount = 0;
		try {
			context.unregisterReceiver(broadcastReceiver);
		} catch (Exception e) {
			//Do nothing
		}
	}
	
	/**
	 * @param uri resource uri
	 * @param display 'proxy' to put result to 'response' event
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void request(String uri, String display) {
		Logger.d(LOG_TAG, "request: uri=" + uri + ";display=" + display);
		if(isOnline()) {
			response(uri, display);
		} else {
			requests.put(uri, display);
		}
	}

	private boolean isOnline() {
		NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
		return ni != null && ni.isConnected();
	}

	private void response(String uri, String display) {
		try {
			String response = OrmmaAssetController.getHttpContent(uri);
			response = URLEncoder.encode(response);

			if (display.equalsIgnoreCase("proxy")) {
				ormmaView.injectJavaScript("ormma.response(\"" + uri + "\", decodeURIComponent(\"" + response + "\"))");
			}
		} catch (Exception e) {
			ormmaView.injectJavaScript("Ormma.fireError(\"response\",\"Could not read uri content\")");
		}
	}
}
