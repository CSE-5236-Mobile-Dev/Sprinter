package com.pearlsea.sprinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pearlsea.sprinter.databinding.ActivityMainBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment welcomeFragment;
    private Fragment signUpFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Initialize and show the Main Activity */
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Setup the Fragment Manager for Fragment Injection into the Activity */
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Add the Welcome Fragment to The Container */
        welcomeFragment = new WelcomeFragment();
        fragmentTransaction.add(R.id.loginFragmentContainer, welcomeFragment);
        fragmentTransaction.commit();

        /* Initialize the Signup Fragment */
        signUpFragment = new SignupFragment();
    }

    /* Response to Clicking Signup Button on Welcome Fragment */
    public void transitionWelcomeToSignup()
    {
        /* Start A Transaction */
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Replace the Current Fragment with the Signup Fragment */
        fragmentTransaction.replace(R.id.loginFragmentContainer, signUpFragment);

        /* Add the old fragment to the back button stack */
        fragmentTransaction.addToBackStack(null);

        /* Finish the fragment transaction */
        fragmentTransaction.commit();
    }
}