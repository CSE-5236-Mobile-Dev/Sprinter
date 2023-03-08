package com.pearlsea.sprinter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pearlsea.sprinter.db.operation_threads.SignupThread;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {

    EditText name;
    EditText email;
    EditText password;
    TextView status;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("SignupFragment", "onCreate Lifecycle Method Triggered");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("SignupFragment", "onCreateView Lifecycle Method Triggered");

        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        FrameLayout signupScreenBackButton = rootView.findViewById(R.id.signupBackToWelcome);
        signupScreenBackButton.setOnClickListener(this);
        FrameLayout sigupScreenSignupButton = rootView.findViewById(R.id.signupButton);
        sigupScreenSignupButton.setOnClickListener(this);

        this.name = rootView.findViewById(R.id.nameTextBox);
        this.email = rootView.findViewById(R.id.emailAddressTextBox);
        this.password = rootView.findViewById(R.id.passwordTextBox);
        this.status = rootView.findViewById(R.id.statusMessage);
        this.ResetStatus();

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.signupBackToWelcome) handleBackButton();
        else if (viewId == R.id.signupButton) handleSignup();
    }

    public void handleBackButton() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.transitionToWelcome();
    }

    public void handleSignup() {
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        Log.d("SignupFragment", "Signup Button Triggered");

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // TODO: Do Any Later Required Validation Here
            return;
        }

        Log.d("SignupFragment", "Signup Button Triggered");
        // TODO: Passing the status and colors (needed to not have this message displayed all the time) is super fucking hacky and we really need a view model and live data once group understands code
        Thread dbOp = new SignupThread(name, email, password, getContext(), this.status);
        dbOp.start();
    }

    public void ResetStatus() {
        this.status.setText("");
    }
}