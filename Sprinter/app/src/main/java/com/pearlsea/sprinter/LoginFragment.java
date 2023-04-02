package com.pearlsea.sprinter;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pearlsea.sprinter.db.User;
import com.pearlsea.sprinter.mvvm.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListenert {
    EditText email;
    EditText password;
    TextView status;

    //private OnFragmentInteractionListener listener;
    LoginViewModel loginModel;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("LoginFragment", "onCreate Lifecycle Method Triggered");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("LoginFragment", "onCreateView Lifecycle Method Triggered");

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        FrameLayout loginScreenBackButton = rootView.findViewById(R.id.loginBackToWelcome);
        loginScreenBackButton.setOnClickListener(this);
        FrameLayout loginScreenLoginButton = rootView.findViewById(R.id.loginButton);
        loginScreenLoginButton.setOnClickListener(this);

        this.email = rootView.findViewById(R.id.emailAddressTextBox);
        this.password = rootView.findViewById(R.id.passwordTextBox);
        this.status = rootView.findViewById(R.id.statusMessage);
        //this.ResetStatus();

        /* Setup View Model */
        this.initializeViewModel();
        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * @param v
     */
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.loginBackToWelcome) handleBackButton();
        else if (viewId == R.id.loginButton) handleLogin();
    }
    public void handleBackButton() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.transitionToWelcome();
    }

    public void handleLogin() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        Log.d("LoginFragment", "Login Button Triggered");

        if (email.isEmpty() || password.isEmpty()) {
            // TODO: Do Any Later Required Validation Here
            return;
        }

        Log.d("LoginFragment", "Login Button Triggered");

        Thread dbOp = new loginThread(email, password, getContext(), loginModel);
        dbOp.start();
    }
    private void initializeViewModel() {
        /* Setup View Model */
        Activity activity = requireActivity();
        this.loginModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(LoginViewModel.class);

        /* Observe the Live Data - Event Style Observer */
        this.loginModel.getStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateStatusText(s);
            }
        });
        this.loginModel.getIsError().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                updateStatusColor(b);
            }
        });
        /* Set Initial State */
        this.loginModel.setStatus("", false);
    }
    private void updateStatusText(String status) {
        this.status.setText(status);

        if (status.equals("User Logedin!")) {
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
