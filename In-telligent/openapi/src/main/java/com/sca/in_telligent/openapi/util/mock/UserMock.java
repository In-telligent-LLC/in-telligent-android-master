package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.User;

public class UserMock {
    static User createMockUser() {
        User user = new User();
        user.getId();
        user.isCanSendLSA();
        user.getBuildingIds();


        return user;
    }
}
