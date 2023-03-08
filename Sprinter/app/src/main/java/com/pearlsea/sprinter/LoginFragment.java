package com.pearlsea.sprinter;
import static android.app.PendingIntent.getActivity;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.widget.FrameLayout;

import com.google.android.material.textfield.TextInputEditText;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */

// TODO: Rename and change types and number of parameters
public class LoginFragment extends MainActivity {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

    }

    public void setContentView(int fragment_login) {
        Log.d("SignupFragment", "onCreateView Lifecycle Method Triggered");

        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        FrameLayout signupScreenBackButton = rootView.findViewById(R.id.signupBackToWelcome);

        View loginScreenBackButton = null;
        loginScreenBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.transitionToWelcome();
            }
        });

    }

    public void LoginHandler(View target) {

        Intent myintent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("key1", "GFG :- Main Activity");

////        pass user input
        TextInputEditText username = (TextInputEditText) findViewById(R.id.email);
        TextInputEditText password = (TextInputEditText) findViewById(R.id.password);

        String usernameText = email.getText().toString();
        String passwordText = password.getText().toString();

        bundle.putString("username", usernameText);
        bundle.putString("password", passwordText);

        myintent.putExtras(bundle);
        startActivity(myintent);

    }
}