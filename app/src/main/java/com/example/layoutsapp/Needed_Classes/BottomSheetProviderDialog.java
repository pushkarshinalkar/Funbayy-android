package com.example.layoutsapp.Needed_Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.layoutsapp.R;
import com.example.layoutsapp.WebView.Movies_WatchNow_Webview;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

public class BottomSheetProviderDialog extends BottomSheetDialogFragment {

    CardView provider1;
    CardView provider2;
    CardView provider3;
    CardView provider4;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_provider_dialogbox,
                container, false);

        provider1 = v.findViewById(R.id.providerCard1);
        provider2 = v.findViewById(R.id.providerCard2);
        provider3 = v.findViewById(R.id.providerCard3);
        provider4 = v.findViewById(R.id.providerCard4);
        String MovID = this.getArguments().getString("movID");
        String MovNameID = this.getArguments().getString("movNameID");
        String MovYearID = this.getArguments().getString("movYearID");
        Boolean isDownloadType = this.getArguments().getBoolean("isDownloadable");

        provider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("message", "server1" );
//                BottomSheetDialog bottomSheet = new BottomSheetDialog();
//                bottomSheet.setArguments(bundle);
//                bottomSheet.show(getParentFragmentManager(),
//                        "SecondBottomSheet");
                Intent intent = new Intent(getActivity(), Movies_WatchNow_Webview.class);
                intent.putExtra("movieID",MovID);
                startActivity(intent);
            }
        });

        provider2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("message", "server2" );
                bundle.putString("MovName+", MovNameID);
                bundle.putString("MovYear", MovYearID);
                bundle.putBoolean("isDownloadID", isDownloadType);
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getParentFragmentManager(),
                        "SecondBottomSheet");
            }
        });

        provider3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("message", "server3" );
                bundle.putString("MovName+", MovNameID);
                bundle.putString("MovYear", MovYearID);
                bundle.putBoolean("isDownloadID", isDownloadType);
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getParentFragmentManager(),
                        "ThirdBottomSheet");
            }
        });

        provider4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("message", "server4" );
                bundle.putString("MovName+", MovNameID);
                bundle.putString("MovYear", MovYearID);
                bundle.putBoolean("isDownloadID", isDownloadType);
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getParentFragmentManager(),
                        "FourthBottomSheet");
            }
        });

        return v;
    }
}
