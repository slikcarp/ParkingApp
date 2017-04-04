package com.vehicleapp.mac.user.parkingapp;

/**
 * Created by user on 2017-03-30.
 */

class ParkingSpot {
    public static final int DEFAULT_ID_VALUE = -1;
    public static final int DEFAULT_X_VALUE = -1;
    public static final int DEFAULT_Y_VALUE = -1;
    private int id;
    private int x;
    private int y;
    private boolean free;

    public ParkingSpot() {
    }

    public ParkingSpot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public double calculateDistance() {
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }
}
