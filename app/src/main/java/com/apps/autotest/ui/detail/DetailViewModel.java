package com.apps.autotest.ui.detail;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.apps.autotest.Repository.DetailRepository;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Named;

public class DetailViewModel extends ViewModel {
    private Picasso picasso;
    private Typeface iranTypeFace;

    public Typeface getIranTypeFace() {
        return iranTypeFace;
    }

    private DetailRepository detailRepository;
    Picasso getPicasso() {
        return picasso;
    }

    @Inject
    public DetailViewModel(Picasso picasso, @Named("iran")Typeface iranTypeface) {
        this.picasso = picasso;
        this.iranTypeFace = iranTypeface;
        detailRepository = DetailRepository.getInstance();
    }
    @BindingAdapter("setTextViewFont")
    public static void setTvFont(TextView textView, Typeface typeface){
        textView.setTypeface(typeface);
    }

    String getImage(){
        return detailRepository.getImageUrl();
    }
}
