package com.pearlsea.sprinter;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StartRunFragment extends Fragment implements View.OnClickListener {
    private Button startButton;

    public StartRunFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_start_run, container, false);

        this.startButton = rootView.findViewById(R.id.startButton);
        this.startButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.startButton) handle_start_run();
    }

    private void handle_start_run() {
        RunActivity caller = (RunActivity) getActivity();
        caller.transitionToRunning();
    }
}