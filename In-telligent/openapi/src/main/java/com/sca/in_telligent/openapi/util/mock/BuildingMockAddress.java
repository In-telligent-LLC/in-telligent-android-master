package com.sca.in_telligent.openapi.util.mock;

import com.sca.in_telligent.openapi.data.network.model.BuildingAddress;
import com.sca.in_telligent.openapi.data.network.model.City;
import com.sca.in_telligent.openapi.data.network.model.Country;
import com.sca.in_telligent.openapi.data.network.model.State;

public class BuildingMockAddress {

    private static final State state = new State(001, 1, "Missouri", "United States");
    private static final Country country = new Country(1, "United States", "US");
    private static final City city = new City(1, "Kansas City", state.getId());

    public static BuildingAddress createMockBuildingAddress() {
        BuildingAddress buildingAddress = new BuildingAddress();
        buildingAddress.setCity(city);
        buildingAddress.setCountry(country);
        buildingAddress.setFormattedAddress("Mock Line1");
        buildingAddress.setRoute("Mock Line1");
        buildingAddress.setState(state);
        buildingAddress.setStreetNumber("Mock Street Number");
        buildingAddress.setPostalCode("Mock Zip Code");
        buildingAddress.setLatitude(0.0);
        buildingAddress.setLongitude(0.0);
        buildingAddress.setRadius(0);


        return buildingAddress;
    }
}
