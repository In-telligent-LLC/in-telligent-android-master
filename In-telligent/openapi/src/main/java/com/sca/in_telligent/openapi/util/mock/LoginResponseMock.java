package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.LoginResponse;

public class LoginResponseMock {

    public static LoginResponse createMockLoginResponse() {
        LoginResponse mockResponse = new LoginResponse();
        mockResponse.isSuccess();
        mockResponse.setToken("eM9dl6qHmVKCiVEsGTk99v:APA91bH5luq1arg-bBam14UgNJvgHpDrJz6y_xrThIOom39fztAV0ZthP764KGqUbYlTz5_bAWE1CfMY-Qv3-pHYRF4RGftbFZ-7An0JWD9bMIKnFXVURXc6IjsJYEXch64OXJcEusYx");
        return mockResponse;
    }
}
