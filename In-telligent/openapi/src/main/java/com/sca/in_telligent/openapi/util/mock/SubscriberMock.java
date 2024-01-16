package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.openapi.data.network.model.User;

import java.util.ArrayList;

public class SubscriberMock {

    public static Subscriber createMockSubscriber() {
        Subscriber subscriber = new Subscriber();
        subscriber.setBetaEnabled(true);
        subscriber.setEmail("mock@example.com");
        subscriber.setId(1);
        subscriber.setLanguage("en");
        subscriber.setLanguageName("English");
        subscriber.setLightningAlertConfirmed(true);
        subscriber.setLightningAlertEnabled(true);
        subscriber.setName("Mock User");
        subscriber.setWeatherAlertEnabled(true);

        // Configurar listas vazias para evitar NullPointerException
        subscriber.setBuildings(new ArrayList<>());
        subscriber.setInterests(new ArrayList<>());
        subscriber.setPersonalCommunities(new ArrayList<>());
        subscriber.setSubscriberOptOuts(new ArrayList<>());

        subscriber.setUser(createMockUser());

        return subscriber;
    }

    private static User createMockUser() {
        User user = new User();
        user.getId();
        user.isCanSendLSA();


        return user;
    }

}
