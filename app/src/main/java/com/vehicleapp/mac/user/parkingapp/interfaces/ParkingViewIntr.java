package com.vehicleapp.mac.user.parkingapp.interfaces;

import com.vehicleapp.mac.user.parkingapp.Floor;

import java.util.List;

/**
 * Created by user on 2017-03-31.
 */

public interface ParkingViewIntr {
    void showInfo(int level, int x, int y);

    void showError(String errorMessage);

    void updateParkingSpots(List<Floor> floors);
}
