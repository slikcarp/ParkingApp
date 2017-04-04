package com.vehicleapp.mac.user.parkingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicleapp.mac.user.parkingapp.interfaces.DaggerParkingComponent;
import com.vehicleapp.mac.user.parkingapp.interfaces.ParkingContract;
import com.vehicleapp.mac.user.parkingapp.interfaces.ParkingPresenterModule;

import java.util.List;

import javax.inject.Inject;

public class ParkingActivity extends AppCompatActivity implements ParkingContract.View {

    @Inject
    ParkingContract.Presenter presenter;

    private LinearLayout buildingLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildingLayout = (LinearLayout) findViewById(R.id.buildingLayout);
        textView = (TextView) findViewById(R.id.textView);

        DaggerParkingComponent.builder()
                .parkingPresenterModule(new ParkingPresenterModule(this, this))
                .build()
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadParkingInfo();
    }

    public void retrieveParkingSpot(View view) {
        presenter.retrieveParkingSpot();
    }

    @Override
    public void showInfo(int level, int x, int y) {
        if(level != Floor.DEFAULT_LEVEL_VALUE
                && x != ParkingSpot.DEFAULT_X_VALUE
                && y != ParkingSpot.DEFAULT_Y_VALUE) {
            textView.setText("Your parking spot is in the " + level
                    + "th level in the spot x=" + x
                    + " y=" + y);
        } else {
            textView.setText(getText(R.string.click_a_button_to_interact));
        }

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateParkingSpots(List<Floor> floors) {
        buildingLayout.removeAllViews();
        for (Floor floor :
                floors) {
            LinearLayout floorLayout = new LinearLayout(this);
            floorLayout.setOrientation(LinearLayout.VERTICAL);
            floorLayout.setPadding(0,0,0,20);
            buildingLayout.addView(floorLayout);

            int aile = -1;
            LinearLayout aileLayout = null;
            for (ParkingSpot parkingSpot :
                    floor.getParkingSpots()) {
                if(parkingSpot != null) {
                    if (aile < parkingSpot.getY()) {
                        aile = parkingSpot.getY();
                        aileLayout = new LinearLayout(this);
                        aileLayout.setOrientation(LinearLayout.HORIZONTAL);
                        floorLayout.addView(aileLayout);
                    }
                    ParkingSpotView parkingSpotView = new ParkingSpotView(this, parkingSpot);
                    aileLayout.addView(parkingSpotView);
                }
            }
        }
    }

    public void leaveParkingSpot(View view) {
        presenter.leaveParkingSpot();
    }
}
