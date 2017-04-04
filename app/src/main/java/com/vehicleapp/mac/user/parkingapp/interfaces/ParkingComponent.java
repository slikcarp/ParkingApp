package com.vehicleapp.mac.user.parkingapp.interfaces;

import com.vehicleapp.mac.user.parkingapp.ParkingActivity;

import dagger.Component;

/**
 * Created by user on 2017-03-31.
 */
@Component(modules = ParkingPresenterModule.class)
public interface ParkingComponent {
    void inject(ParkingActivity activity);
}
