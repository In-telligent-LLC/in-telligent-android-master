package com.sca.in_telligent.util;


import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.util.AppResponder.ResponderListener;

import java.util.HashMap;

public interface Responder {
    void received(final String msgId);

    void respond(final String msgId, final HashMap<String, Object> params);

    void alertOpened(AlertOpenedRequest alertOpenedRequest);

    void alertDelivered(AlertOpenedRequest alertDeliveredRequest);

    void respondToPersonalSafety(HashMap<String, Object> params);

    void setResponderListener(
            ResponderListener responderListener);
}
