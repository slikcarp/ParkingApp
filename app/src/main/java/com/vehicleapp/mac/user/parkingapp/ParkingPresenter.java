package com.vehicleapp.mac.user.parkingapp;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicleapp.mac.user.parkingapp.interfaces.ParkingContract;
import com.vehicleapp.mac.user.parkingapp.preferences.CarPreferencesIntr;
import com.vehicleapp.mac.user.parkingapp.preferences.CarPreferencesModule;
import com.vehicleapp.mac.user.parkingapp.preferences.DaggerCarPreferencesComponent;

import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

/**
 * Created by Carlos Romero on 2017-03-31.
 */

public class ParkingPresenter implements ParkingContract.Presenter {

    private static final String MAIN_NODE = "parking";
    private static final String BUILDING_NODE = "building";
    private static final String PARKING_SPOTS_NODE = "parkingSpots";
    private static final DatabaseReference dr = FirebaseDatabase.getInstance().getReference(MAIN_NODE);
    private static final String LOADER_NODE = "loader";
    private static final Building building = new Building();

    @Inject
    CarPreferencesIntr carPreferences;

    private final ParkingContract.View view;
    private final Context context;
    private ParkingSpot parkingSpotSelected = null;
    private Floor floorSelected = null;

    public ParkingPresenter(ParkingContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void initialize() {
        DaggerCarPreferencesComponent.builder()
                .carPreferencesModule(new CarPreferencesModule(context))
                .build()
                .inject(this);

        addBuildingEventListener();
    }

    private void addBuildingEventListener() {
        ValueEventListener val = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                building.getFloors().clear();

                for (DataSnapshot dsFloors :
                        dataSnapshot.getChildren()) {
                    building.getFloors().add(dsFloors.getValue(Floor.class));
                }

                sortFloors();

                updateParkingSpotLocalInfo();

                view.updateParkingSpots(building.getFloors());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());

                clearSelection();
            }
        };

        getBuildingNode().child(Floor.FLOORS_NODE).addValueEventListener(val);
    }

    private void updateParkingSpotLocalInfo() {
        if (floorSelected != null && parkingSpotSelected != null) {
            fillLocalInfoWithNewSelectedParkingSpot();
        }
        else if(parkingSpotSelected != null) {
            cleanPreviousParkingSpotInfo();
        }
    }

    private void fillLocalInfoWithNewSelectedParkingSpot() {
        Floor floorUpdated = building.getFloors().get(floorSelected.getLevel() - 1);
        ParkingSpot parkingSpotUpdated = floorUpdated.getParkingSpots().get(parkingSpotSelected.getId());
        if (!parkingSpotUpdated.isFree()) {
            carPreferences.setParkingSpotInfo(floorSelected.getLevel(),
                    parkingSpotSelected.getId(),
                    parkingSpotSelected.getX(),
                    parkingSpotSelected.getY());

            view.showInfo(floorSelected.getLevel(), parkingSpotSelected.getX(), parkingSpotSelected.getY());

            clearSelection();
        }
    }

    private void cleanPreviousParkingSpotInfo() {
        Floor floorUpdated = building.getFloors().get(carPreferences.getParkingSpotFloor() - 1);
        ParkingSpot parkingSpotUpdated = floorUpdated.getParkingSpots().get(carPreferences.getParkingSpotId());
        if (parkingSpotUpdated.isFree()) {
            carPreferences.setParkingSpotInfo(Floor.DEFAULT_LEVEL_VALUE,
                    ParkingSpot.DEFAULT_ID_VALUE,
                    ParkingSpot.DEFAULT_X_VALUE,
                    ParkingSpot.DEFAULT_Y_VALUE);

            view.showInfo(Floor.DEFAULT_LEVEL_VALUE, ParkingSpot.DEFAULT_X_VALUE, ParkingSpot.DEFAULT_Y_VALUE);

            clearSelection();
        }
    }

    private void clearSelection() {
        parkingSpotSelected = null;
        floorSelected = null;
    }


    private void sortFloors() {
        Collections.sort(building.getFloors(), new Comparator<Floor>() {
            @Override
            public int compare(Floor o1, Floor o2) {
                return Integer.valueOf(o1.getLevel()).compareTo(o2.getLevel());
            }
        });
    }

    @Override
    public void loadParkingInfo() {
        retrieveBuilding();
    }

    private void retrieveBuilding() {
        getBuildingNode().child(LOADER_NODE).setValue(Loader.LOADER);
    }

    @Override
    public void retrieveParkingSpot() {
        if(isParkingSpotReserved()) {
            view.showError("To retrieve a new parking spot you have to leave the previous one.");
            return;
        }

        chooseParkingSpot();

        lockDBParkingSpot();
    }

    private boolean isParkingSpotReserved() {
        return carPreferences.getParkingSpotFloor() >= 0;
    }

    private void chooseParkingSpot() {
        for (Floor floor :
                building.getFloors()) {
            for (ParkingSpot parkingSpot :
                    floor.getParkingSpots()) {
                if(parkingSpot != null && parkingSpot.isFree()) {
                    if(parkingSpotSelected == null ||
                            parkingSpot.calculateDistance() < parkingSpotSelected.calculateDistance())
                    {
                        parkingSpotSelected = parkingSpot;
                    }
                }
            }
            if(parkingSpotSelected != null) {
                floorSelected = floor;
                break;
            }
        }
    }

    private void lockDBParkingSpot() {
        if (parkingSpotSelected != null) {
            parkingSpotSelected.setFree(false);

            getBuildingNode().child(Floor.FLOORS_NODE)
                    .child(String.valueOf(floorSelected.getLevel()))
                    .child(PARKING_SPOTS_NODE)
                    .child(String.valueOf(parkingSpotSelected.getId()))
                    .setValue(parkingSpotSelected);
        } else {
            view.showError("There is no free space.");
        }
    }

    @Override
    public void leaveParkingSpot() {
        if(!isParkingSpotReserved()) {
            view.showError("No parking spot is reserved yet.");
            return;
        }

        unlockDBParkingSpot();
    }

    private void unlockDBParkingSpot() {
        parkingSpotSelected = new ParkingSpot(carPreferences.getParkingSpotId(),
                carPreferences.getParkingSpotX(), carPreferences.getParkingSpotY());
        parkingSpotSelected.setFree(true);

        getBuildingNode().child(Floor.FLOORS_NODE)
                .child(String.valueOf(carPreferences.getParkingSpotFloor()))
                .child(PARKING_SPOTS_NODE)
                .child(String.valueOf(carPreferences.getParkingSpotId()))
                .setValue(parkingSpotSelected);
    }

    private DatabaseReference getBuildingNode() {
        return dr.child(BUILDING_NODE);
    }

    private static class Loader {
        private static final Loader LOADER = new Loader();
        /**
         * This field is necessary as a control to retrieve the data from the DB.
         */
        public String loader = LOADER_NODE;
    }
}
