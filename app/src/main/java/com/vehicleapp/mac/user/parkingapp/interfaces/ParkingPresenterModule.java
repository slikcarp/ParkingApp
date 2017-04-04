package com.vehicleapp.mac.user.parkingapp.interfaces;

import android.content.Context;

import com.vehicleapp.mac.user.parkingapp.ParkingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017-03-31.
 */

@Module
public class ParkingPresenterModule {

    private final ParkingContract.View view;
    private final Context context;

    public ParkingPresenterModule(ParkingContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Provides
    public ParkingContract.Presenter providePresenter() {
        return new ParkingPresenter(view, context);
    }
}
