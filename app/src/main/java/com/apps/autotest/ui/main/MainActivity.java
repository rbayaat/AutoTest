package com.apps.autotest.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.apps.autotest.R;
import com.apps.autotest.databinding.MainBinding;
import com.apps.autotest.model.RideResponse;
import com.apps.autotest.model.Trip;
import com.apps.autotest.ui.detail.DetailActivity;
import com.apps.autotest.utils.ViewModelProviderFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerActivity;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 99;
    private GoogleMap mMap;
    private MainViewModel viewModel;
    private Marker startMarker,endMarker;
    private boolean isSelectingStart = true;
    Trip trip = new Trip();
    private List<String> polylinePoints = new ArrayList<>();
    @Inject
    ViewModelProviderFactory providerFactory;
    MainBinding mainBinding;
    //Style the polyline
    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    private static final String TAG = "MainActivity";

    @Override
    public void onBackPressed() {
        if (!isSelectingStart){
            isSelectingStart = true;
            startMarker.remove();
            mainBinding.selectPointsButton.setText(R.string.ConfirmStart);
            trip = new Trip();
        }else{
        super.onBackPressed();}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,providerFactory).get(MainViewModel.class);
        mainBinding.setMainViewModel(viewModel);
        checkPermission();
        if(!isNetworkConnected()){
            mainBinding.mainProgressbar.setVisibility(View.GONE);
            Toast.makeText(this, R.string.networkError, Toast.LENGTH_SHORT).show();
            return;
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        observeRideDetails();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    private void observeRideDetails(){
        viewModel.getRideDetails().observe(this, new Observer<RideResponse>() {
            @Override
            public void onChanged(RideResponse object) {
                mainBinding.mainProgressbar.setVisibility(View.GONE);
                LatLngBounds.Builder builder =  new LatLngBounds.Builder();
                for (int i=0;i<object.getData().size();i++) {
                    RideResponse.Data tmp = object.getData().get(i);
                    polylinePoints.add(tmp.getStep().getPolyline().getPoints());
                    //Start point
                    LatLng tmpStartLatLng = new LatLng(
                            Double.valueOf(object.getData().get(i).getStep().getStartLocation().getLat()),
                            Double.valueOf(object.getData().get(i).getStep().getStartLocation().getLng()));
                    //End point
                    LatLng tmpEndLatLng = new LatLng(
                            Double.valueOf(object.getData().get(i).getStep().getEndLocation().getLat()),
                            Double.valueOf(object.getData().get(i).getStep().getEndLocation().getLng()));
                    builder.include(tmpStartLatLng);
                    builder.include(tmpEndLatLng);
                    mMap.addMarker(new MarkerOptions().position(tmpStartLatLng));
                    mMap.addMarker(new MarkerOptions().position(tmpEndLatLng));
                    //Draw line
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(PolyUtil.decode(polylinePoints.get(i)))
                            .width(12)
                            .pattern(PATTERN_POLYGON_ALPHA);
                    mainBinding.selectPointsButton.setEnabled(true);
                    mMap.addPolyline(polylineOptions);

                }
                LatLngBounds bounds = builder.build();
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (height * 0.20);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,padding);
                mMap.moveCamera(cameraUpdate);

            }
        });

        mainBinding.selectPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraPosition position = mMap.getCameraPosition();
                LatLng lng = position.target;
                if (isSelectingStart){
                    if (checkPoints(lng,true)){
                        isSelectingStart = false;
                        mainBinding.selectPointsButton.setText(R.string.ConfirmEnd);
                        startMarker = mMap.addMarker(new MarkerOptions().position(lng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        trip.setStartLat(lng.latitude);
                        trip.setStartLng(lng.longitude);
                    }
                    else{
                        Toast.makeText(MainActivity.this, R.string.start_point_alert, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                        if (checkPoints(lng,false)){
                            endMarker = mMap.addMarker(new MarkerOptions().position(lng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            trip.setEndLat(lng.latitude);
                            trip.setEndLng(lng.longitude);
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("trip", trip);
                            mMap.clear();
                            isSelectingStart = true;
                            mainBinding.selectPointsButton.setText(R.string.ConfirmStart);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, R.string.end_point_alert, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        });
    }
    private boolean checkPoints(LatLng location,boolean isFirstMarker){
        if (!isFirstMarker){
            List<LatLng> tmpLatLng = PolyUtil.decode(trip.getPoints());
            return PolyUtil.isLocationOnPath(location,tmpLatLng,false,5);
        }
        else {
            for (int i = 0; i < polylinePoints.size(); i++) {
                List<LatLng> tmpLatLng = PolyUtil.decode(polylinePoints.get(i));
                if (PolyUtil.isLocationOnPath(location, tmpLatLng, false, 5)) {
                    trip.setPoints(polylinePoints.get(i));
                    return true;
                }
            }
        }
            return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                    finish();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    protected void onRestart() {
        mainBinding.mainProgressbar.setVisibility(View.VISIBLE);
        super.onRestart();
    }
}
