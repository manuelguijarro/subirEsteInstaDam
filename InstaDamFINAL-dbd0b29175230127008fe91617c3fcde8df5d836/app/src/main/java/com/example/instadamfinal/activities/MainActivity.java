package com.example.instadamfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.instadamfinal.R;
import com.example.instadamfinal.fragments.HomeFragment;
import com.example.instadamfinal.fragments.PostFragment;
import com.example.instadamfinal.fragments.SearchFragment;
import com.example.instadamfinal.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    public static int idUsuario = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);
        loadToolbar();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

       bottomNavigationView.setOnItemSelectedListener(this::cambioFragmento);
    }


    private boolean cambioFragmento(MenuItem item) {
        int idItemn = item.getItemId();
        if (idItemn == R.id.menu_home){
            fragment = new HomeFragment();
        }
        if(idItemn == R.id.menu_seach){
            fragment = new SearchFragment();
        }
        if (idItemn == R.id.menu_add_post){
            fragment = new PostFragment();
        }
        if (idItemn == R.id.menu_settings){
            fragment = new SettingsFragment();
        }
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .commit();
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    private void loadToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}