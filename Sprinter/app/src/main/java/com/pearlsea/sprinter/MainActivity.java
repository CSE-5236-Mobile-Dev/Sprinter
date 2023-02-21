package com.pearlsea.sprinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pearlsea.sprinter.databinding.ActivityMainBinding;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment welcomeFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Initialize and show the Main Activity */
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Setup the Fragment Manager for Fragment Injection into the Activity */
        welcomeFragment = new WelcomeFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Add the Fragment to The Container */
        fragmentTransaction.add(R.id.loginFragmentContainer, welcomeFragment);
        fragmentTransaction.commit();
    }
}