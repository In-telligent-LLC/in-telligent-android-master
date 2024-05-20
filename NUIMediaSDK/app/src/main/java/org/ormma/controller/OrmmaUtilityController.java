/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package org.ormma.controller;

import android.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.ormma.view.OrmmaView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aje.android.sdk.EmptyResponseException;
import aje.android.sdk.Logger;

/**
 * The Class OrmmaUtilityController.  Main ormma controller.  initiates the others.
 */
public class OrmmaUtilityController extends OrmmaController {

	/**
	 * The Constant TAG.
	 */
	private static final String LOG_TAG = "OrmmaUtilityController";

	//other controllers
	private OrmmaAssetController assetController;
	private OrmmaDisplayController displayController;
	private OrmmaLocationController locationController;
	private OrmmaNetworkController networkController;
	private OrmmaSensorController sensorController;

	/**
	 * Instantiates a new ormma utility controller.
	 *
	 * @param adView  the ad view
	 * @param context the context
	 */
	@SuppressLint("JavascriptInterface")
    public OrmmaUtilityController(OrmmaView adView, Context context) {

		super(adView, context);

		assetController = new OrmmaAssetController(adView, context);
		displayController = new OrmmaDisplayController(adView, context);
		locationController = new OrmmaLocationController(adView, context);
		networkController = new OrmmaNetworkController(adView, context);
		sensorController = new OrmmaSensorController(adView, context);

		adView.addJavascriptInterface(assetController, "ORMMAAssetsControllerBridge");
		adView.addJavascriptInterface(displayController, "ORMMADisplayControllerBridge");
		adView.addJavascriptInterface(locationController, "ORMMALocationControllerBridge");
		adView.addJavascriptInterface(networkController, "ORMMANetworkControllerBridge");
		adView.addJavascriptInterface(sensorController, "ORMMASensorControllerBridge");
	}


	/**
	 * Inits the controller.  injects state info
	 *
	 * @param density the density
	 */
	public void init(float density) {
		String injection = "window.ormmaview.fireChangeEvent({ state: \'default\'," + " network: \'"
				+ networkController.getNetwork() + "\'," + " size: " + displayController.getSize() + ","
				+ " maxSize: " + displayController.getMaxSize() + "," + " screenSize: "
				+ displayController.getScreenSize() + "," + " defaultPosition: { x:"
				+ (int) (ormmaView.getLeft() / density) + ", y: " + (int) (ormmaView.getTop() / density)
				+ ", width: " + (int) (ormmaView.getWidth() / density) + ", height: "
				+ (int) (ormmaView.getHeight() / density) + " }," + " orientation:"
				+ displayController.getOrientation() + "," + getSupports() + " });";
		Logger.d(LOG_TAG, "init: injection: " + injection);
		ormmaView.injectJavaScript(injection);
		ormmaView.injectJavaScript("mraid.completeInitializationSDK_();");

	}

