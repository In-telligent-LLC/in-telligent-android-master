package aje.android.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;

import org.ormma.controller.OrmmaException;
import org.ormma.view.OrmmaView;

/*
As soon as our WebView is fixed in size and it is not scrollable, we have two ways to render banners:
    1. Do not show an ad if it larger than the view.
        As it said in 'Google AdMob Ads Android SDK' they use this way.
    2. Try to scale out the content to fit to the view size.
*/
public class AdJugglerAdView extends OrmmaView {
	private static LogLevel logLevel;

    public AdJugglerAdView(Context context) throws OrmmaException {
        super(context);
        init();
    }

    public AdJugglerAdView(Context context, AttributeSet attrs) throws OrmmaException {
        super(context, attrs);
        init();
    }

    public AdJugglerAdView(Context context, AttributeSet attrs, int defStyle) throws OrmmaException {
        super(context, attrs, defStyle);
        init();
    }

	@Override
    public void setListener(AdListener listener) {
        super.setListener(listener);
    }

    /**
     * The main SDK method that calls the ad and shows it
     *
     * @param adRequest contains parameters of the ad call
     * @throws IncorrectAdRequestException happens if the ad request parameters are incorrect
     */
    public void showAd(AdRequest adRequest) throws IncorrectAdRequestException {
        String adRequestCode = adRequest.generateAdRequestUrl();
        loadAd(adRequestCode);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void init() {
        getSettings().setSupportZoom(false);
        getSettings().setSupportMultipleWindows(false);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setUseWideViewPort(false);
    }

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}
}
