package com.example.layoutsapp.Needed_Classes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

public class BottomSheet_DonateUs_Dialog extends BottomSheetDialogFragment {


    Button copyBTC;
    Button copyETH;
    Button copyBCH;

    TextView btcTextID;
    TextView ethTextID;
    TextView bchTextID;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.donate_us_dialog_box,
                container, false);

        copyBTC = v.findViewById(R.id.buttonCopyBTCadd);
        copyETH = v.findViewById(R.id.buttonCopyETHadd);
        copyBCH = v.findViewById(R.id.buttonCopyBCHadd);

        btcTextID = v.findViewById(R.id.bitcoin_add_id);
        ethTextID = v.findViewById(R.id.ethereum_id);
        bchTextID = v.findViewById(R.id.bitcoinCashID);

         copyBTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("BTCid",btcTextID.getText());
                clipboardManager.setPrimaryClip(clip);
                Toasty.success(v.getContext(),"Copied BTC ID").show();
            }
        });

        copyETH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ETHid",ethTextID.getText());
                clipboardManager.setPrimaryClip(clip);
                Toasty.success(v.getContext(),"Copied ETH ID").show();
            }
        });

        copyBCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("BCHid",bchTextID.getText());
                clipboardManager.setPrimaryClip(clip);
                Toasty.success(v.getContext(),"Copied BCH ID").show();
            }
        });


        return v;
    }
}
