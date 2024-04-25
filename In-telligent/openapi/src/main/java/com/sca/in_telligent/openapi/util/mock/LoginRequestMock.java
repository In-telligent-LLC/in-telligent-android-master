package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.LoginRequest;

public class LoginRequestMock {

        public static LoginRequest createMockLoginRequest() {
            LoginRequest mockRequest = new LoginRequest();
            mockRequest.setDeviceId("eM9dl6qHmVKCiVEsGTk99v:APA91bH5luq1arg-bBam14UgNJvgHpDrJz6y_xrThIOom39fztAV0ZthP764KGqUbYlTz5_bAWE1CfMY-Qv3-pHYRF4RGftbFZ-7An0JWD9bMIKnFXVURXc6IjsJYEXch64OXJcEusYx");
            mockRequest.setEmail("mock@example.com");
            mockRequest.setPassword("mockPassword");
            mockRequest.setAdId("mockAdId");
            return mockRequest;
        }
    }

