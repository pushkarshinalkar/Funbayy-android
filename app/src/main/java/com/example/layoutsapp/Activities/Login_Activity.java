package com.example.layoutsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.AppDatabaseSer;
import com.example.layoutsapp.R;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login_Activity extends AppCompatActivity {

    SharedPref sharedpref;
    Button regButt;

    EditText pass1;
    EditText pass2;
    EditText pass3;
    EditText pass4;
    TextView hitext;
    TextView verifyPINtext;
    TextView forgotText;
    TextView beautifulExpText;
    TextView logoutPass;

    private Dialog dialog;
    String fullPIN;

    TextInputLayout nameLay;
    TextInputLayout PINlay;

    TextInputEditText enteredName;
    TextInputEditText enteredPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        pass1=findViewById(R.id.editTextNumberPassword1);
        pass2=findViewById(R.id.editTextNumberPasswordtwo);
        pass3=findViewById(R.id.editTextNumberPassword3);
        pass4=findViewById(R.id.editTextNumberPassword4);
        pass3.setEnabled(false);
        pass2.setEnabled(false);
        pass1.setEnabled(false);
        nameLay = findViewById(R.id.loginTextInput);
        PINlay = findViewById(R.id.loginPasswordInput);
        regButt = findViewById(R.id.registerButt);
        enteredName = findViewById(R.id.loginTextInputEditable);
        enteredPin = findViewById(R.id.loginPasswordInputEditable);
        beautifulExpText = findViewById(R.id.beautifulExpid);
        hitext = findViewById(R.id.hiTextLogin);
        verifyPINtext = findViewById(R.id.verifyPINid);
        beautifulExpText =findViewById(R.id.beautifulExpid);
        forgotText =findViewById(R.id.forgotPassid);
        logoutPass = findViewById(R.id.logoutPassid);


        if (!sharedpref.getPinFromLogIn().equals("")){
            enteredName.setVisibility(View.GONE);
            enteredPin.setVisibility(View.GONE);
            regButt.setVisibility(View.GONE);
            nameLay.setVisibility(View.GONE);
            PINlay.setVisibility(View.GONE);
            SharedPref shared2 = new SharedPref(Login_Activity.this);
            String hiTextstr = "Hi "+shared2.getNameFromLogIn();
            hitext.setText(hiTextstr);
            beautifulExpText.setVisibility(View.GONE);

        }else {
            pass1.setVisibility(View.GONE);
            pass2.setVisibility(View.GONE);
            pass3.setVisibility(View.GONE);
            pass4.setVisibility(View.GONE);
            hitext.setVisibility(View.GONE);
            verifyPINtext.setVisibility(View.GONE);
            forgotText.setVisibility(View.GONE);
            logoutPass.setVisibility(View.GONE);
        }


        if (sharedpref.loadNightModeState()==null){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if (sharedpref.loadNightModeState()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        pass4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    pass3.setEnabled(true);
                    pass3.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        pass3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    pass2.setEnabled(true);
                    pass2.requestFocus();
                }else {
                    pass4.requestFocus();
                    pass3.setEnabled(false);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });
        pass2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    pass1.setEnabled(true);
                    pass1.requestFocus();
                }else {
                    pass3.requestFocus();
                    pass2.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });

        pass1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() ==1) {
                    // below fullPIN gettext order should be same as first entered pin will be in pass4
                    fullPIN= pass4.getText().toString()+pass3.getText().toString()+pass2.getText().toString()+pass1.getText().toString();
                    if (fullPIN.equals(sharedpref.getPinFromLogIn())){

                        Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
//                        Toast.makeText(Login_Activity.this, sharedpref.getPinFromLogIn(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Login_Activity.this, fullPIN, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pass2.requestFocus();
                    pass1.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });


        regButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enteredName.getText().toString().equals("")&&!enteredPin.getText().toString().equals("")){
                    sharedpref.setNameFromLogIn(enteredName.getText().toString());
                    sharedpref.setNameFromProfile(enteredName.getText().toString());
                    sharedpref.setPinFromLogIn(enteredPin.getText().toString());
                    Intent intent2 = new Intent(Login_Activity.this,MainActivity.class);
                    startActivity(intent2);
                    finish();
                }else if (enteredName.getText().toString().equals("")){
                    Toast.makeText(Login_Activity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(Login_Activity.this, "Enter your PIN", Toast.LENGTH_SHORT).show();

            }
        });



        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
       // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = Login_Activity.this.getSharedPreferences("mainSharedPrefFile", Context.MODE_PRIVATE);
                pref.edit().clear().apply();
                AppDatabaseSer db  = AppDatabaseSer.getDbInstance(Login_Activity.this.getApplicationContext());
                db.clearAllTables();
                AppDatabase dbmov  = AppDatabase.getDbInstance(Login_Activity.this.getApplicationContext());
                dbmov.clearAllTables();
                dialog.dismiss();
                recreate();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        logoutPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}