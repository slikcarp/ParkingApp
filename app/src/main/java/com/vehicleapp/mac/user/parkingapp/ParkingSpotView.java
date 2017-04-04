package com.vehicleapp.mac.user.parkingapp;

import android.content.Context;
import android.widget.CheckBox;

/**
 * Created by user on 2017-03-31.
 */

public class ParkingSpotView  extends android.support.v7.widget.AppCompatCheckBox {
    public ParkingSpotView(Context context, ParkingSpot parkingSpot) {
        super(context);
        initialize(parkingSpot);
    }

    private void initialize(ParkingSpot parkingSpot) {
        setText("x:" + parkingSpot.getX() + " y:" + parkingSpot.getY());
        setChecked(!parkingSpot.isFree());
        setEnabled(false);
    }
}
