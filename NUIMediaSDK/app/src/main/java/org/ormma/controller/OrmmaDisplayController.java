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
import android.content.Intent;
import android.content.IntentFilter;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import org.json.JSONObject;
import org.ormma.controller.listeners.OrientationListener;
import org.ormma.view.OrmmaView;

/**
 * The Class OrmmaDisplayController.  A ormma controller for handling display related operations
 */
public class OrmmaDisplayController extends OrmmaController {

	//tag for logging
	private static final String LOG_TAG = "OrmmaDisplayController";

	private WindowManager windowManager;
	private boolean maxSizeAvailable = false;
	private int maxWidth = -1;
	private int maxHeight = -1;
	private OrientationListener broadCastReceiver;
	private float density;

	/**
	 * Instantiates a new ormma display controller.
	 *
	 * @param adView the ad view
	 * @param c      the context
	 */
	public OrmmaDisplayController(OrmmaView adView, Context c) {
		super(adView, c);
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		density = metrics.density;

	}

	/**
	 * Resize the view.
	 *
	 * @param width  the width
	 * @param height the height
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void resize(int width, int height) {
		Logger.d(LOG_TAG, "resize: width: " + width + " height: " + height);
		if (((maxHeight > 0) && (height > maxHeight)) || ((maxWidth > 0) && (width > maxWidth))) {
			ormmaView.raiseError("Maximum size exceeded", "resize");
		} else {
			ormmaView.resize((int) (density * width), (int) (density * height));
		}
	}

	/**
	 * Open a browser
	 *
	 * @param url     the url
	 * @param back    show the back button
	 * @param forward show the forward button
	 * @param refresh show the refresh button
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void open(String url, boolean back, boolean forward, boolean refresh) {
		Logger.d(LOG_TAG, "open: url: " + url + " back: " + back + " forward: " + forward + " refresh: " + refresh);
		if (!URLUtil.isValidUrl(url)) {
			ormmaView.raiseError("Invalid url", "open");
		} else {
			ormmaView.open(url, back, forward, refresh);
		}


	}

	/**
	 * Open map
	 *
	 * @param url        - map url
	 * @param fullscreen - boolean indicating whether map to be launched in full screen
	 * @throws OrmmaException operation failed
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void openMap(String url, boolean fullscreen) throws OrmmaException {
		Logger.d(LOG_TAG, "openMap: url: " + url);
		ormmaView.openMap(url, fullscreen);
	}


	/**
	 * Play audio
	 *
	 * @param url        - audio url to be played
	 * @param autoPlay   - if audio should play immediately
	 * @param controls   - should native player controls be visible
	 * @param loop       - should video start over again after finishing
	 * @param position   - should audio be included with ad content
	 * @param startStyle - normal/full screen (if audio should play in native full screen mode)
	 * @param stopStyle  - normal/exit (exit if player should exit after audio stops)
	 * @throws OrmmaException operation failed
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void playAudio(String url, boolean autoPlay, boolean controls, boolean loop, boolean position, String startStyle, String stopStyle) throws OrmmaException {
		Logger.d(LOG_TAG, "playAudio: url: " + url + " autoPlay: " + autoPlay + " controls: " + controls + " loop: " + loop + " position: " + position + " startStyle: " + startStyle + " stopStyle: " + stopStyle);
		if (!URLUtil.isValidUrl(url)) {
			ormmaView.raiseError("Invalid url", "playAudio");
		} else {
			ormmaView.playAudio(url, autoPlay, controls, loop, position, startStyle, stopStyle);
		}
	}


	/**
	 * Play video
	 *
	 * @param url        - video url to be played
	 * @param audioMuted - should audio be muted
	 * @param autoPlay   - should video play immediately
	 * @param controls   - should native player controls be visible
	 * @param loop       - should video start over again after finishing
	 * @param position   - top and left coordinates of video in pixels if video should play inline
	 * @param startStyle - normal/fullscreen (if video should play in native full screen mode)
	 * @param stopStyle  - normal/exit (exit if player should exit after video stops)
	 * @throws OrmmaException operation failed
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void playVideo(String url, boolean audioMuted, boolean autoPlay, boolean controls, boolean loop, int[] position, String startStyle, String stopStyle) throws OrmmaException {
		Logger.d(LOG_TAG, "playVideo: url: " + url + " audioMuted: " + audioMuted + " autoPlay: " + autoPlay + " controls: " + controls + " loop: " + loop + " x: " + position[0] +
				" y: " + position[1] + " width: " + position[2] + " height: " + position[3] + " startStyle: " + startStyle + " stopStyle: " + stopStyle);
		Dimensions d = null;
		if (position[0] != -1) {
			d = new Dimensions();
			d.x = position[0];
			d.y = position[1];
			d.width = position[2];
			d.height = position[3];
			d = getDeviceDimensions(d);
		}
		if (!URLUtil.isValidUrl(url)) {
			ormmaView.raiseError("Invalid url", "playVideo");
		} else {
			ormmaView.playVideo(url, audioMuted, autoPlay, controls, loop, d, startStyle, stopStyle);
		}
	}

	/**
	 * Get Device dimensions
	 *
	 * @param d - dimensions received from java script
	 * @return device dimensions
	 */
	private Dimensions getDeviceDimensions(Dimensions d) {
		d.width *= density;
		d.height *= density;
		d.x *= density;
		d.y *= density;
		if (d.height < 0) {
			d.height = ormmaView.getHeight();
		}
		if (d.width < 0) {
			d.width = ormmaView.getWidth();
		}
		int loc[] = new int[2];
		ormmaView.getLocationInWindow(loc);
		if (d.x < 0) {
			d.x = loc[0];
		}
		if (d.y < 0) {
			int topStuff = 0;// ((Activity)context).findViewById(Window.ID_ANDROID_CONTENT).getTop();
			d.y = loc[1] - topStuff;
		}
		return d;
	}

