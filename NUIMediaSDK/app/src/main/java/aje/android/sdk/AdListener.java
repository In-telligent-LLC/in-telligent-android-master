package aje.android.sdk;

/**
 * Contains callbacks to watch the status of the ad call 
 */
public interface AdListener {
    /**
     * Called when ad call is started
     */
	void onFetchAdStarted();

    /**
     * Called when ad call is completely processed and the ad is shown
     */
    void onFetchAdFinished();

    /**
     * Called when error happens<br>
     * See {@link AdError} for the list of possible error events
     * 
     * @param error type of the error
     * @param message detailed error message
     */
    void onFailedToFetchAd(AdError error, String message);

    /**
     * Called when SDK can't open browser on ad click
     * 
     * @param url URL to show on click
     * @param errorMessage detailed error message
     */
    void onFailedToClickAd(String url, String errorMessage);

    /**
     * Called when user clicks on the ad<br>
     * Return <i>true</i> if you want to process click manually<br>
     * By default the browser is opened
     * 
     * @param url URL to show on click
     * @return false - open the browser with the specified URL<br>
     * true - don't open the browser with the specified URL, process click manually
     */
    boolean onClickAd(String url);

    /**
     * Called when banner is expanded via ORMMA scriptlet
     */
    void onExpand();

    /**
     * Called when user closes expanded banner
     */
    void onExpandClose();

    /**
     * Called when banner is resized via ORMMA scriptlet
     */
    void onResize();

    /**
     * Called when user closes resized banner
     */
    void onResizeClose();
}
