(function () {
    var mraid = window.mraid = {};

    var EVENTS = mraid.EVENTS = {
        READY:"ready",
        ERROR:"error",
        STATECHANGE:"stateChange",
        VIEWABLECHANGE:"viewableChange"
    };

    var STATES = mraid.STATES= {
        LOADING:'loading',
        DEFAULT:'default',
        EXPANDED:'expanded',
        HIDDEN:'hidden'
    };

    mraid.state = STATES.LOADING;

    var listeners = {};

    var contains = function(value, array) {
        for (var i in array) if (array[i] == value) return true;
        return false;
    };

    var EventListeners = function(event) {
        this.event = event;
        this.count = 0;
        var listeners = {};

        this.add = function(func) {
            var id = String(func);
            if (!listeners[id]) {
                listeners[id] = func;
                this.count++;
                if (this.count == 1) ormmaview.activate(event);
            }
        };
        this.remove = function(func) {
            var id = String(func);
            if (listeners[id]) {
                listeners[id] = null;
                delete listeners[id];
                this.count--;
                if (this.count == 0) ormmaview.deactivate(event);
                return true;
            } else {
                return false;
            }
        };
        this.removeAll = function() { for (var id in listeners) this.remove(listeners[id]); };
        this.broadcast = function(args) { for (var id in listeners) listeners[id].apply({}, args); };
        this.toString = function() {
            var out = [event,':'];
            for (var id in listeners) out.push('|',id,'|');
            return out.join('');
        };
    };


    var broadcastEvent = function() {
        var args = new Array(arguments.length);
        for (var i = 0; i < arguments.length; i++) {
            args[i] = arguments[i];
        }
        var event = args.shift();
        try {
            if (listeners[event]) {
                listeners[event].broadcast(args);
            }
        } catch (e) {
        }
    };

    /**
     * Use this method to subscribe a specific handler method to a specific event.
     * In this way, multiple listeners can subscribe to a specific event,
     * and a single listener can handle multiple events.
     *
     * @param event string, name of event to listen for. One of<br/>
     * <ul>
     *     <li><b>ready</b> - report initialize complete</li>
     *     <li><b>error</b> - report error has occurred</li>
     *     <li><b>stateShange</b> - report state changes</li>
     *     <li><b>viewableState</b> - report viewable changes</li>
     * </ul>
     * @param listener function, function name (or anonymous function) to execute
     */
    mraid.addEventListener = function (event, listener) {
        if (!event || !listener) {
            broadcastEvent(EVENTS.ERROR, 'Both event and listener are required.', 'addEventListener');
        } else if (!contains(event, EVENTS)) {
			broadcastEvent(EVENTS.ERROR, 'Unknown event: ' + event, 'addEventListener');
        } else {
            if (!listeners[event]) {
                listeners[event] = new EventListeners(event);
            }
            listeners[event].add(listener);
        }
    };

    /**
     * Use this method to unsubscribe a specific handler method from a specific event.
     * Event listeners should always be removed when they are no longer useful
     * to avoid errors. If no listener function is provided, then all functions listening
     * to the event will be removed.
     *
     * @param event - string, name of event
     * @param listener - function, function name (or anonymous function) to be removed
     */
    mraid.removeEventListener = function (event, listener) {
        if (!event) {
            broadcastEvent(EVENTS.ERROR, 'Must specify an event.', 'removeEventListener');
        }
        else {
            if (listener && (!listeners[event] || !listeners[event].remove(listener))) {
                broadcastEvent(EVENTS.ERROR, 'Listener not currently registered for event', 'removeEventListener');
                return;
            }
            else if (listeners[event]){
                listeners[event].removeAll();
            }

            if (listeners[event] && listeners[event].count == 0) {
                listeners[event] = null;
                delete listeners[event];
            }
        }
    };

    /**
     * Returns supported MRAID version
     */
    mraid.getVersion = function () {
        return "1.0";
    };

    /**
     * The getState method returns the current state of the ad container,
     * returning whether the ad container is in its default, fixed position
     * or is in an expanded, larger position.
     */
    mraid.getState = function () {
        return ormma.getState();
    };

    /**
     * Close expanded Ad
     */
    mraid.close = function () {
        ormma.close();
    };

    /**
     * Expand Ad
     * @param URL
     */
    mraid.expand = function (URL) {
        if (ormma.getState() == 'expanded') {
            return;
        }

        var screenSize = ormma.getScreenSize();
        var dimensions = {
            width: mraid.getExpandProperties.width || screenSize.width,
            height: mraid.getExpandProperties.height || screenSize.height,
            x: 0,
            y: 0
        };

        ormma.expand(dimensions, URL);
    };

    /**
     * Get expand properties
     */
    mraid.getExpandProperties = function () {
        return ormma.getExpandProperties();
    };

    /**
     * Get Ad Placement type
     */
    mraid.getPlacementType = function () {
        return ormma.getPlacementType();
    };

    /**
     * The isViewable method returns whether the ad container is currently on
     * or off the screen. The viewableChange event fires when the ad moves
     * from on-screen to off-screen and vice versa.
     */
    mraid.isViewable = function () {
        return ormma.isViewable();
    };

    /**
     * Open embedded browser
     * @param url
     */
    mraid.open = function (url) {
        ormma.open(url);
    };


    /**
     * Set expand properties
     * @param properties
     */
    mraid.setExpandProperties = function (properties) {
        ormma.setExpandProperties(properties);
    };

    /**
     * Use custom close method instead of default one
     */
    mraid.useCustomClose = function () {
        ormma.useCustomClose(true);
    };

    /**
     * Internal function, not part of MRAID
     */
    mraid.completeInitializationSDK_ = function() {
        ormma.setState(STATES.DEFAULT);
        broadcastEvent(EVENTS.READY);
    };
})();