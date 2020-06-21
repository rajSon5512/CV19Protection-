package com.example.cv19protection.activity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cv19protection.R;

public class LoginFragment extends Fragment {

    private TextView sign_up_textview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_log_in,container,false);

        init(view);

        return view;
    }

    private void init(View view) {

        sign_up_textview=view.findViewById(R.id.sign_up_textview);

        sign_up_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.login_container,new SignUpFragment()).addToBackStack("").commit();

            }
        });

    }
}
