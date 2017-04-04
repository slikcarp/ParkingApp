package com.vehicleapp.mac.user.parkingapp.preferences;

/**
 * Created by user on 2017-04-03.
 */

public interface CarPreferencesIntr {

    String getCarId();

    void setCarId(String carId);

    void setParkingSpotInfo(int level, int spotId, int x, int y);

    int getParkingSpotFloor();

    int getParkingSpotId();

    int getParkingSpotX();

    int getParkingSpotY();
}
