package com.example.layoutsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.layoutsapp.Adapters.Favourite_Movies_Adapter;
import com.example.layoutsapp.Adapters.Favourite_Series_Adapter;
import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.AppDatabaseSer;
import com.example.layoutsapp.Database.User;
import com.example.layoutsapp.Database.UserSer;
import com.example.layoutsapp.R;

import java.util.List;


public class FavouriteList_Activity extends AppCompatActivity {
    private Favourite_Movies_Adapter userListAdapterMov;
    private Favourite_Series_Adapter userListAdapterSer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_name);

        TextView tvFavText = findViewById(R.id.whichFavTextView);

        if (getIntent().getBooleanExtra("isSeries",false)){
            initRecyclerViewSer();
            loadUserListSer();
            tvFavText.setText("FAVOURITE SERIES");

        }else{
            initRecyclerViewMov();
            loadUserListMov();

        }

    }

    private void initRecyclerViewMov() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewFavList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapterMov = new Favourite_Movies_Adapter(this);
        recyclerView.setAdapter(userListAdapterMov);
    }

    private void loadUserListMov() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList =db.userDao().getAllUsers();
        userListAdapterMov.setUserList(userList);
    }



    private void initRecyclerViewSer() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewFavList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapterSer = new Favourite_Series_Adapter(this);
        recyclerView.setAdapter(userListAdapterSer);
    }

    private void loadUserListSer() {
        AppDatabaseSer db = AppDatabaseSer.getDbInstance(this.getApplicationContext());
        List<UserSer> userList =db.userDaoSer().getAllUsers();
        userListAdapterSer.setUserList(userList);
    }


//    private void saveNewUser(String firstName, String lastName) {
//        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());
//
//        User user = new User();
//        user.firstName = firstName;
//        user.lastName = lastName;
//        db.userDao().insertUser(user);
//
////        finish();
//
//    }

//    private void deleteNewUser(String firstName) {
//        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());
//
//        User user = new User();
//        db.userDao().deleteByUserId(firstName);
//
////        finish();
//
//    }
}