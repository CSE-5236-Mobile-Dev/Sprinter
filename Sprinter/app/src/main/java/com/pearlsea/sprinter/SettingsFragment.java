package com.pearlsea.sprinter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.operation_threads.DeleteUserThread;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    RunActivity callingActivity;

    public SettingsFragment(RunActivity activity) {
        this.callingActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView deleteButton = rootView.findViewById(R.id.delete_account_button);
        deleteButton.setOnClickListener(this);
        TextView logoutButton = rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.delete_account_button) handle_account_deletion();
        if (viewId == R.id.logout_button) handle_account_logout();
    }

    private void handle_account_deletion() {
        Thread deleteUser = new DeleteUserThread(DatabaseInstanceSingleton.activeUser, getContext(), callingActivity);
        deleteUser.start();
    }

    private void handle_account_logout() {
        DatabaseInstanceSingleton.activeUser = null;
        RunActivity parentActivity = (RunActivity) getActivity();
        parentActivity.transitionToWelcomeScreen();
    }
}