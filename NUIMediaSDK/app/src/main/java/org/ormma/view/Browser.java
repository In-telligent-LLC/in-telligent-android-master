/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package org.ormma.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.ormma.controller.OrmmaAssetController;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import aje.android.R;

/**
 * Activity for implementing Ormma open calls. Configurable via the following
 * extras: URL_EXTRA String : url to load SHOW_BACK_EXTRA boolean (default
 * false) : show the back button SHOW_FORWARD_EXTRA boolean (default false) :
 * show the forward button SHOW_REFRESH_EXTRA boolean (default false) : show the
 * prefresh button
 * <p/>
 * layout is constructed programatically
 */
public class Browser extends Activity {

	/**
	 * Extra Constants *
	 */
	public static final String URL_EXTRA = "extra_url";
	public static final String SHOW_BACK_EXTRA = "open_show_back";
	public static final String SHOW_FORWARD_EXTRA = "open_show_forward";
	public static final String SHOW_REFRESH_EXTRA = "open_show_refresh";
	public static final String SHOW_OPEN_EXTRA = "open_show_open";

	/**
	 * Layout Id constants.
	 */
	private static final int ButtonId = 100;
	private static final int WebViewId = 101;
	private static final int ForwardId = 102;
	private static final int BackwardId = 103;

	/** {@inheritDoc} */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Build the layout
		RelativeLayout rl = new RelativeLayout(this);
		WebView webview = new WebView(this);

		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);

		Intent i = getIntent();

		// Build the button bar
		LinearLayout bll = new LinearLayout(this);
		bll.setOrientation(LinearLayout.HORIZONTAL);
		bll.setId(ButtonId);
		bll.setWeightSum(100);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		lp.addRule(RelativeLayout.ABOVE, ButtonId);
		bll.setBackgroundResource(R.drawable.bkgrnd);
		rl.addView(webview, lp);

		lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rl.addView(bll, lp);

		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		lp2.weight = 20;
		lp2.gravity = Gravity.CENTER_VERTICAL;

		ImageButton backButton = new ImageButton(this);
		backButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		backButton.setId(BackwardId);

		bll.addView(backButton, lp2);
		if (!i.getBooleanExtra(SHOW_BACK_EXTRA, true)) {
			backButton.setVisibility(ViewGroup.GONE);
		}
		backButton.setImageResource(R.drawable.leftarrow);

		backButton.setOnClickListener(new OnClickListener() {
			/** {@inheritDoc} */
			@Override
			public void onClick(android.view.View arg0) {
				WebView wv = (WebView) findViewById(WebViewId);
				if (wv.canGoBack()) {
					wv.goBack();
				} else {
					Browser.this.finish();
				}
			}
		});

		ImageButton forwardButton = new ImageButton(this);
		forwardButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		forwardButton.setId(ForwardId);
		lp2 = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		lp2.weight = 20;
		lp2.gravity = Gravity.CENTER_VERTICAL;

		bll.addView(forwardButton, lp2);

		if (!i.getBooleanExtra(SHOW_FORWARD_EXTRA, true)) {
			forwardButton.setVisibility(ViewGroup.GONE);
		}

		forwardButton.setOnClickListener(new OnClickListener() {
			/** {@inheritDoc} */
			@Override
			public void onClick(android.view.View arg0) {
				WebView wv = (WebView) findViewById(WebViewId);
				wv.goForward();
			}
		});

		ImageButton refreshButton = new ImageButton(this);
		refreshButton.setImageResource(R.drawable.refresh);
		refreshButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		lp2 = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp2.weight = 20;
		lp2.gravity = Gravity.CENTER_VERTICAL;

		bll.addView(refreshButton, lp2);
		if (!i.getBooleanExtra(SHOW_REFRESH_EXTRA, true)) {
			refreshButton.setVisibility(ViewGroup.GONE);
		}

		refreshButton.setOnClickListener(new OnClickListener() {
			/** {@inheritDoc} */
			@Override
			public void onClick(android.view.View arg0) {
				WebView wv = (WebView) findViewById(WebViewId);
				wv.reload();
			}
		});

		ImageButton openButton = new ImageButton(this);
		openButton.setImageResource(R.drawable.openbrowser);
		openButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		lp2 = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp2.weight = 20;
		lp2.gravity = Gravity.CENTER_VERTICAL;

		bll.addView(openButton, lp2);
		if (!i.getBooleanExtra(SHOW_OPEN_EXTRA, true)) {
			openButton.setVisibility(ViewGroup.GONE);
		}

		openButton.setOnClickListener(new OnClickListener() {
			/** {@inheritDoc} */
			@Override
			public void onClick(android.view.View view) {
				WebView wv = (WebView) findViewById(WebViewId);
				
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wv.getUrl()));
				startActivity(myIntent);
			}
		});		
		
		ImageButton closeButton = new ImageButton(this);
		closeButton.setImageResource(R.drawable.close);
		closeButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		lp2 = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp2.weight = 20;
		lp2.gravity = Gravity.CENTER_VERTICAL;

		bll.addView(closeButton, lp2);

		closeButton.setOnClickListener(new OnClickListener() {
			/** {@inheritDoc} */
			@Override
			public void onClick(android.view.View arg0) {
				Browser.this.finish();
			}
		});

		// Show progress bar
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		// Enable cookies
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(i.getStringExtra(URL_EXTRA));
		webview.setId(WebViewId);

		webview.setWebViewClient(new WebViewClient() {
			/** {@inheritDoc} */
			@Override
			public void onReceivedError(WebView view, int errorCode,
			                            String description, String failingUrl) {
				Activity a = (Activity) view.getContext();
				Toast.makeText(a, "Ormma Error:" + description,
						Toast.LENGTH_SHORT).show();
			}

			/** {@inheritDoc} */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			/** {@inheritDoc} */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				ImageButton forwardButton = (ImageButton) findViewById(ForwardId);
				forwardButton
						.setImageResource(R.drawable.unrightarrow);
			}

			/** {@inheritDoc} */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				ImageButton forwardButton = (ImageButton) findViewById(ForwardId);

				// grey out buttons when appropriate
				if (view.canGoForward()) {
					forwardButton.setImageResource(R.drawable.rightarrow);
				} else {
					forwardButton.setImageResource(R.drawable.unrightarrow);
				}

			}
		});
		setContentView(rl);

		webview.setWebChromeClient(new WebChromeClient() {
			/** {@inheritDoc} */
			@Override
			public void onProgressChanged(WebView view, int progress) {
				// show progress bar while loading, url when loaded
				Activity a = (Activity) view.getContext();
				a.setTitle("Loading...");
				a.setProgress(progress * 100);
				if (progress == 100) {
					a.setTitle(view.getUrl());
				}
			}
		});

	}

	/** {@inheritDoc} */
	@Override
	protected void onPause() {
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	/** {@inheritDoc} */
	@Override
	protected void onResume() {
		super.onResume();
		CookieSyncManager.getInstance().startSync();
	}

}
