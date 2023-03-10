package com.pearlsea.sprinter;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.Update;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pearlsea.sprinter.db.operation_threads.UpdateMetricsThread;
import com.pearlsea.sprinter.mvvm.MetricsViewModel;

public class MetricsFragment extends Fragment implements View.OnClickListener {

    MetricsViewModel metricsModel;
    EditText height;
    EditText weight;
    EditText age;
    TextView status;
    Spinner gender;
    String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public MetricsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_metrics, container, false);

        FrameLayout finishButton = rootView.findViewById(R.id.finishButton);
        finishButton.setOnClickListener(this);

        this.height = rootView.findViewById(R.id.heightField);
        this.weight = rootView.findViewById(R.id.weightField);
        this.age = rootView.findViewById(R.id.ageField);
        this.gender = rootView.findViewById(R.id.genderField);
        this.status = rootView.findViewById(R.id.statusMessage);

        Activity activity = requireActivity();
        this.metricsModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(MetricsViewModel.class);

        /* Observe the Live Data - Event Style Observer */
        this.metricsModel.getStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateStatusText(s);
            }
        });

        this.metricsModel.getIsError().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                updateStatusColor(b);
            }
        });

        /* Set Initial State */
        this.metricsModel.setStatus("", false);

        return rootView;
    }

    public void onClick(View v) {
        Log.d("Metric Fragment", "On Click Triggered");
        final int viewId = v.getId();
        if(viewId == R.id.finishButton) handleFinishButton();
    }

    private void handleFinishButton() {
        Log.d("Metric Fragment", "Finish Button Pressed");
        String heightString = this.height.getText().toString();
        int height = Integer.parseInt(heightString);

        String weightString = this.weight.getText().toString();
        int weight = Integer.parseInt(weightString);

        String ageString = this.age.getText().toString();
        int age = Integer.parseInt(ageString);

        String gender = this.gender.getSelectedItem().toString();

        Thread updateMetrics = new UpdateMetricsThread(this.email, height, weight, age, gender, getContext(), metricsModel);
        updateMetrics.start();
    }

    private void updateStatusText(String status) {
        Log.d("Metric Fragment", "Text Update Triggered");
        this.status.setText(status);

        if (status.equals("completed")) {
            Log.d("Metric Fragment", "Completed Triggered");
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) mainActivity.transitionToApp();
        }
    }

    private void updateStatusColor(boolean isError) {
        Log.d("Metric Fragment", "Text Update Triggered");
        if (isError) {
            this.status.setTextColor(getResources().getColor(R.color.red, getContext().getTheme()));
        } else {
            this.status.setTextColor(getResources().getColor(R.color.black, getContext().getTheme()));
        }
    }
}