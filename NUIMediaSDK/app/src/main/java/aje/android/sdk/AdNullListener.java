package aje.android.sdk;

/**
 * The dummy listener that is used in the ORMMA bridge
 */
public class AdNullListener implements AdListener {
    public final static AdNullListener instance = new AdNullListener();

    private AdNullListener() {
    }

    @Override
    public void onFetchAdStarted() {
    }

    @Override
    public void onFetchAdFinished() {
    }

    @Override
    public void onFailedToFetchAd(AdError error, String message) {
    }

    @Override
    public void onFailedToClickAd(String url, String errorMessage) {
    }

    @Override
    public boolean onClickAd(String url) {
        return false;
    }

    @Override
    public void onExpandClose() {
    }

    @Override
    public void onResize() {
    }

    @Override
    public void onExpand() {
    }

    @Override
    public void onResizeClose() {
    }
}
