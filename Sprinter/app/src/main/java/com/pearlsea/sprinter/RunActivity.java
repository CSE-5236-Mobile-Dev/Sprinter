package com.pearlsea.sprinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pearlsea.sprinter.db.SprinterDatabase;

import java.util.Set;

public class RunActivity extends AppCompatActivity implements View.OnClickListener {

    public SprinterDatabase appDatabase;
    public ImageView settingsButton;
    public FragmentManager fragmentManager;

    Fragment startRunFragment;
    Button startRunButton;
    Fragment runningFragment;

    /* Declare Fragments */
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        settingsFragment = new SettingsFragment(this);

        /* Add the Start Run Fragment */
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        startRunFragment = new StartRunFragment();
        fragmentTransaction.add(R.id.runFragmentContainer, startRunFragment);
        fragmentTransaction.commit();

        runningFragment = new RunningFragment();


        settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == settingsButton.getId()) handleSettingsButton();
    }

    public void handleSettingsButton() {
        Log.d("RunActivity", "Settings Button Triggered");

        Fragment activeFragment = fragmentManager.findFragmentById(R.id.runFragmentContainer);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (activeFragment instanceof SettingsFragment) {
            // Settings Fragment Currently Active - Go Back to Main Run Fragment
            transaction.replace(R.id.runFragmentContainer, startRunFragment);
        }
        else {
            // Settings Fragment Not Active - Show Settings Fragment
            transaction.replace(R.id.runFragmentContainer, settingsFragment);
        }

        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void transitionToRunning() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.runFragmentContainer, runningFragment);

        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void transitionToWelcomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}