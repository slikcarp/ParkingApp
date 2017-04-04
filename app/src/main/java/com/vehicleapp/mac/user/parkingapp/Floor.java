package com.vehicleapp.mac.user.parkingapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by user on 2017-03-30.
 */

public class Floor {

    public static final String FLOORS_NODE = "floors";
    public static final int DEFAULT_LEVEL_VALUE = -1;
    private int level;
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    public Floor() {
    }

    public Floor(int level, List<ParkingSpot> parkingSpots) {
        this.level = level;
        this.parkingSpots = parkingSpots;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public void sortFloors() {
        Collections.sort(parkingSpots, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                int xResult = new Integer(o1.getX()).compareTo(o2.getX());
                if(xResult == 0) {
                    return new Integer(o1.getY()).compareTo(o2.getId());
                }
                return xResult;
            }
        });
    }
}
