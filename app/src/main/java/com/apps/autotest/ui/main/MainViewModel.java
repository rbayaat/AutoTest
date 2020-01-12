package com.apps.autotest.ui.main;

import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.apps.autotest.Repository.MainRepository;
import com.apps.autotest.model.RideResponse;
import com.apps.autotest.network.RequestApi;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.inject.Named;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    private RequestApi requestApi;

    public Typeface getIranTypeface() {
        return iranTypeface;
    }

    private Typeface iranTypeface;
    @Inject
    public MainViewModel(RequestApi api, @Named("iran")Typeface iranTypeface) {
        this.requestApi = api;
        this.iranTypeface = iranTypeface;
        mainRepository = MainRepository.getInstance();
    }

    LiveData<RideResponse> getRideDetails(){
        return mainRepository.getRideDetails(requestApi,"Salam!");
    }

    @BindingAdapter("setButtonFont")
    public static void setButtonFont(Button button,Typeface typeface){
        button.setTypeface(typeface);
    }

}
