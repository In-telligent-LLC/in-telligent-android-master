package aje.android.sdk;

public enum AdError {
    /** AdJuggler returned an empty response (it is possible as result of the frequency capping or incorrect setup) **/
    EMPTY_RESPONSE,
    /** Network communication error (usually it is connection/read timeouts) **/
    NETWORK_ERROR,
    /** SDK can't parse the response **/
    INTERNAL_ERROR
}


