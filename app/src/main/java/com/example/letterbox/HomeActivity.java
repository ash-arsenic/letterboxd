package com.example.letterbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    String[] menu = {"Popular", "Top Rated", "Latest", "Upcoming", "Now Playing"};
    private Object Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_spinner_item, menu);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new TestFragment();
                loadFragment(fragment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMovie, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.search) {
//            Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
//            return true;
//        } else if(id == R.id.setting) {
//            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
//            return true;
//        } else if(id == R.id.about) {
//            Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}