	/**
	 * Gets the supports object.  Examines application permissions
	 *
	 * @return the supports
	 */
	private String getSupports() {
		String supports = "supports: [ 'level-1', 'level-2', 'level-3', 'screen', 'orientation', 'network'";

		boolean p = locationController.allowLocationServices() &&
				((context.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
						|| (context.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED));
		if (p) {
			supports += ", 'location'";
		}

		p = context.checkCallingOrSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
		if (p) {
			supports += ", 'sms'";
		}

		p = context.checkCallingOrSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
		if (p) {
			supports += ", 'phone'";
		}

		p = ((context.checkCallingOrSelfPermission(android.Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) && (context
				.checkCallingOrSelfPermission(android.Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED));
		if (p) {
			supports += ", 'calendar'";
		}

		supports += ", 'video'";

		supports += ", 'audio'";

		supports += ", 'map'";

		supports += ", 'heading'";

		supports += ", 'tilt'";

		supports += ", 'shake'";

		supports += ", 'email' ]";
		Logger.d(LOG_TAG, "getSupports: " + supports);
		return supports;

	}

	/**
	 * Ready.
	 */
	public void ready() {
		ormmaView.injectJavaScript("Ormma.setState(\"" + ormmaView.getState() + "\");");
		ormmaView.injectJavaScript("ORMMAReady();");
		ormmaView.injectJavaScript("window.ormmaview.fireReadyEvent()");
	}

	/**
	 * Send an sms.
	 *
	 * @param recipient the recipient
	 * @param body      the body
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void sendSMS(String recipient, String body) {
		Logger.d(LOG_TAG, "sendSMS: recipient: " + recipient + " body: " + body);
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.putExtra("address", recipient);
		sendIntent.putExtra("sms_body", body);
		sendIntent.setType("vnd.android-dir/mms-sms");
		sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(sendIntent);
	}

	/**
	 * Send an email.
	 *
	 * @param recipient the recipient
	 * @param subject   the subject
	 * @param body      the body
	 * @throws OrmmaException operation failed
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void sendMail(String recipient, String subject, String body) throws OrmmaException {
		Logger.d(LOG_TAG, "sendMail: recipient: " + recipient + " subject: " + subject + " body: " + body);
		Intent i = new Intent(Intent.ACTION_SENDTO);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT, body);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Logger.e(LOG_TAG, "There are no email clients installed.");
			throw new OrmmaException("Couldn't send email", ex);
		}
	}

	/**
	 * Creates the tel url.
	 *
	 * @param number the number
	 * @return the string
	 */
	private String createTelUrl(String number) {
		if (TextUtils.isEmpty(number)) {
			return null;
		}

		StringBuilder buf = new StringBuilder("tel:");
		buf.append(number);
		return buf.toString();
	}

	/**
	 * Make call.
	 *
	 * @param number the number
	 *
	 * NOTE: This method is used from JavaScript interface
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void makeCall(String number) {
		Logger.d(LOG_TAG, "makeCall: number: " + number);
		String url = createTelUrl(number);
		if (url == null) {
			ormmaView.raiseError("Bad Phone Number", "makeCall");
		} else {
			Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(url));
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}

	/**
	 * Creates a calendar event.
	 *
	 * @param date  the date
	 * @param title the title
	 * @param body  the body
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void createEvent(final String date, final String title, final String body) {
		Logger.d(LOG_TAG, "createEvent: date: " + date + " title: " + title + " body: " + body);
		final ContentResolver cr = context.getContentResolver();
		Cursor cursor;
		final String[] cols = new String[]{"_id", "displayName", "_sync_account"};

		if (Integer.parseInt(Build.VERSION.SDK) == 8) {
			// 2.2 or higher
			cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), cols, null, null, null);
		} else {
			cursor = cr.query(Uri.parse("content://calendar/calendars"), cols, null, null, null);
		}

		if (cursor == null || !cursor.moveToFirst()) {
			// No CalendarID found
			Toast.makeText(context, "No calendar account found", Toast.LENGTH_LONG).show();
			if (cursor != null) {
				cursor.close();
			}
			return;
		}

		if (cursor.getCount() == 1) {
			addCalendarEvent(cursor.getInt(0), date, title, body);
		} else {
			final List<Map<String, String>> entries = new ArrayList<Map<String, String>>();

			for (int i = 0; i < cursor.getCount(); i++) {
				Map<String, String> entry = new HashMap<String, String>();
				entry.put("ID", cursor.getString(0));
				entry.put("NAME", cursor.getString(1));
				entry.put("EMAILID", cursor.getString(2));
				entries.add(entry);
				cursor.moveToNext();
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Choose Calendar to save event to");
			ListAdapter adapter = new SimpleAdapter(context,
					entries,
					android.R.layout.two_line_list_item,
					new String[]{"NAME", "EMAILID"},
					new int[]{R.id.text1, R.id.text2});


			builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
				/** {@inheritDoc} */
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Map<String, String> entry = entries.get(which);
					addCalendarEvent(Integer.parseInt(entry.get("ID")), date, title, body);
					dialog.cancel();
				}

			});

