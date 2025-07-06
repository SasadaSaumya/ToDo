package com.sasadev.todo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.sasadev.todo.fragment.HomeFragment;
import com.sasadev.todo.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private int selectedItemId = R.id.home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chipNavigationBar = findViewById(R.id.bottom_nav);
        chipNavigationBar.setItemSelected(R.id.home, true);
        loadFragment(new HomeFragment());

        chipNavigationBar.setOnItemSelectedListener(i -> {
            selectedItemId = i;
            if (i == R.id.home) {
                loadFragment(new HomeFragment());
            } else if (i == R.id.profile) {
                loadFragment(new ProfileFragment());
            }
        });


    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContentFrameLayout,fragment).commit();

    }
}