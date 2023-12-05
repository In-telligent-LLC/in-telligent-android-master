/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

/**
 *
 * This is the view to place into a layout to implement ormma functionality.
 * It can be used either via xml or programatically
 *
 * It is a subclass of the standard WebView which brings with it all the standard
 * functionality as well as the inherent bugs on some os versions.
 *
 * Webviews have a tendency to leak on orientation in older versions of the android OS
 * this can be minimized by using an application context, but this will break the launching
 * of subwindows (such as alert calls from javascript)
 *
 *
 */
package org.ormma.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import org.ormma.controller.OrmmaAssetController;
import org.ormma.controller.OrmmaController.Dimensions;
import org.ormma.controller.OrmmaController.PlayerProperties;
import org.ormma.controller.OrmmaController.Properties;
import org.ormma.controller.OrmmaException;
import org.ormma.controller.OrmmaNetworkController;
import org.ormma.controller.OrmmaUtilityController;
import org.ormma.controller.util.OrmmaPlayer;
import org.ormma.controller.util.OrmmaPlayerListener;
import org.ormma.controller.util.OrmmaUtils;

import java.util.HashSet;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import aje.android.R;
import aje.android.sdk.AdListener;
import aje.android.sdk.AdNullListener;
import aje.android.sdk.Logger;

/**
 * This is the view to place into a layout to implement ormma functionality. It
 * can be used either via xml or programatically
 * <p/>
 * It is a subclass of the standard WebView which brings with it all the
 * standard functionality as well as the inherent bugs on some os versions.
 * <p/>
 * Webviews have a tendency to leak on orientation in older versions of the
 * android OS this can be minimized by using an application context, but this
 * will break the launching of subwindows (such as alert calls from javascript)
 * <p/>
 * It is important to not use any of the layout constants elsewhere in the same
 * view as things will get confused. Normally this is not an issue as generated
 * layout constants will not interfere.
 */
@SuppressWarnings("unused")
public class OrmmaView extends WebView implements OnGlobalLayoutListener {

	private static final String LOG_TAG = "OrmmaView";

	/**
	 * enum representing possible view states
	 */
	public enum ViewState {
		DEFAULT, RESIZED, EXPANDED, HIDDEN, LEFT_BEHIND, OPENED
	}

	// static for accessing xml attributes
	private static int[] attrs = {android.R.attr.maxWidth, android.R.attr.maxHeight};

	// Messaging constants
	private static final int MESSAGE_RESIZE = 1000;
	private static final int MESSAGE_CLOSE = 1001;
	private static final int MESSAGE_HIDE = 1002;
	private static final int MESSAGE_SHOW = 1003;
	private static final int MESSAGE_EXPAND = 1004;
	private static final int MESSAGE_SEND_EXPAND_CLOSE = 1005;
	private static final int MESSAGE_OPEN = 1006;
	private static final int MESSAGE_PLAY_VIDEO = 1007;
	private static final int MESSAGE_PLAY_AUDIO = 1008;
	private static final int MESSAGE_RAISE_ERROR = 1009;

	// Extra constants
	public static final String DIMENSIONS = "expand_dimensions";
	public static final String PLAYER_PROPERTIES = "player_properties";
	public static final String EXPAND_URL = "expand_url";
	public static final String ACTION_KEY = "action";
	private static final String EXPAND_PROPERTIES = "expand_properties";
	private static final String RESIZE_WIDTH = "resize_width";
	private static final String RESIZE_HEIGHT = "resize_height";
	protected static final String CURRENT_FILE = "_ormma_current";
	private static final String AD_PATH = "AD_PATH";
	private static final String ERROR_MESSAGE = "message";
	private static final String ERROR_ACTION = "action";

	// Debug message constant
	private static final String TAG = "OrmmaView";

	// layout constants
	protected static final int BACKGROUND_ID = 101;

	protected AdListener adEventsListener = AdNullListener.instance;

	// private constants
	static String ormmaScriptPath; // holds the path for the ormma.js
	static String mraidScriptPath; // holds the path for the mraid.js
	static String ormmaBridgeScriptPath; // holds the path for the
	// ormma_bridge.js
	private boolean pageLoaded; // boolean flag holding the loading
	// state of a page
	private OrmmaUtilityController utilityController; // primary javascript