	/**
	 * Expand the view
	 *
	 * @param url        the uRL
	 * @param properties the properties for the expansion
	 * @throws OrmmaException operation failed
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void expand(String url, String properties) throws OrmmaException {
		Logger.d(LOG_TAG, "expand: url: " + url + " properties: " + properties);
		try {
			DisplayMetrics metrics = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(metrics);
			Dimensions dimensions = new Dimensions();
			dimensions.width = metrics.widthPixels;
			dimensions.height = metrics.heightPixels;
			ormmaView.expand(dimensions, url, (Properties) getFromJSON(new JSONObject(properties), Properties.class));
		} catch (Exception e) {
			throw new OrmmaException("Couldn't expand ad", e);
		}
	}

	/**
	 * Close the view
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void close() {
		Logger.d(LOG_TAG, "close");
		ormmaView.close();
	}

	/**
	 * Hide the view
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void hide() {
		Logger.d(LOG_TAG, "hide");
		ormmaView.hide();
	}

	/**
	 * Show the view
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void show() {
		Logger.d(LOG_TAG, "show");
		ormmaView.show();
	}

	/**
	 * @return true if ad is viewable
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public boolean isViewable() {
		return (ormmaView.getVisibility() == View.VISIBLE);
	}

	/**
	 * Gets the orientation.
	 *
	 * @return the orientation
	 */
	public int getOrientation() {
		int orientation = windowManager.getDefaultDisplay().getOrientation();
		int ret = -1;
		switch (orientation) {
			case Surface.ROTATION_0:
				ret = 0;
				break;

			case Surface.ROTATION_90:
				ret = 90;
				break;

			case Surface.ROTATION_180:
				ret = 180;
				break;

			case Surface.ROTATION_270:
				ret = 270;
				break;
		}
		Logger.d(LOG_TAG, "getOrientation: " + ret);
		return ret;
	}

	/**
	 * Gets the screen size.
	 *
	 * @return the screen size
	 */
	public String getScreenSize() {
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);

		return "{ width: " + (int) (metrics.widthPixels / metrics.density) + ", " + "height: "
				+ (int) (metrics.heightPixels / metrics.density) + "}";
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		return ormmaView.getSize();
	}

	/**
	 * Gets the max size.
	 *
	 * @return the max size
	 */
	public String getMaxSize() {
		if (maxSizeAvailable) {
			return "{ width: " + maxWidth + ", " + "height: " + maxHeight + "}";
		} else {
			return getScreenSize();
		}
	}

	/**
	 * Sets the max size.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public void setMaxSize(int w, int h) {
		maxSizeAvailable = true;
		maxWidth = w;
		maxHeight = h;
	}

	/**
	 * On orientation changed.
	 *
	 * @param orientation the orientation
	 */
	public void onOrientationChanged(int orientation) {
		String script = "window.ormmaview.fireChangeEvent({ orientation: " + orientation + "});";
		Logger.d(LOG_TAG, script);
		ormmaView.injectJavaScript(script);
	}

	/** {@inheritDoc} */
	@Override
	public void stopAllListeners() {
		stopConfigurationListener();
		broadCastReceiver = null;
	}

	public void stopConfigurationListener() {
		try {
			context.unregisterReceiver(broadCastReceiver);
		} catch (Exception e) {
			//Do nothing
		}
	}

	public void startConfigurationListener() {
		try {
			if (broadCastReceiver == null) {
				broadCastReceiver = new OrientationListener(this);
			}
			context.registerReceiver(broadCastReceiver, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
		} catch (Exception e) {
			//Send error
		}
	}
}
