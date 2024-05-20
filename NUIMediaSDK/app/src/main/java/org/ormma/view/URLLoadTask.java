package org.ormma.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.ormma.controller.OrmmaUtilityController;

import aje.android.sdk.AdError;
import aje.android.sdk.EmptyResponseException;
import aje.android.sdk.Logger;
import android.content.res.AssetManager;

public class URLLoadTask implements Runnable {
	private static final String LOG_TAG = "URLLoadTask";
	
	private String url;
	private OrmmaView view;
	private boolean sendEvents;
	private OrmmaUtilityController controller;
	private Runnable onComplete;
	
	public URLLoadTask(String url, OrmmaView view, OrmmaUtilityController controller, boolean sendEvents, Runnable onComplete) {
		this.url = url;
		this.view = view;
		this.controller = controller;
		this.sendEvents = sendEvents;
		this.onComplete = onComplete;
	}
	
	@Override
	public void run() {
		if (sendEvents) {
			view.adEventsListener.onFetchAdStarted();
		}
		Logger.i(LOG_TAG, "Start to load: "+url);
		
		try {
			InputStream is = null;
			if (url.startsWith("file:///android_asset/")) {
				// if it is in the asset directory use the assetmanager
				AssetManager am = view.getContext().getAssets();
				is = am.open(url.replace("file:///android_asset/", ""));
			} else {
				URLConnection con = new URL(url).openConnection();
				con.setConnectTimeout(30000); // 30 seconds
				con.setReadTimeout(30000); // 30 seconds
				is = con.getInputStream();
			}
			controller.deleteOldAds();

			try {
				view.localFilePath = controller.writeToDiskWrap(
					is,	OrmmaView.CURRENT_FILE, true, null, OrmmaView.ormmaBridgeScriptPath, OrmmaView.ormmaScriptPath, OrmmaView.mraidScriptPath
				);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						//Do nothing
					}
				}
			}							
				
			final String localURL = "file://" + view.localFilePath + File.separator + OrmmaView.CURRENT_FILE;
			Logger.i(LOG_TAG, "Loaded in file: "+view.localFilePath);

			synchronized (view.syncUrlLoad) {
				view.getHandler().post(new Runnable(){
					@Override
					public void run() {
						Logger.i(LOG_TAG, "Start to render HTML from file: "+localURL);						
						view.clearView();
						view.loadUrlInternal(localURL);
					}
				});
				Logger.i(LOG_TAG, "Wait HTML rendering from file: "+localURL);
				view.syncUrlLoad.wait(30000); // wait at least 30 seconds, or page finished event
				Logger.i(LOG_TAG, "Complete HTML rendering from file: "+localURL);
			}
			if (onComplete != null) {
				onComplete.run();
			}
			
			if (sendEvents) {
				view.adEventsListener.onFetchAdFinished();
			}
		} catch (IOException e) {
			if (sendEvents) {
				view.adEventsListener.onFailedToFetchAd(AdError.NETWORK_ERROR, e.getMessage());
			}
			Logger.e(LOG_TAG, "Cannot load Ad from: "+url, e);
		} catch (EmptyResponseException e) {
			if (sendEvents) {
				view.adEventsListener.onFailedToFetchAd(AdError.EMPTY_RESPONSE, "Empty response was returned from the Ad Server");
			}
			Logger.e(LOG_TAG, "Empty response for: "+url);
		} catch (Throwable e) {
			if (sendEvents) {
				view.adEventsListener.onFailedToFetchAd(AdError.INTERNAL_ERROR, e.getMessage());
			}
			Logger.e(LOG_TAG, "Cannot load Ad from: "+url, e);
		}
	}
}
