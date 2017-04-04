package com.vehicleapp.mac.user.parkingapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-03-30.
 */

public class Building {
    private FloorId floorId;
    private List<Floor> floors = new ArrayList<>();

    public Building() {
    }

    public Building(FloorId floorId) {
        this.floorId = floorId;
    }

    public FloorId getFloorId() {
        return floorId;
    }

    public void setFloorId(FloorId floorId) {
        this.floorId = floorId;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public static class FloorId {
        public int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
