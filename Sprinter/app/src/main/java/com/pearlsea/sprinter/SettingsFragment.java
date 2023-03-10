package com.pearlsea.sprinter;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.operation_threads.DeleteUserThread;
import com.pearlsea.sprinter.mvvm.MetricsViewModel;

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


        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.delete_account_button) handle_account_deletion();
    }

    private void handle_account_deletion() {
        Thread deleteUser = new DeleteUserThread(DatabaseInstanceSingleton.activeUser, getContext(), callingActivity);
        deleteUser.start();
    }
}