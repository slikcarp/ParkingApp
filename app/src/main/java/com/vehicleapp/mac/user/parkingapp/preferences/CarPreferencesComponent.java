package com.vehicleapp.mac.user.parkingapp.preferences;

import com.vehicleapp.mac.user.parkingapp.ParkingPresenter;

import dagger.Component;

/**
 * Created by user on 2017-04-03.
 */
@Component(modules = CarPreferencesModule.class)
public interface CarPreferencesComponent {
    void inject(ParkingPresenter instance);
}
