package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;

public class SubscriberResponseMock {

    public static SubscriberResponse createMockSubscriberResponse() {
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        subscriberResponse.setSubscriber(SubscriberMock.createMockSubscriber());
        subscriberResponse.setSuccess(true);
        return subscriberResponse;
    }

}
