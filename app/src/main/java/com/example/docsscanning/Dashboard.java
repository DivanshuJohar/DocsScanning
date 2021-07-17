package com.example.docsscanning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    private ActionBar toolbar;
    BottomNavigationView bottomNavigationView;
    public static final int permit_code = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new DocumentListFragment()).commit();
    }
        private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.gallery:
                            fragment = new GalleryFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
                            return true;
                        case R.id.capture:
                            fragment = new CaptureFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
                            return true;
                        case R.id.doclist:
                            fragment = new DocumentListFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
                            return true;
                    }

                    return true;
                };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.search:
                // do your code
                return true;
            case R.id.merge:
                // do your code
                return true;
        }
        return true;
    }

    public void check(View v){
        //check permission
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)==
            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                    PackageManager.PERMISSION_DENIED)
            {
                String[] permit = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permit,permit_code);
            }
        }
    }
    }

