package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.BuildingAddress;

public class BuildingMock {

    public static Building createMockBuilding() {
        Building mockBuilding = Mockito.mock(Building.class);

        when(mockBuilding.getId()).thenReturn(1);
        when(mockBuilding.getDescription()).thenReturn("Mock Description");
        when(mockBuilding.getPassword()).thenReturn("Mock Password");
        when(mockBuilding.getWebsite()).thenReturn("http://mockwebsite.com");
        when(mockBuilding.isVoipEnabled()).thenReturn(true);
        when(mockBuilding.isTextEnabled()).thenReturn(true);
        when(mockBuilding.getCreated()).thenReturn("2023-01-01");
        when(mockBuilding.getSubscriberCount()).thenReturn(100);

        // Mocking related objects
        BuildingAddress mockAddress = createMockBuildingAddress();
        when(mockBuilding.getBuildingAddress()).thenReturn(mockAddress);

        return mockBuilding;
    }

    private static BuildingAddress createMockBuildingAddress() {
        BuildingAddress mockAddress = Mockito.mock(BuildingAddress.class);
        when(mockAddress.getCity()).thenReturn("Mock City");
        when(mockAddress.getState()).thenReturn("Mock State");
        // ... mock other properties of BuildingAddress as needed
        return mockAddress;
    }
}
