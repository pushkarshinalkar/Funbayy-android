package com.example.layoutsapp.Needed_Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.layoutsapp.R;
import com.example.layoutsapp.WebView.Movies_WatchNow_Webview;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.ChipGroup;
import com.ramotion.fluidslider.FluidSlider;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class BottomSheet_RateUs_Dialog extends BottomSheetDialogFragment {


    Button rateusbutt;
    double pos=5.0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottomsheet_rate_dialogbox,
                container, false);
        rateusbutt = v.findViewById(R.id.rateusbuttid);

        rateusbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos>=5.0){
                    Toasty.success(v.getContext(),"Your feedback has been noted").show();
                }else {
                    Toasty.warning(v.getContext(),"Your feedback seems low\nPlease mention your problems in report bugs section").show();
                }
            }
        });

        final int max = 10;
        final int min = 0;
        final int total = max - min;

        final FluidSlider slider = v.findViewById(R.id.fluidSlider);
        slider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
//                textView.setVisibility(View.INVISIBLE);
                return Unit.INSTANCE;
            }
        });

        slider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
//                textView.setVisibility(View.VISIBLE);
                float posf = slider.getPosition()*10;
                pos = Math.floor(posf);
//                Toast.makeText(getContext(), "value is "+pos, Toast.LENGTH_SHORT).show();
                return Unit.INSTANCE;

            }
        });

        // Java 8 lambda
        slider.setPositionListener(pos -> {
            final String value = String.valueOf( (int)(min + total * pos) );
            slider.setBubbleText(value);
            return Unit.INSTANCE;

        });





        slider.setPosition(0.5f);
        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));

        return v;
    }
}
