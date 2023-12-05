package aje.android.sdk;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class AdRequest {

    //query params names
    private static final String PARAM_DIM = "dim";
    private static final String PARAM_LAT = "lat";
    private static final String PARAM_LON = "lon";
    private static final String PARAM_PV = "pv";
    private static final String PARAM_ZONE = "z";
    private static final String PARAM_CHANNEL = "ch";
    private static final String PARAM_KEYWORDS = "kw";
    private static final String PARAM_ADTYPE = "adtype";
    private static final String PARAM_CLICK = "click";

    //for pv generation
    private static final Random rnd = new Random();

    //query params. linked - for tests sake. query params order will stay same
    Map<String, String> params = new LinkedHashMap<String, String>();

    private String proto = "http";
    private String server;
    private String adSpot;
    private String[] rotatorNames;
    private String aspect;
    private int page = 0;

    /**
     * Construct empty ad request, all fields are filled by default values
     */
    public AdRequest() {
    }

    /**
     * Construct the ad request by existed AdJuggler ad tag
     *
     * @param adTag the AdJuggler ad tag created by "XML Feed" template
     *
     * @throws IncorrectAdRequestException if the submitted request has incorrect format
     */
    public AdRequest(String adTag) throws IncorrectAdRequestException {
        parseTag(adTag);
    }

    /**
     * Add custom AdJuggler ad tag parameter in the request, for example "ct" (category)
     *
     * @param name name of the AdJuggler ad tag parameter
     * @param value value of the AdJuggler ad tag parameter
     */
    public void addParameter(String name, String value) {
        params.put(name, value);
    }

    private void checkParams() throws IncorrectAdRequestException {
        if (isEmpty(server)) {
            throw new IncorrectAdRequestException("Server isn't specified");
        }
        if (isEmpty(adSpot)) {
            throw new IncorrectAdRequestException("AdSpot isn't specified");
        }
        if (!params.containsKey(PARAM_ZONE)) {
            throw new IncorrectAdRequestException("Zone isn't specified");
        }
    }

    private void decodeQuery(String queryString) throws IncorrectAdRequestException {
        if (queryString != null) {
            for (String pair : queryString.split("&")) {
                int eq = pair.indexOf('=');
                if (eq < 0) {
                    // key with no value
                } else {
                    // key=value
                    String key = URLDecoder.decode(pair.substring(0, eq));
                    String value = URLDecoder.decode(pair.substring(eq + 1));
                    if (isEmpty(key)) {
                        throw new IncorrectAdRequestException("Empty key for value '" + value + "'");
                    }
                    addParameter(key, value);
                }
            }
        }
    }

    private String encodeQuery(StringBuilder sb) throws IncorrectAdRequestException {
        //check parameters required for query string
        if (!params.containsKey(PARAM_PV)) {
            params.put(PARAM_PV, getRandomPv());
        }

        for (Map.Entry<String, String> e : params.entrySet()) {
            try {
                sb.append(URLEncoder.encode(e.getKey(), "UTF-8"))
                .append("=")
                .append(URLEncoder.encode(e.getValue(), "UTF-8"))
                .append("&");
            } catch (UnsupportedEncodingException exception) {
                throw new IncorrectAdRequestException(exception);
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * Generate the AdJuggler ad tag by the provided parameters
     *
     * @return AdJuggler ad tag to call
     *
     * @throws IncorrectAdRequestException if required parameter is absent
     */
    public String generateAdRequestUrl() throws IncorrectAdRequestException {
        checkParams();

        StringBuilder sb = new StringBuilder();
        sb.append(proto).append("://").append(server);
        sb.append('/');
        for (String rotatorName : rotatorNames) {
            sb.append(rotatorName).append('/');
        }
        sb.append(adSpot).append('/').append(page).append('/').append(aspect).append('?');
        encodeQuery(sb);
        return sb.toString();
    }

    /**
     * Set geo coordinates
     *
     * @param latitude
     * @param longitude
     */
    public void setGeo(double latitude, double longitude) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(6);
        decimalFormat.setMaximumIntegerDigits(4);

        addParameter(PARAM_LAT, decimalFormat.format(latitude));
        addParameter(PARAM_LON, decimalFormat.format(longitude));
    }

    private void parseTag(String tag) throws IncorrectAdRequestException {
        try {
            URL url = new URL(tag);

            server = url.getHost();
            int port = url.getPort();
            if (port > 0) {
                server += ":" + port;
            }

            proto = isEmpty(url.getProtocol()) ? "http" : url.getProtocol();

            if (!proto.equals("http") && !proto.equals("https")) {
                throw new IncorrectAdRequestException("Only HTTP and HTTPS protocols are supported");
            }

            String path = url.getPath();
            String[] pathChunks = path.split("/");
            if (pathChunks.length < 4) {
                throw new IncorrectAdRequestException("Path URI must have at least 4 parts: " + path);
            }

            int rotatorNamesLength = pathChunks.length -3;
            rotatorNames = new String[rotatorNamesLength];
            System.arraycopy(pathChunks, 0, rotatorNames, 0, rotatorNamesLength);

            adSpot = pathChunks[rotatorNamesLength];
            try {
                page = Integer.parseInt(pathChunks[rotatorNamesLength + 1]);
            } catch (NumberFormatException e) {
                throw new IncorrectAdRequestException("Page must be number");
            }
            aspect = pathChunks[rotatorNamesLength + 2];

            decodeQuery(url.getQuery());
            checkParams();
        } catch (MalformedURLException e) {
            throw new IncorrectAdRequestException(e);
        }

    }

    /**
     * Set ad spot ID
     *
     * @param adSpot ad Spot ID
     */
    public void setAdSpot(String adSpot) {
        this.adSpot = adSpot;
    }

    /**
     * Set page, it is 0 by default
     *
     * @param page page number
     */
    public void setPage(int page) {
        this.page = page;
    }

    public void setAdSize(String adSize) {
        addParameter(PARAM_DIM, adSize);
    }

    /**
     * If you show several ads on one screen then you have to specify one page view ID for all ad calls<br>
     * If you don't specify page view ID then random ID (see {@link #getRandomPv()}) is assigned to the ad call
     *
     * @param pageView page view ID, it is recommended to use 10-15 digits number
     */
    public void setPageView(String pageView) {
        addParameter(PARAM_PV, pageView);
    }

    /**
     * If true then "https" scheme is used<br>
     * By default it is "http" scheme
     *
     * @param secure false - "http" scheme <br> true - "https" scheme
     */
    public void setSecure(boolean secure) {
        this.proto = (secure ? "https" : "http");
    }

    /**
     * Setup the AdJuggler server to call<br>
     * For example, "rotator.adjuggler.com"<br>
     * You may specify custom port as "rotator.adjuggler.com:8888"
     *
     * @param server server name to call
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * You zone ID in the AdJuggler system
     *
     * @param zone zone ID
     */
    public void setZone(String zone) {
        addParameter(PARAM_ZONE, zone);
    }

    /**
     * Setup channel ID
     *
     * @param channel channel ID
     */
    public void setChannel(String channel) {
        addParameter(PARAM_CHANNEL, channel);
    }

    /**
     * Setup click redirect URL
     *
     * @param click click redirect URL
     */
    public void setClickRedirect(String click) {
        addParameter(PARAM_CLICK, click);
    }

    /**
     * Setup keywords<br>
     * They are converted in the comma-separated list
     *
     * @param keywords
     */
    public void setKeywords(String... keywords) {
        if (keywords != null && keywords.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < keywords.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(keywords[i]);
            }
            addParameter(PARAM_KEYWORDS, sb.toString());
        }
    }


    /**
     * Returns 12-digits random number as pv value.
     *
     * @return random number converted to string
     */
    public static String getRandomPv() {
        return Long.toString(Math.abs(rnd.nextLong() % 1000000000000L));
    }

    /**
     * Specify what type of ad you need (see {@link AdType})
     * By default AdJuggler may return the ad of any type
     *
     * @param adType required ad type or null for any ad type
     */
    public void setAdType(AdType adType) {
        addParameter(PARAM_ADTYPE, adType.getName());
    }

    private boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }

    //    Try to run this simple snippet and you will see the following exception:
    //        java.lang.RuntimeException: Stub!
    //    Google it and remember that not all classes from android.jar (like URLEncodedUtils) implemented on your local machine. ??

    //    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
    //        URI uri = URI.create("www.example.com/index.php?foo=bar&bla=blub");
    //        URLEncodedUtils.parse(uri, "UTF8");
    //    }
}
