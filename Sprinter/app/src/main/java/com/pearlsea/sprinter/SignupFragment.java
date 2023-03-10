package com.pearlsea.sprinter;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pearlsea.sprinter.db.operation_threads.SignupThread;
import com.pearlsea.sprinter.mvvm.SignupViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {

    EditText name;
    EditText email;
    EditText password;
    TextView status;

    SignupViewModel signupModel;

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
        /* Lifecycle Logging */
        Log.d("SignupFragment", "onCreateView Lifecycle Method Triggered");

        /* Inflate XML */
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        /* Create Button Listeners */
        FrameLayout signupScreenBackButton = rootView.findViewById(R.id.signupBackToWelcome);
        signupScreenBackButton.setOnClickListener(this);
        FrameLayout sigupScreenSignupButton = rootView.findViewById(R.id.signupButton);
        sigupScreenSignupButton.setOnClickListener(this);

        /* Save Elements */
        this.name = rootView.findViewById(R.id.nameTextBox);
        this.email = rootView.findViewById(R.id.emailAddressTextBox);
        this.password = rootView.findViewById(R.id.passwordTextBox);
        this.status = rootView.findViewById(R.id.statusMessage);

        /* Setup View Model */
        this.initializeViewModel();

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

        Thread dbOp = new SignupThread(name, email, password, getContext(), signupModel);
        dbOp.start();
    }

    private void initializeViewModel() {
        /* Setup View Model */
        Activity activity = requireActivity();
        this.signupModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SignupViewModel.class);

        /* Observe the Live Data - Event Style Observer */
        this.signupModel.getStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateStatusText(s);
            }
        });

        this.signupModel.getIsError().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                updateStatusColor(b);
            }
        });

        /* Set Initial State */
        this.signupModel.setStatus("", false);
    }

    private void updateStatusText(String status) {
        this.status.setText(status);

        if (status.equals("User Created!")) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) mainActivity.transitionToMetrics(this.email.getText().toString());
        }
    }

    private void updateStatusColor(boolean isError) {
        if (isError) {
            this.status.setTextColor(getResources().getColor(R.color.red, getContext().getTheme()));
        } else {
            this.status.setTextColor(getResources().getColor(R.color.black, getContext().getTheme()));
        }
    }
}