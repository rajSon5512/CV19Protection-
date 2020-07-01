package com.example.cv19protection.activity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cv19protection.R;
import com.example.cv19protection.activity.InnerActivity.LoginActivity;
import com.example.cv19protection.activity.Model.MySession;
import com.example.cv19protection.activity.Services.Foregroundservice;

public class SelfAssessmentFragment extends Fragment {

    private MySession mySession;
    private Button log_out_button;
    private Button start_service_button,stop_service_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_selfassessment,container,false);

        mySession=new MySession(getContext());

        init(view);

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment.handler.removeCallbacks(MapFragment.runnable);
                mySession.setMobileNumber("");
                mySession.setid("");
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


        return view;
    }

    private void init(View view) {

        log_out_button=view.findViewById(R.id.log_out_button);
        start_service_button=view.findViewById(R.id.start_button);
        stop_service_button=view.findViewById(R.id.stop_button);

        start_service_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });

        stop_service_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService();
            }
        });

    }


    public void startService() {
        Intent serviceIntent = new Intent(getActivity(), Foregroundservice.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }
    public void stopService() {

        Intent serviceIntent = new Intent(getActivity(), Foregroundservice.class);
        getActivity().stopService(serviceIntent);
    }

}
