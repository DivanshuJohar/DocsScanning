package com.example.docsscanning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private ActionBar toolbar;
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    ListView myList;
    ArrayList<String> list ;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
         searchView = findViewById(R.id.searchView);
         myList = findViewById(R.id.searchlistview);
//         list.add("");
  //       adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    //     myList.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.doclist);
        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new CaptureFragment()).commit();
    }
        private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                       /* case R.id.gallery:
                            fragment = new GalleryFragment();
                            fragmentmanager(fragment);
                           // getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
                            return true;*/
                        case R.id.capture:
                            fragment = new CaptureFragment();
                            fragmentmanager(fragment);
                           // getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
                            return true;
                        case R.id.doclist:
                            fragment = new DocumentListFragment();
                            fragmentmanager(fragment);
                            //getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
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
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.search:
                // do your code
                //fragment = new GalleryFragment();
                //fragmentmanager(fragment);
                return true;
            case R.id.merge:
                // do your code
                return true;
        }
        return true;
    }

    public void fragmentmanager(Fragment frag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flFragment, frag, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();

    }
}

