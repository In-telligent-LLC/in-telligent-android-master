package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.Building;

public class BuildingMock {

    public static Building createMockBuilding() {
        Building building = new Building();
        building.setId(1);
        building.setDescription("Mock Building");
        building.setOther(true);
        building.setBuildingAddress(BuildingMockAddress.createMockBuildingAddress());
        return building;
    }


}