	// bridge
	private float density; // screen pixel density
	private int contentViewHeight; // height of the content
	private boolean keyboardOut; // state of the keyboard
	private int defaultHeight; // default height of the view
	private int defaultWidth; // default width of the view
	private int initLayoutHeight; // initial height of the view
	private int initLayoutWidth; // initial height of the view
	private int index; // index of the view within its viewgroup
	private GestureDetector gestureDetector; // gesture detector for capturing
	// unwanted gestures
	private ViewState viewState = ViewState.DEFAULT; // holds current view
	// state
	private static OrmmaPlayer player;
	// (back to the parent)
	String localFilePath; // local path the the ad html

	// URL Protocols registered by the client.
	// if such a protocol is encountered then
	// shouldOverrideUrlLoading will forward the url to the listener
	// by calling handleRequest
	// Should this be a static variable?
	private final HashSet<String> registeredProtocols = new HashSet<String>();

	private boolean bGotLayoutParams;

	private ThreadPoolExecutor urlLoadThread = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10000));
	Object syncUrlLoad = new Object(); 
	
	public enum ACTION {
		PLAY_AUDIO, PLAY_VIDEO
	}

	/**
	 * Sets the listener.
	 *
	 * @param listener the new listener
	 */
	public void setListener(AdListener listener) {
		adEventsListener = listener;
	}

	/**
	 * Removes the listener.
	 */
	public void removeListener() {
		adEventsListener = null;
	}

	/**
	 * Instantiates a new ormma view.
	 *
	 * @param context  the context
	 * @param listener the listener
	 * @throws org.ormma.controller.OrmmaException creation failed
	 */
	public OrmmaView(Context context, AdListener listener) throws OrmmaException {
		super(context);
		setListener(listener);
		initialize();
	}

	/**
	 * Instantiates a new ormma view.
	 *
	 * @param context the context
	 * @throws org.ormma.controller.OrmmaException creation failed
	 */
	public OrmmaView(Context context) throws OrmmaException {
		super(context);
		initialize();
	}

	public OrmmaView(Context context, AttributeSet attrs, int defStyle) throws OrmmaException {
		super(context, attrs, defStyle);
		initialize();
	}

	/**
	 * Sets the max size.
	 *
	 * @param w the width
	 * @param h the height
	 */
	public void setMaxSize(int w, int h) {
		utilityController.setMaxSize(w, h);
	}

	/**
	 * Register a protocol
	 *
	 * @param protocol the protocol to be registered
	 */

	public void registerProtocol(String protocol) {
		if (protocol != null) {
			registeredProtocols.add(protocol.toLowerCase());
		}
	}

	/**
	 * Deregister a protocol
	 *
	 * @param protocol the protocol to be de registered
	 */

	public void deregisterProtocol(String protocol) {
		if (protocol != null) {
			registeredProtocols.remove(protocol.toLowerCase());
		}
	}

	/**
	 * Is Protocol Registered
	 *
	 * @param uri The uri
	 * @return true , if the url's protocol is registered by the user, else
	 *         false if scheme is null or not registered
	 */
	private boolean isRegisteredProtocol(Uri uri) {

		String scheme = uri.getScheme();
		if (scheme == null) {
			return false;
		}

		for (String protocol : registeredProtocols) {
			if (protocol.equalsIgnoreCase(scheme)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inject java script into the view
	 *
	 * @param str the java script to inject
	 */
	public void injectJavaScript(String str) {
		if (str != null) {
			super.loadUrl("javascript:" + str);
		}
	}

	/*
	 * @see android.webkit.WebView#loadUrl(java.lang.String)
	 */
	@Override
	public void loadUrl(String url) {
		try {
			loadUrl(url, false, null);
		} catch (Throwable e) {
			Logger.e(LOG_TAG, "Cannot load URL", e);
		}
	}
	
	public void loadAd(String url) {
		try {
			loadUrl(url, true, null);
		} catch (Throwable e) {
			Logger.e(LOG_TAG, "Cannot load Ad", e);
		}
	}	

	/**
	 * The Class TimeOut. A timertask for terminating the load if it takes too
	 * long If it fires three times without making progress, it will cancel the
	 * load
	 */
	class TimeOut extends TimerTask {

		int mProgress = 0;
		int mCount = 0;

		@Override
		public void run() {
			int progress = getProgress();
			if (progress == 100) {
				this.cancel();
			} else {
				if (mProgress == progress) {
					mCount++;
					if (mCount == 3) {
						try {
							stopLoading();
						} catch (Exception e) {
							Logger.w(TAG, "error in stopLoading");
						}
						this.cancel();
					}
				}
			}
			mProgress = progress;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void clearView() {
		reset();
		super.clearView();
	}

	/**
	 * Reset the view.
	 */
	void reset() {
		if (viewState == ViewState.EXPANDED) {
			closeExpanded();
		} else if (viewState == ViewState.RESIZED) {
			closeResized();
		}
		invalidate();
		utilityController.stopAllListeners();
		resetLayout();
	}

	private void resetCache () {
		utilityController.deleteOldAds();
	}

	/**
	 * Load url.
	 *
	 * @param url          the url
	 * @throws org.ormma.controller.OrmmaException operation failed
	 */
	private void loadUrl(final String url, final boolean sendEvents, Runnable onComplete) {
		if (URLUtil.isValidUrl(url)) {
			urlLoadThread.execute(new URLLoadTask(url, this, utilityController, sendEvents, onComplete));
		} else {
			super.loadUrl(url);
		}
	}

	void loadUrlInternal(String url) {
		super.loadUrl(url);
	}
	
	/**
	 * Instantiates a new ormma view.
	 *
	 * @param context the context
	 * @param set     the set
	 * @throws org.ormma.controller.OrmmaException creation failed
	 */
	@SuppressWarnings("ResourceType")
	public OrmmaView(Context context, AttributeSet set) throws OrmmaException {
		super(context, set);

		initialize();

		TypedArray a = getContext().obtainStyledAttributes(set, attrs);

		int w = a.getDimensionPixelSize(0, -1);
		int h = a.getDimensionPixelSize(1, -1);

		if (w > 0 && h > 0) {
			utilityController.setMaxSize(w, h);
		}

		a.recycle();

	}

	/** {@inheritDoc} */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean ret = gestureDetector.onTouchEvent(ev);
		if (ret) {
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * The message handler. To keep things in the ui thread.
	 */
	private Handler mHandler = new Handler() {

		/** {@inheritDoc} */
		@Override
		public void handleMessage(Message msg) {

			Bundle data = msg.getData();
			switch (msg.what) {
				case MESSAGE_SEND_EXPAND_CLOSE:
					if (adEventsListener != null) {
						adEventsListener.onExpandClose();
					}
					break;
				case MESSAGE_RESIZE: {
					viewState = ViewState.RESIZED;
					ViewGroup.LayoutParams lp = getLayoutParams();
					lp.height = data.getInt(RESIZE_HEIGHT, lp.height);
					lp.width = data.getInt(RESIZE_WIDTH, lp.width);
					String injection = "window.ormmaview.fireChangeEvent({ state: \'resized\',"
							+ " size: { width: "
							+ lp.width
							+ ", "
							+ "height: "
							+ lp.height + "}});";
					injectJavaScript(injection);
					requestLayout();
					if (adEventsListener != null) {
						adEventsListener.onResize();
					}
					break;
				}
				case MESSAGE_CLOSE: {
					switch (viewState) {
						case RESIZED:
							closeResized();
							break;
						case EXPANDED:
							closeExpanded();
							break;
					}

					break;
				}
				case MESSAGE_HIDE: {
					setVisibility(View.INVISIBLE);
					String injection = "window.ormmaview.fireChangeEvent({ state: \'hidden\' });";

					injectJavaScript(injection);
					break;
				}
				case MESSAGE_SHOW: {
					String injection = "window.ormmaview.fireChangeEvent({ state: \'default\' });";
					injectJavaScript(injection);
					setVisibility(View.VISIBLE);
					break;
				}
				case MESSAGE_EXPAND: {
					doExpand(data);
					break;
				}
				case MESSAGE_OPEN: {
					viewState = ViewState.LEFT_BEHIND;
					break;
				}

				case MESSAGE_PLAY_AUDIO: {
					playAudioImpl(data);
					break;
				}

				case MESSAGE_PLAY_VIDEO: {
					playVideoImpl(data);
					break;
				}
				case MESSAGE_RAISE_ERROR:
					String strMsg = data.getString(ERROR_MESSAGE);
					String action = data.getString(ERROR_ACTION);
					String injection = "window.ormmaview.fireErrorEvent(\"" + strMsg + "\", \"" + action + "\")";
					injectJavaScript(injection);
					break;
			}
			super.handleMessage(msg);
		}

	};

	FrameLayout placeHolder;

	/**
	 * Change ad display to new dimensions
	 *
	 * @param d - display dimensions
	 * @return Frame layout
	 */
	@SuppressWarnings("ResourceType")
	private FrameLayout changeContentArea(Dimensions d, boolean useCustomClose) {

		FrameLayout contentView = (FrameLayout) getRootView().findViewById(android.R.id.content);

		ViewGroup parent = (ViewGroup) getParent();
		FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(d.width, d.height);
		fl.topMargin = d.x;
		fl.leftMargin = d.y;

		int index;
		int count = parent.getChildCount();
		for (index = 0; index < count; index++) {
			if (parent.getChildAt(index) == OrmmaView.this) {
				break;
			}
		}
		this.index = index;
		placeHolder = new FrameLayout(getContext());

		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(getWidth(), getHeight());
		parent.addView(placeHolder, index, lp);
		parent.removeView(OrmmaView.this);

		FrameLayout background = new FrameLayout(getContext());

		background.setOnTouchListener(new OnTouchListener() {
			/** {@inheritDoc} */
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Logger.i("ORMMA", "background touch called");
				return true;
			}
		});

		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT);
		background.setId(BACKGROUND_ID);
		background.setPadding(d.x, d.y, 0, 0);
		background.addView(OrmmaView.this, fl);

		if (!useCustomClose) {
			ImageButton closeButton = new ImageButton(getContext());
			closeButton.setImageResource(R.drawable.close);
			closeButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
			closeButton.setClickable(true);
			closeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					closeExpanded();
				}
			});
			WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
					WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
					PixelFormat.TRANSLUCENT);
			params.gravity = Gravity.RIGHT | Gravity.TOP;
			params.setTitle("Load Average");

			background.addView(closeButton, 50, 50);
		}

		contentView.addView(background, layoutParams);

		return background;
	}

	private void doExpand(Bundle data) {
		final Dimensions d = (Dimensions) data.getParcelable(DIMENSIONS);
		final String url = data.getString(EXPAND_URL);
		final Properties p = data.getParcelable(EXPAND_PROPERTIES);

		Runnable onComplete = new Runnable() {
			@Override
			public void run() {
				FrameLayout background = changeContentArea(d, p.useCustomClose);

				if (p.useBackground) {
					int color = p.backgroundColor | ((int) (p.backgroundOpacity * 0xFF) * 0x10000000);
					background.setBackgroundColor(color);
				}

				String injection = "window.ormmaview.fireChangeEvent({ state: \'expanded\',"
						+ " size: "
						+ "{ width: "
						+ (int) (d.width / density)
						+ ", "
						+ "height: " + (int) (d.height / density) + "}" + " });";
				Logger.d(LOG_TAG, "doExpand: injection: " + injection);
				injectJavaScript(injection);
				adEventsListener.onExpand();

				viewState = ViewState.EXPANDED;
			}
		};
		
		if (URLUtil.isValidUrl(url)) {
			loadUrl(url, false, onComplete);
		} else {
			onComplete.run();
		}
	}

	/**
	 * Close resized.
	 */
	private void closeResized() {
		if (adEventsListener != null) {
			adEventsListener.onResizeClose();
		}
		String injection = "window.ormmaview.fireChangeEvent({ state: \'default\',"
				+ " size: "
				+ "{ width: "
				+ defaultWidth
				+ ", "
				+ "height: "
				+ defaultHeight + "}" + "});";
		Logger.d(LOG_TAG, "closeResized: injection: " + injection);
		injectJavaScript(injection);
		resetLayout();
	}

	/**
	 * The webview client used for trapping certain events
	 */
	WebViewClient mWebViewClient = new WebViewClient() {
		/** {@inheritDoc} */
		@Override
		public void onReceivedError(WebView view, int errorCode,
		                            String description, String failingUrl) {
			Logger.d("OrmmaView", "error:" + description);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		/** {@inheritDoc} */
		@Override
		public void onPageFinished(WebView view, String url) {
			defaultHeight = (int) (getHeight() / density);
			defaultWidth = (int) (getWidth() / density);

			utilityController.init(density);
			
			synchronized (syncUrlLoad) {
				syncUrlLoad.notifyAll();
			}
		}

		/** {@inheritDoc} */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Uri uri = Uri.parse(url);
			try {
				// If the protocol is registered then forward it to listener.
				if (adEventsListener != null && adEventsListener.onClickAd(url)) {
					return true;
				}

				if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.parse(url));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getContext().startActivity(intent);
					return true;
				}

				if (url.startsWith("mailto:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getContext().startActivity(intent);
					return true;
				}

				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setData(uri);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
				return true;

			} catch (Exception e) {
				try {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					intent.setData(uri);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getContext().startActivity(intent);
					return true;
				} catch (Exception e2) {
					adEventsListener.onFailedToClickAd(url, e.getMessage());
					return false;
				}
			}

		}

		/** {@inheritDoc} */
		@Override
		public void onLoadResource(WebView view, String url) {
			Logger.d(TAG, "lr:" + url);
		}

	};

	/**
	 * The m web chrome client.
	 */
	WebChromeClient mWebChromeClient = new WebChromeClient() {
		/** {@inheritDoc} */
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
		                         JsResult result) {
			Logger.d("OrmmaView", message);
			return false;
		}
	};

	@SuppressLint("JavascriptInterface")
    private void initialize() throws OrmmaException {

		setScrollContainer(false);
		setVerticalScrollBarEnabled(false);
		setHorizontalScrollBarEnabled(false);
		gestureDetector = new GestureDetector(new ScrollEater());

		setBackgroundColor(0);
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		wm.getDefaultDisplay().getMetrics(metrics);
		density = metrics.density;

		pageLoaded = false;

		getSettings().setJavaScriptEnabled(true);

		utilityController = new OrmmaUtilityController(this, this.getContext());
		OrmmaNetworkController networkController = new OrmmaNetworkController(this, this.getContext());
		OrmmaAssetController assetController = new OrmmaAssetController(this, this.getContext());

		addJavascriptInterface(utilityController, "ORMMAUtilityControllerBridge");
		addJavascriptInterface(networkController, "ORMMANetworkControllerBridge");
		addJavascriptInterface(assetController, "ORMMAAssetControllerBridge");

		setWebViewClient(mWebViewClient);

		setWebChromeClient(mWebChromeClient);
		setScriptPath();

		contentViewHeight = getContentViewHeight();

		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@SuppressLint("JavascriptInterface")
    public void addJavascriptObject(Object obj, String name) {
		addJavascriptInterface(obj, name);
	}

	/**
	 * Gets the content view height.
	 *
	 * @return the content view height
	 */
	private int getContentViewHeight() {
		View contentView = getRootView().findViewById(android.R.id.content);
		if (contentView != null) {
			return contentView.getHeight();
		} else {
			return -1;
		}
	}

	/**
	 * Sets the script path.
	 * @throws org.ormma.controller.OrmmaException operation failed
	 */
	private synchronized void setScriptPath() throws OrmmaException {
		if (ormmaScriptPath == null) {
			ormmaScriptPath = utilityController.copyTextFromJarIntoAssetDir(
					"/js/ormma.js", "js/ormma.js");
		}
		if (mraidScriptPath == null) {
			mraidScriptPath = utilityController.copyTextFromJarIntoAssetDir(
					"/js/mraid.js", "js/mraid.js");
		}
		if (ormmaBridgeScriptPath == null) {
			ormmaBridgeScriptPath = utilityController.copyTextFromJarIntoAssetDir(
					"/js/ormma_bridge.js", "js/ormma_bridge.js");
		}
	}

	/**
	 * Close an expanded view.
	 */
	protected synchronized void closeExpanded() {

		resetContents();

		String injection = "window.ormmaview.fireChangeEvent({ state: \'default\',"
				+ " size: "
				+ "{ width: "
				+ defaultWidth
				+ ", "
				+ "height: "
				+ defaultHeight + "}" + "});";
		Logger.d(LOG_TAG, "closeExpanded: injection: " + injection);
		injectJavaScript(injection);

		viewState = ViewState.DEFAULT;

		mHandler.sendEmptyMessage(MESSAGE_SEND_EXPAND_CLOSE);
		setVisibility(VISIBLE);
	}

	/**
	 * Close an opened view.
	 *
	 * @param openedFrame the opened frame
	 */
	protected void closeOpened(View openedFrame) {
		((ViewGroup) ((Activity) getContext()).getWindow().getDecorView())
				.removeView(openedFrame);
		requestLayout();
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return viewState.toString().toLowerCase();
	}

	/**
	 * Resize the view
	 *
	 * @param width  the width
	 * @param height the height
	 */
	public void resize(int width, int height) {
		Message msg = mHandler.obtainMessage(MESSAGE_RESIZE);

		Bundle data = new Bundle();
		data.putInt(RESIZE_WIDTH, width);
		data.putInt(RESIZE_HEIGHT, height);
		msg.setData(data);

		mHandler.sendMessage(msg);
	}

	/**
	 * Close the view
	 */
	public void close() {
		mHandler.sendEmptyMessage(MESSAGE_CLOSE);
	}

	/**
	 * Hide the view
	 */
	public void hide() {
		mHandler.sendEmptyMessage(MESSAGE_HIDE);
	}

	/**
	 * Show the view
	 */
	public void show() {
		mHandler.sendEmptyMessage(MESSAGE_SHOW);
	}

	/**
	 * Gets the connectivity manager.
	 *
	 * @return the connectivity manager
	 */
	public ConnectivityManager getConnectivityManager() {
		return (ConnectivityManager) this.getContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * creates an expand message and throws it to the handler for the real work
	 *
	 * @param dimensions the dimensions
	 * @param url        the uRL
	 * @param properties the properties
	 */
	public void expand(Dimensions dimensions, String url, Properties properties) {
		Message msg = mHandler.obtainMessage(MESSAGE_EXPAND);

		Bundle data = new Bundle();
		data.putParcelable(DIMENSIONS, dimensions);
		data.putString(EXPAND_URL, url);
		data.putParcelable(EXPAND_PROPERTIES, properties);
		msg.setData(data);

		mHandler.sendMessage(msg);
	}

	/**
	 * Open.
	 *
	 * @param url     the url
	 * @param back    show the back button
	 * @param forward show the forward button
	 * @param refresh show the refresh button
	 */
	public void open(String url, boolean back, boolean forward, boolean refresh) {

		Intent i = new Intent(getContext(), Browser.class);
		Logger.d(TAG, "open:" + url);
		i.putExtra(Browser.URL_EXTRA, url);
		i.putExtra(Browser.SHOW_BACK_EXTRA, back);
		i.putExtra(Browser.SHOW_FORWARD_EXTRA, forward);
		i.putExtra(Browser.SHOW_REFRESH_EXTRA, refresh);
		i.putExtra(Browser.SHOW_OPEN_EXTRA, true);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(i);

	}

	public void openMap(String POI, boolean fullscreen) throws OrmmaException {

		Logger.d(TAG, "Opening Map Url " + POI);

		POI = POI.trim();
		POI = OrmmaUtils.convert(POI);

		if (fullscreen) {
			try {
				// start google maps
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(POI));
				mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				getContext().startActivity(mapIntent);

			} catch (ActivityNotFoundException e) {
				throw new OrmmaException("Couldn't open map", e);
			}
		} else {
			throw new OrmmaException("Embedded map is not supported");
		}
	}


	public void playAudioImpl(Bundle data) {

		PlayerProperties properties = (PlayerProperties) data
				.getParcelable(PLAYER_PROPERTIES);

		String url = data.getString(EXPAND_URL);

		OrmmaPlayer audioPlayer = getPlayer();
		audioPlayer.setPlayData(properties, url);
		audioPlayer.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
		((ViewGroup) getParent()).addView(audioPlayer);

		audioPlayer.playAudio();

	}

	/**
	 * Play audio
	 *
	 * @param url        - audio URL
	 * @param autoPlay   - should audio play immediately
	 * @param controls   - should native controls be visible
	 * @param loop       - should audio start over again after finishing
	 * @param position   - should audio be included with ad content
	 * @param startStyle - normal/fullscreen; full screen if audio should play in full
	 *                   screen
	 * @param stopStyle  - normal/exit; exit if audio should exit after audio stops
	 * @throws org.ormma.controller.OrmmaException operation failed
	 */
	public void playAudio(String url, boolean autoPlay, boolean controls,
	                      boolean loop, boolean position, String startStyle, String stopStyle) throws OrmmaException {

		PlayerProperties properties = new PlayerProperties();

		properties.setProperties(false, autoPlay, controls, position, loop, startStyle,
				stopStyle);

		Bundle data = new Bundle();

		data.putString(ACTION_KEY, ACTION.PLAY_AUDIO.toString());
		data.putString(EXPAND_URL, url);
		data.putParcelable(PLAYER_PROPERTIES, properties);

		if (properties.isFullScreen()) {
			try {
				Intent intent = new Intent(getContext(), OrmmaActionHandler.class);
				intent.putExtras(data);
				getContext().startActivity(intent);
			} catch (ActivityNotFoundException e) {
				throw new OrmmaException("Couldn't play audio", e);
			}
		} else {
			Message msg = mHandler.obtainMessage(MESSAGE_PLAY_AUDIO);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
	}

	@SuppressWarnings("ResourceType")
	public void playVideoImpl(Bundle data) {

		PlayerProperties properties = (PlayerProperties) data
				.getParcelable(PLAYER_PROPERTIES);
		Dimensions d = (Dimensions) data.getParcelable(DIMENSIONS);
		String url = data.getString(EXPAND_URL);

		OrmmaPlayer videoPlayer = getPlayer();
		videoPlayer.setPlayData(properties, url);

		FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(
				d.width, d.height);
		fl.topMargin = d.x;
		fl.leftMargin = d.y;
		videoPlayer.setLayoutParams(fl);

		FrameLayout backGround = new FrameLayout(getContext());
		backGround.setOnTouchListener(new OnTouchListener() {
			/** {@inheritDoc} */
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Logger.i("ORMMA", "background touch called");
				return true;
			}
		});
		backGround.setId(BACKGROUND_ID);
		backGround.setPadding(d.x, d.y, 0, 0);

		FrameLayout contentView = (FrameLayout) getRootView().findViewById(
				android.R.id.content);
		contentView.addView(backGround, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));

		backGround.addView(videoPlayer);
		setVisibility(View.INVISIBLE);

		videoPlayer.setListener(new OrmmaPlayerListener() {
			/** {@inheritDoc} */
			@Override
			public void onPrepared() {
			}

			/** {@inheritDoc} */
			@Override
			public void onError() {
				onComplete();
			}

			/** {@inheritDoc} */
			@Override
			public void onComplete() {
				FrameLayout background = (FrameLayout) getRootView()
						.findViewById(BACKGROUND_ID);
				if (background != null && background.getParent() != null) {
					((ViewGroup) background.getParent()).removeView(background);
				}
				setVisibility(View.VISIBLE);
			}
		});

		videoPlayer.playVideo();

	}

	/**
	 * Play video
	 *
	 * @param url        - video URL
	 * @param audioMuted - should audio be muted
	 * @param autoPlay   - should video play immediately
	 * @param controls   - should native player controls be visible
	 * @param loop       - should video start over again after finishing
	 * @param d          - inline area dimensions
	 * @param startStyle - normal/fullscreen; full screen if video should play in full
	 *                   screen
	 * @param stopStyle  - normal/exit; exit if video should exit after video stops
	 * @throws org.ormma.controller.OrmmaException operation failed
	 */
	public void playVideo(String url, boolean audioMuted, boolean autoPlay,
	                      boolean controls, boolean loop, Dimensions d, String startStyle,
	                      String stopStyle) throws OrmmaException {

		Message msg = mHandler.obtainMessage(MESSAGE_PLAY_VIDEO);

		PlayerProperties properties = new PlayerProperties();

		properties.setProperties(audioMuted, autoPlay, controls, false, loop,
				startStyle, stopStyle);

		Bundle data = new Bundle();
		data.putString(EXPAND_URL, url);
		data.putString(ACTION_KEY, ACTION.PLAY_VIDEO.toString());

		data.putParcelable(PLAYER_PROPERTIES, properties);

		if (d != null) {
			data.putParcelable(DIMENSIONS, d);
		}

		if (properties.isFullScreen()) {
			try {
				Intent intent = new Intent(getContext(), OrmmaActionHandler.class);
				intent.putExtras(data);
				getContext().startActivity(intent);
			} catch (ActivityNotFoundException e) {
				throw new OrmmaException("Couldn't play video", e);
			}
		} else if (d != null) {
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
	}

	/**
	 * Revert to earlier ad state
	 */
	@SuppressWarnings("ResourceType")
	public void resetContents() {

		FrameLayout contentView = (FrameLayout) getRootView().findViewById(
				android.R.id.content);

		FrameLayout background = (FrameLayout) getRootView().findViewById(
				BACKGROUND_ID);
		ViewGroup parent = (ViewGroup) placeHolder.getParent();
		background.removeView(this);
		contentView.removeView(background);
		resetLayout();
		parent.addView(this, index);
		parent.removeView(placeHolder);
		parent.invalidate();
	}

	/**
	 * The Class NewLocationReciever.
	 */
	public static abstract class NewLocationReceiver {

		/**
		 * On new location.
		 *
		 * @param v the v
		 */
		public abstract void OnNewLocation(ViewState v);
	}

	/**
	 * Reset layout.
	 */
	private void resetLayout() {
		ViewGroup.LayoutParams lp = getLayoutParams();
		if (bGotLayoutParams) {
			lp.height = initLayoutHeight;
			lp.width = initLayoutWidth;
		}
		setVisibility(VISIBLE);
		requestLayout();
	}

	/**
	 * Checks if is page finished.
	 *
	 * @return true, if is page finished
	 */
	public boolean isPageFinished() {
		return pageLoaded;
	}

	/** {@inheritDoc} */
	@Override
	public void onGlobalLayout() {
		boolean state = keyboardOut;
		if (!keyboardOut && contentViewHeight >= 0
				&& getContentViewHeight() >= 0
				&& (contentViewHeight != getContentViewHeight())) {

			state = true;
			String injection = "window.ormmaview.fireChangeEvent({ keyboardState: true});";
			injectJavaScript(injection);

		}
		if (keyboardOut && contentViewHeight >= 0
				&& getContentViewHeight() >= 0
				&& (contentViewHeight == getContentViewHeight())) {

			state = false;
			String injection = "window.ormmaview.fireChangeEvent({ keyboardState: false});";
			injectJavaScript(injection);
		}
		if (contentViewHeight < 0) {
			contentViewHeight = getContentViewHeight();
		}

		keyboardOut = state;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		return "{ width: " + (int) (getWidth() / density) + ", " + "height: "
				+ (int) (getHeight() / density) + "}";
	}

	/** {@inheritDoc} */
	@Override
	protected void onAttachedToWindow() {
		if (!bGotLayoutParams) {
			ViewGroup.LayoutParams lp = getLayoutParams();
			initLayoutHeight = lp.height;
			initLayoutWidth = lp.width;
			bGotLayoutParams = true;
		}
		super.onAttachedToWindow();
	}

	/** {@inheritDoc} */
	@Override
	public WebBackForwardList saveState(Bundle outState) {
		outState.putString(AD_PATH, localFilePath);
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public WebBackForwardList restoreState(Bundle savedInstanceState) {

		localFilePath = savedInstanceState.getString(AD_PATH);

		String url = "file://" + localFilePath + java.io.File.separator
				+ CURRENT_FILE;
		super.loadUrl(url);

		return null;
	}

	/**
	 * The Class ScrollEater.
	 */
	class ScrollEater extends SimpleOnGestureListener {

		/** {@inheritDoc} */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
		                        float distanceX, float distanceY) {
			return true;
		}
	}


	public void raiseError(String strMsg, String action) {

		Message msg = mHandler.obtainMessage(MESSAGE_RAISE_ERROR);

		Bundle data = new Bundle();
		data.putString(ERROR_MESSAGE, strMsg);
		data.putString(ERROR_ACTION, action);
		msg.setData(data);
		mHandler.sendMessage(msg);
	}

	/**
	 * Checks if is expanded.
	 *
	 * @return true, if is expanded
	 */
	public boolean isExpanded() {
		return viewState == ViewState.EXPANDED;
	}

	/** {@inheritDoc} */
	@Override
	protected void onDetachedFromWindow() {
		utilityController.stopAllListeners();
		super.onDetachedFromWindow();
	}

	private OrmmaPlayer getPlayer() {
		if (player != null) {
			player.releasePlayer();
		}

		player = new OrmmaPlayer(getContext());
		return player;
	}
	
	@Override
	protected void finalize() {
		if (urlLoadThread != null) {
			urlLoadThread.shutdown();
		}
	}
}
