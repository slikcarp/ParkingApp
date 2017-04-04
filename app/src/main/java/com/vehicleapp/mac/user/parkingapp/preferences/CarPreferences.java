package com.vehicleapp.mac.user.parkingapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.vehicleapp.mac.user.parkingapp.R;

/**
 * Created by user on 2017-04-02.
 */

public class CarPreferences implements CarPreferencesIntr {

    private final Context context;
    private final SharedPreferences sharedPreferences;

    public CarPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
    }

    @Override
    public String getCarId() {
        return sharedPreferences.getString(context.getString(R.string.car_id), null);
    }

    @Override
    public void setCarId(String carId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.car_id), carId);
        editor.commit();
    }

    @Override
    public void setParkingSpotInfo(int level, int spotId, int x, int y) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.parking_spot_floor), level);
        editor.putInt(context.getString(R.string.parking_spot_id), spotId);
        editor.putInt(context.getString(R.string.parking_spot_x), x);
        editor.putInt(context.getString(R.string.parking_spot_y), y);
        editor.commit();
    }

    @Override
    public int getParkingSpotFloor() {
        return sharedPreferences.getInt(context.getString(R.string.parking_spot_floor), -1);
    }

    @Override
    public int getParkingSpotId() {
        return sharedPreferences.getInt(context.getString(R.string.parking_spot_id), -1);
    }

    @Override
    public int getParkingSpotX() {
        return sharedPreferences.getInt(context.getString(R.string.parking_spot_x), -1);
    }

    @Override
    public int getParkingSpotY() {
        return sharedPreferences.getInt(context.getString(R.string.parking_spot_y), -1);
    }
}
