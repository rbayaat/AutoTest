package com.apps.autotest.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.apps.autotest.R;
import com.apps.autotest.databinding.DetailBinding;
import com.apps.autotest.model.Trip;
import com.apps.autotest.utils.ViewModelProviderFactory;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DetailActivity extends DaggerAppCompatActivity {
    LatLng startPoint,endPoint;
    @Inject
    ViewModelProviderFactory providerFactory;
    DetailViewModel detailViewModel;
    DetailBinding detailBinding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        detailViewModel = ViewModelProviders.of(this,providerFactory).get(DetailViewModel.class);
        detailBinding.setDetailViewModel(detailViewModel);
        detailViewModel.getPicasso().load(detailViewModel.getImage()).placeholder(R.drawable.placeholder).into(detailBinding.detailImg);
        Trip trip;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trip = (Trip)getIntent().getSerializableExtra("trip");
            if (trip!=null){
                startPoint = new LatLng(trip.getStartLat(),trip.getStartLng());
                endPoint = new LatLng(trip.getEndLat(),trip.getEndLng());
                detailBinding.detailStartLatlng.setText(
                        "Lat:"+
                        String.valueOf(startPoint.latitude)+"\n"+
                        "Lon: "+
                        String.valueOf(startPoint.longitude));
                detailBinding.detailEndLatlng.setText(
                        "Lat:"+
                        String.valueOf(endPoint.latitude)+"\n"+
                        "Lon: "+
                        String.valueOf(endPoint.longitude));
            }
        }

    }
}
