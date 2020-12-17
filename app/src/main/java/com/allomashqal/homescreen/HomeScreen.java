package com.allomashqal.homescreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.allomashqal.R;
import com.allomashqal.homescreen.fragments.HomePageFrag;
import com.allomashqal.homescreen.fragments.MyAccountFrag;
import com.allomashqal.homescreen.fragments.MyBookingFrag;
import com.allomashqal.homescreen.fragments.NotificationsFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreen extends AppCompatActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.bottomnavigation)
    BottomNavigationView bottomnavigation;
    FragmentManager manager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new  HomePageFrag());
        fragmentTransaction.commit();

        listeners();
    }

    private void listeners() {
        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container,new  HomePageFrag());
                        fragmentTransaction.commit();

                        return true;

                    case R.id.booking:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container,new MyBookingFrag());
                        fragmentTransaction.commit();

                        return true;

                    case R.id.Notifications:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container,new NotificationsFrag());
                        fragmentTransaction.commit();

                        return true;

                    case R.id.myaccount:
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.container,new MyAccountFrag());
                        fragmentTransaction.commit();

                        return true;

                }

                return false;
            }
        });
    }
}