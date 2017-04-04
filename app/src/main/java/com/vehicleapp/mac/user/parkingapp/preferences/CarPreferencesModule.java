package com.vehicleapp.mac.user.parkingapp.preferences;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017-04-03.
 */

@Module
public class CarPreferencesModule {

    private final Context context;

    public CarPreferencesModule(Context context) {
        this.context = context;
    }

    @Provides
    public CarPreferencesIntr provideCarPreferences() {
        return new CarPreferences(context);
    }
}
