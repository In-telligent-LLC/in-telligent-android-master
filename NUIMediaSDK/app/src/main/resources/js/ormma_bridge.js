/*  Copyright (c) 2011 The ORMMA.org project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */


(function () {

    var ormmaview = window.ormmaview = {};


    /****************************************************/
    /********** PROPERTIES OF THE ORMMA BRIDGE **********/
    /****************************************************/

    /** Expand Properties */
    var expandProperties = {
        useBackground:false,
        backgroundColor:'#ffffff',
        backgroundOpacity:1.0,
        lockOrientation:false,
        useCustomClose: false
    };


    /** The set of listeners for ORMMA Native Bridge Events */
    var listeners = {};

    var dimensions = {};

    var shakeProperties = {};

    /**********************************************/
    /************* JAVA ENTRY POINTS **************/
    /**********************************************/

    /**
     * Called by the JAVA SDK when an asset has been fully cached.
     *
     * @returns string, "OK"
     */
    ormmaview.fireAssetReadyEvent = function (alias, URL) {
        var handlers = listeners["assetReady"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i](alias, URL);
            }
        }

        return "OK";
    };


    /**
     * Called by the JAVA SDK when an asset has been removed from the
     * cache at the request of the creative.
     *
     * @returns string, "OK"
     */
    ormmaview.fireAssetRemovedEvent = function (alias) {
        var handlers = listeners["assetRemoved"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i](alias);
            }
        }

        return "OK";
    };


    /**
     * Called by the JAVA SDK when an asset has been automatically
     * removed from the cache for reasons outside the control of the creative.
     *
     * @returns string, "OK"
     */
    ormmaview.fireAssetRetiredEvent = function (alias) {
        var handlers = listeners["assetRetired"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i](alias);
            }
        }

        return "OK";
    };


    /**
     * Called by the JAVA SDK when various state properties have changed.
     *
     * @returns string, "OK"
     */
    ormmaview.fireChangeEvent = function (properties) {
        var handlers = listeners["change"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i](properties);
            }
        }

        return "OK";
    };


    /**
     * Called by the JAVA SDK when an error has occured.
     *
     * @returns string, "OK"
     */
    ormmaview.fireErrorEvent = function (message, action) {
        var handlers = listeners["error"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i](message, action);
            }
        }

        return "OK";
    };

    ormmaview.fireReadyEvent = function () {
        var handlers = listeners["ready"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i]();
            }
        }

        return "OK";
    };

    /**
     * Called by the JAVA SDK when the user shakes the device.
     *
     * @returns string, "OK"
     */
    ormmaview.fireShakeEvent = function () {
        var handlers = listeners["shake"];
        if (handlers != null) {
            for (var i = 0; i < handlers.length; i++) {
                handlers[i]();
            }
        }

        return "OK";
    };


    /**
     *
     */
    ormmaview.showAlert = function (message) {
        //noinspection JSUnresolvedVariable
        ORMMAUtilityControllerBridge.showAlert(message);
    };


    /*********************************************/
    /********** INTERNALLY USED METHODS **********/
    /*********************************************/


    /**
     *
     */
    ormmaview.zeroPad = function (number) {
        var text = "";
        if (number < 10) {
            text += "0";
        }
        text += number;
        return text;
    };


    /***************************************************************************/
    /********** LEVEL 0 (not part of spec, but required by public API **********/
    /***************************************************************************/

    /**
     *
     */
    ormmaview.activate = function (event) {
        //noinspection JSUnresolvedVariable
        ORMMAUtilityControllerBridge.activate(event);
    };

    /**
     *
     */
    ormmaview.addEventListener = function (event, listener) {
        var handlers = listeners[event];
        if (handlers == null) {
            // no handlers defined yet, set it up
            listeners[event] = [];
            handlers = listeners[event];
        }

        // see if the listener is already present
        for (var handler in handlers) {
            if (listener == handler) {
                // listener already present, nothing to do
                return;
            }
        }

        // not present yet, go ahead and add it
        handlers.push(listener);
    };


    /**
     *
     */
    ormmaview.deactivate = function (event) {
        //noinspection JSUnresolvedVariable
        ORMMAUtilityControllerBridge.deactivate(event);
    };


    /**
     *
     */
    ormmaview.removeEventListener = function (event, listener) {
        var handlers = listeners[event];
        if (handlers != null) {
            handlers.remove(listener);
        }
    };


    /*****************************/
    /********** LEVEL 1 **********/
    /*****************************/

    /**
     *
     */
    ormmaview.close = function () {
        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.close();
        } catch (e) {
            ormmaview.showAlert("close: " + e);
        }
    };


    /**
     *
     */
    ormmaview.expand = function (url) {
        try {
            ORMMADisplayControllerBridge.expand(url, ormmaview.stringify(expandProperties));
        } catch (e) {
            ormmaview.showAlert("executeNativeExpand: " + e + ", URL = " + URL + ", expandProperties = " + expandProperties);
        }
    };


    /**
     *
     */
    ormmaview.hide = function () {
        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.hide();
        } catch (e) {
            ormmaview.showAlert("hide: " + e);
        }
    };


    /**
     *
     */
    ormmaview.open = function (URL, controls) {
        // the navigation parameter is an array, break it into its parts
        var back = false;
        var forward = false;
        var refresh = false;
        if (controls == null) {
            back = true;
            forward = true;
            refresh = true;
        } else {
            for (var i = 0; i < controls.length; i++) {
                if (( controls[i] == "none" ) && ( i > 0 )) {
                    // error
                    self.fireErrorEvent("none must be the only navigation element present.", "open");
                    return;
                }
                else if (controls[i] == "all") {
                    if (i > 0) {
                        // error
                        self.fireErrorEvent("none must be the only navigation element present.", "open");
                        return;
                    }

                    // ok
                    back = true;
                    forward = true;
                    refresh = true;
                }
                else if (controls[i] == "back") {
                    back = true;
                }
                else if (controls[i] == "forward") {
                    forward = true;
                }
                else if (controls[i] == "refresh") {
                    refresh = true;
                }
            }
        }

        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.open(URL, back, forward, refresh);
        } catch (e) {
            ormmaview.showAlert("open: " + e);
        }

    };

    ormmaview.openMap = function (POI, fullscreen) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.openMap(POI, fullscreen);
        } catch (e) {
            ormmaview.showAlert("openMap: " + e);
        }
    };

    ormmaview.useCustomClose = function (flag) {
        expandProperties.useCustomClose = flag;
    };

    ormmaview.playAudio = function (URL, properties) {
        var autoPlay = false, controls = false, loop = false, position = false,
                startStyle = 'normal', stopStyle = 'normal';

        if (properties != null) {

            if (( typeof properties.autoplay != "undefined" ) && ( properties.autoplay != null )) {
                autoPlay = true;
            }

            if (( typeof properties.controls != "undefined" ) && ( properties.controls != null )) {
                controls = true;
            }

            if (( typeof properties.loop != "undefined" ) && ( properties.loop != null )) {
                loop = true;
            }

            if (( typeof properties.position != "undefined" ) && ( properties.position != null )) {
                position = true;
            }

            if (( typeof properties.startStyle != "undefined" ) && ( properties.startStyle != null )) {
                startStyle = properties.startStyle;
            }

            if (( typeof properties.stopStyle != "undefined" ) && ( properties.stopStyle != null )) {
                stopStyle = properties.stopStyle;
            }

            if (startStyle == 'normal') {
                position = true;
            }

            if (position) {
                autoPlay = true;
                controls = false;
                loop = false;
                stopStyle = 'exit';
            }

            if (loop) {
                stopStyle = 'normal';
                controls = true;
            }

            if (!autoPlay) {
                controls = true;
            }

            if (!controls) {
                stopStyle = 'exit';
            }
        }

        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.playAudio(URL, autoPlay, controls, loop, position, startStyle, stopStyle);
        }
        catch (e) {
            ormmaview.showAlert("playAudio: " + e);
        }
    };


    /**
     *
     */
    ormmaview.playVideo = function (URL, properties) {
        var audioMuted = false, autoPlay = false, controls = false, loop = false, position = [-1, -1, -1, -1],
                startStyle = 'normal', stopStyle = 'normal';
        if (properties != null) {

            if (( typeof properties.audio != "undefined" ) && ( properties.audio != null )) {
                audioMuted = true;
            }

            if (( typeof properties.autoplay != "undefined" ) && ( properties.autoplay != null )) {
                autoPlay = true;
            }

            if (( typeof properties.controls != "undefined" ) && ( properties.controls != null )) {
                controls = true;
            }

            if (( typeof properties.loop != "undefined" ) && ( properties.loop != null )) {
                loop = true;
            }

            if (( typeof properties.position != "undefined" ) && ( properties.position != null )) {
                position[0] = properties.position.top;
                position[1] = properties.position.left;

                if (( typeof properties.width != "undefined" ) && ( properties.width != null )) {
                    position[2] = properties.width;
                }

                if (( typeof properties.height != "undefined" ) && ( properties.height != null )) {
                    position[3] = properties.height;
                }
            }


            if (( typeof properties.startStyle != "undefined" ) && ( properties.startStyle != null )) {
                startStyle = properties.startStyle;
            }

            if (( typeof properties.stopStyle != "undefined" ) && ( properties.stopStyle != null )) {
                stopStyle = properties.stopStyle;
            }

            if (loop) {
                stopStyle = 'normal';
                controls = true;
            }

            if (!autoPlay)
                controls = true;

            if (!controls) {
                stopStyle = 'exit';
            }

            if (position[0] == -1 || position[1] == -1) {
                startStyle = "fullscreen";
            }
        }

        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.playVideo(URL, audioMuted, autoPlay, controls, loop, position, startStyle, stopStyle);
        }
        catch (e) {
            ormmaview.showAlert("playVideo: " + e);
        }

    };


    /**
     *
     */
    ormmaview.resize = function (width, height) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.resize(width, height);
        } catch (e) {
            ormmaview.showAlert("resize: " + e);
        }
    };


    ormmaview.getExpandProperties = function () {
        return expandProperties;
    };


    /**
     *
     */
    ormmaview.setExpandProperties = function (properties) {
        expandProperties = properties;
    };


    /**
     *
     */
    ormmaview.show = function () {
        try {
            //noinspection JSUnresolvedVariable
            ORMMADisplayControllerBridge.show();
        } catch (e) {
            ormmaview.showAlert("show: " + e);
        }
    };

    /**
     *
     */
    ormmaview.isViewable = function () {
        try {
            //noinspection JSUnresolvedVariable
            return ORMMADisplayControllerBridge.isViewable();
        } catch (e) {
            ormmaview.showAlert("show: " + e);
        }
    };


    /*****************************/
    /********** LEVEL 2 **********/
    /*****************************/

    /**
     *
     */
    ormmaview.createEvent = function (date, title, body) {
        var msecs = (date.getTime() - date.getMilliseconds());

        try {
            //noinspection JSUnresolvedVariable
            ORMMAUtilityControllerBridge.createEvent(msecs.toString(), title, body);
        } catch (e) {
            ormmaview.showAlert("createEvent: " + e);
        }

    };

    /**
     *
     */
    ormmaview.makeCall = function (phoneNumber) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMAUtilityControllerBridge.makeCall(phoneNumber);
        } catch (e) {
            ormmaview.showAlert("makeCall: " + e);
        }
    };


    /**
     *
     */
    ormmaview.sendMail = function (recipient, subject, body) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMAUtilityControllerBridge.sendMail(recipient, subject, body);
        } catch (e) {
            ormmaview.showAlert("sendMail: " + e);
        }
    };


    /**
     *
     */
    ormmaview.sendSMS = function (recipient, body) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMAUtilityControllerBridge.sendSMS(recipient, body);
        } catch (e) {
            ormmaview.showAlert("sendSMS: " + e);
        }
    };

    /**
     *
     */
    ormmaview.setShakeProperties = function (properties) {
        var fields = ['interval', 'intensity'], f;

        for (f in fields) {
            if (fields.hasOwnProperty(f)) {
                var field = fields[f];
                if (properties[field] !== undefined) {
                    shakeProperties[field] = properties[field];
                }
            }
        }
    };

    ormmaview.storePicture = function (URL) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMAAssetControllerBridge.storePicture(URL);
        } catch (e) {
            ormmaview.showAlert("storePicture: " + e);
        }
    };


    /*****************************/
    /********** LEVEL 3 **********/
    /*****************************/

    /**
     *
     */
    ormmaview.addAsset = function (URL, alias) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMAAssetControllerBridge.addAsset(URL, alias);
        } catch (e) {
            ormmaview.showAlert("storePicture: " + e);
        }
    };

    /**
     *
     */
    ormmaview.request = function (URI, display) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMANetworkControllerBridge.request(URI, display);
        } catch (e) {
            ormmaview.showAlert("request: " + e);
        }
    };

    /**
     *
     */
    ormmaview.removeAsset = function (alias) {
        try {
            //noinspection JSUnresolvedVariable
            ORMMAAssetControllerBridge.removeAsset(URL, alias);
        } catch (e) {
            ormmaview.showAlert("storePicture: " + e);
        }
    };


    ormmaview.stringify = function (args) {
        if (typeof JSON === "undefined") {
            var s = "";
            var len = args.length;
            var i;
            if (typeof len == "undefined") {
                return ormmaview.stringifyArg(args);
            }
            for (i = 0; i < args.length; i++) {
                if (i > 0) {
                    s = s + ",";
                }
                s = s + ormmaview.stringifyArg(args[i]);
            }
            s = s + "]";
            return s;
        } else {
            return JSON.stringify(args);
        }
    };

    ormmaview.stringifyArg = function (arg) {
        var s, type, start, name, nameType, a;
        type = typeof arg;
        s = "";
        if ((type === "number") || (type === "boolean")) {
            s = s + arg;
        } else if (arg instanceof Array) {
            s = s + "[" + arg + "]";
        } else if (arg instanceof Object) {
            start = true;
            s = s + '{';
            for (name in arg) {
                if (arg.hasOwnProperty(name) && arg[name] !== null) {
                    if (!start) {
                        s = s + ',';
                    }
                    s = s + '"' + name + '":';
                    nameType = typeof arg[name];
                    if ((nameType === "number") || (nameType === "boolean")) {
                        s = s + arg[name];
                    } else if ((typeof arg[name]) === 'function') {
                        // don't copy the functions
                        s = s + '""';
                    } else if (arg[name] instanceof Object) {
                        s = s + this.stringify(arg[name]);
                    } else {
                        s = s + '"' + arg[name] + '"';
                    }
                    start = false;
                }
            }
            s = s + '}';
        } else {
            a = arg.replace(/\\/g, '\\\\');
            a = a.replace(/"/g, '\\"');
            s = s + '"' + a + '"';
        }
        ormmaview.showAlert("json:" + s);
        return s;
    }

})();
