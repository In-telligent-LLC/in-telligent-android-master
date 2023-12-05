package com.sca.in_telligent.util;


import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.util.AppResponder.ResponderListener;

import java.util.HashMap;

public interface Responder {
    public void received(final String msgId);

    public void respond(final String msgId, final HashMap<String, Object> params);

    public void alertOpened(AlertOpenedRequest alertOpenedRequest);

    public void alertDelivered(AlertOpenedRequest alertDeliveredRequest);

    public void respondToPersonalSafety(HashMap<String, Object> params);

    public void setResponderListener(
            ResponderListener responderListener);
}
