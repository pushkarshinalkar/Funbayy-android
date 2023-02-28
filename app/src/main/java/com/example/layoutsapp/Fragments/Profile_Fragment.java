package com.example.layoutsapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layoutsapp.Activities.FavouriteList_Activity;
import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.AppDatabaseSer;
import com.example.layoutsapp.Database.User;
import com.example.layoutsapp.Database.UserSer;
import com.example.layoutsapp.R;
import com.example.layoutsapp.Needed_Classes.SharedPref;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class Profile_Fragment extends Fragment {

    TextView watchlistC;
    TextView favourateC;


    TextView profileEditPin;
    TextView profileEditName;
    TextView profileEditPhone;
    TextView profileEditMail;
    Button UpdateProfile;
    Context context = getContext();

    CardView fav_mov;
    CardView fav_ser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_frag_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPref shared2 = new SharedPref(view.getContext());

        AppDatabase db = AppDatabase.getDbInstance(this.getActivity().getApplicationContext());
        List<User> userList =db.userDao().getAllUsers();
        String noOfFavMov = String.valueOf(userList.size());

        AppDatabaseSer dbSer = AppDatabaseSer.getDbInstance(this.getActivity().getApplicationContext());
        List<UserSer> userListSer =dbSer.userDaoSer().getAllUsers();
        String noOfFavSer = String.valueOf(userListSer.size());

        UpdateProfile = view.findViewById(R.id.updatebutt);
        fav_ser = view.findViewById(R.id.profilecard2);

        profileEditPin = view.findViewById(R.id.profilePasswordInputEditable);
        profileEditName = view.findViewById(R.id.profileTextInputEditable);
        profileEditMail = view.findViewById(R.id.profileMailInputEditable);
        profileEditPhone = view.findViewById(R.id.profilephoneInputEditable);

        watchlistC = view.findViewById(R.id.textView3);
        favourateC = view.findViewById(R.id.textView6);
        watchlistC.setText(noOfFavMov);
        favourateC.setText(noOfFavSer);


        profileEditName.setText(shared2.getNameFromProfile());
        profileEditPin.setText(shared2.getPinFromLogIn());
        profileEditMail.setText(shared2.getProfileMail());
        profileEditPhone.setText(shared2.getProfilePhoneNo());
        fav_mov = view.findViewById(R.id.profilecard1);
//        fav_ser = view.findViewById(R.id.profilecard2);

        fav_mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),FavouriteList_Activity.class);
                intent.putExtra("isSeries",false);
                startActivity(intent);
            }
        });


//        watchcountStr = shared2.WatchlistString();   --- Unnecessary variable
//        watchlistC.setText(shared2.WatchlistString());
//
////        favouratecountStr =shared2.FavouritelistString();
//        favourateC.setText(shared2.FavouritelistString());

        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ProfileNameText = profileEditName.getText().toString();
                shared2.setNameFromProfile(ProfileNameText);

                String MailText = profileEditMail.getText().toString();
                shared2.setProfileMail(MailText);

                String PhoneText = profileEditPhone.getText().toString();
                shared2.setProfilePhoneNo(PhoneText);

                if (profileEditPin.length()<4){
                    Toasty.error(view.getContext(),"Enter 4 digit PIN").show();
                }else {
                    String PinText = profileEditPin.getText().toString();
                    shared2.setPinFromLogIn(PinText);
                    Toasty.success(view.getContext(),"Profile updated successfully").show();
//                    Toasty.info(view.getContext(),"Username will be updated next time you open the app").show();
                }
            }
        });

        fav_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FavouriteList_Activity.class);
                intent.putExtra("isSeries",true);
                startActivity(intent);
            }
        });









    }
}