			builder.create().show();
		}
		cursor.close();
	}

	/**
	 * Add event into Calendar
	 *
	 * @param callId the callId
	 * @param date   the date
	 * @param title  the title
	 * @param body   the body
	 */
	private void addCalendarEvent(final int callId, final String date, final String title, final String body) {
		final ContentResolver cr = context.getContentResolver();
		long dtStart = Long.parseLong(date);
		long dtEnd = dtStart + 60 * 1000 * 60;
		ContentValues cv = new ContentValues();
		cv.put("calendar_id", callId);
		cv.put("title", title);
		cv.put("description", body);
		cv.put("dtstart", dtStart);
		cv.put("hasAlarm", 1);
		cv.put("dtend", dtEnd);

		Uri newEvent;
		if (Integer.parseInt(Build.VERSION.SDK) == 8) {
			newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
		} else {
			newEvent = cr.insert(Uri.parse("content://calendar/events"), cv);
		}

		if (newEvent != null) {
			long id = Long.parseLong(newEvent.getLastPathSegment());
			ContentValues values = new ContentValues();
			values.put("event_id", id);
			values.put("method", 1);
			values.put("minutes", 15); // 15 minutes
			if (Integer.parseInt(Build.VERSION.SDK) == 8) {
				cr.insert(Uri.parse("content://com.android.calendar/reminders"), values);
			} else {
				cr.insert(Uri.parse("content://calendar/reminders"), values);
			}
		}

		Toast.makeText(context, "Event added to calendar", Toast.LENGTH_SHORT).show();
	}


	/**
	 * Copy text from jar into asset dir.
	 *
	 * @param alias  the alias
	 * @param source the source
	 * @return the string
	 * @throws OrmmaException operation failed
	 */
	public String copyTextFromJarIntoAssetDir(String alias, String source) throws OrmmaException {
		return assetController.copyTextFromJarIntoAssetDir(alias, source);
	}

	/**
	 * Sets the max size.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public void setMaxSize(int w, int h) {
		displayController.setMaxSize(w, h);
	}

	/**
	 * Write to disk wrapping with ormma stuff.
	 *
	 * @param is                     the iinput stream
	 * @param currentFile            the file to write to
	 * @param storeInHashedDirectory store in a directory based on a hash of the input
	 * @param injection              and additional javascript to insert
	 * @param bridgePath             the path the ormma javascript bridge
	 * @param ormmaPath              the ormma javascript
	 * @param mraidPath              the mraid javascript
	 * @return the string
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException           Signals that an I/O exception has occurred.
	 * @throws aje.android.sdk.EmptyResponseException AdJuggler returned empty response
	 */
	public String writeToDiskWrap(InputStream is, String currentFile, boolean storeInHashedDirectory, String injection, String bridgePath,
	                              String ormmaPath, String mraidPath) throws IllegalStateException, IOException, EmptyResponseException {
		return assetController.writeToDiskWrap(is, currentFile, storeInHashedDirectory, injection, bridgePath, ormmaPath, mraidPath);
	}

	/**
	 * Activate a listener
	 *
	 * @param event the event
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void activate(String event) {
		Logger.d(LOG_TAG, "activate: " + event);
		if (event.equalsIgnoreCase(Defines.Events.NETWORK_CHANGE)) {
			networkController.startNetworkListener();
		} else if (locationController.allowLocationServices() && event.equalsIgnoreCase(Defines.Events.LOCATION_CHANGE)) {
			locationController.startLocationListener();
		} else if (event.equalsIgnoreCase(Defines.Events.SHAKE)) {
			sensorController.startShakeListener();
		} else if (event.equalsIgnoreCase(Defines.Events.TILT_CHANGE)) {
			sensorController.startTiltListener();
		} else if (event.equalsIgnoreCase(Defines.Events.HEADING_CHANGE)) {
			sensorController.startHeadingListener();
		} else if (event.equalsIgnoreCase(Defines.Events.ORIENTATION_CHANGE)) {
			displayController.startConfigurationListener();
		}
	}

	/**
	 * Deactivate a listener
	 *
	 * @param event the event
	 *
	 * NOTE: This method is used from JavaScript API
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void deactivate(String event) {
		Logger.d(LOG_TAG, "deactivate: " + event);
		if (event.equalsIgnoreCase(Defines.Events.NETWORK_CHANGE)) {
			networkController.stopNetworkListener();
		} else if (event.equalsIgnoreCase(Defines.Events.LOCATION_CHANGE)) {
			locationController.stopAllListeners();
		} else if (event.equalsIgnoreCase(Defines.Events.SHAKE)) {
			sensorController.stopShakeListener();
		} else if (event.equalsIgnoreCase(Defines.Events.TILT_CHANGE)) {
			sensorController.stopTiltListener();
		} else if (event.equalsIgnoreCase(Defines.Events.HEADING_CHANGE)) {
			sensorController.stopHeadingListener();
		} else if (event.equalsIgnoreCase(Defines.Events.ORIENTATION_CHANGE)) {
			displayController.stopConfigurationListener();
		}
	}

	/**
	 * Delete old ads.
	 */
	public void deleteOldAds() {
		assetController.deleteOldAds();
	}

	/* (non-Javadoc)
	 * @see com.ormma.controller.OrmmaController#stopAllListeners()
	 */
	@Override
	public void stopAllListeners() {
		try {
			assetController.stopAllListeners();
			displayController.stopAllListeners();
			locationController.stopAllListeners();
			networkController.stopAllListeners();
			sensorController.stopAllListeners();
		} catch (Exception e) {
			//Do nothing
		}
	}

	/**
	 * TODO: Remove this method
	 * @param message error message
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void showAlert(final String message) {
		Logger.e(LOG_TAG, message);
	}
}
