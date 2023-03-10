package com.pearlsea.sprinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pearlsea.sprinter.databinding.ActivityMainBinding;
import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.SprinterDatabase;
import com.pearlsea.sprinter.db.User;
import com.pearlsea.sprinter.db.UserDao;

import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SprinterDatabase appDatabase;
    private Fragment welcomeFragment;
    private Fragment signUpFragment;
    private MetricsFragment metricsFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate Lifecycle Method Triggered");

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

        /* Initialize the Metrics Fragment */
        metricsFragment = new MetricsFragment();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("MainActivity", "onPause Lifecycle Method Triggered");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MainActivity", "onResume Lifecycle Method Triggered");
    }

    /* Transition from current active fragment to signup fragment */
    public void transitionToSignup()
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

    /* Transition from current active fragment to welcome */
    public void transitionToWelcome()
    {
        /* Start A Transaction */
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Replace the Current Fragment with the Signup Fragment */
        fragmentTransaction.replace(R.id.loginFragmentContainer, welcomeFragment);

        /* Add the old fragment to the back button stack */
        fragmentTransaction.addToBackStack(null);

        /* Finish the fragment transaction */
        fragmentTransaction.commit();
    }

    public void transitionToMetrics(String email)
    {
        this.metricsFragment.setEmail(email);

        /* Start A Transaction */
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Replace the Current Fragment with the Signup Fragment */
        fragmentTransaction.replace(R.id.loginFragmentContainer, metricsFragment);

        /* Add the old fragment to the back button stack */
        fragmentTransaction.addToBackStack(null);

        /* Finish the fragment transaction */
        fragmentTransaction.commit();
    }

    public void transitionToApp()
    {
        Log.d("MainActivity", "Transition to App Called");
    }
}