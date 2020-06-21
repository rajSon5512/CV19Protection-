package com.example.cv19protection.activity.InnerActivity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cv19protection.R;
import com.example.cv19protection.activity.Fragments.IntoFragment;

public class IntoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into);


        FragmentManager fm=getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.intoduction_container,new IntoFragment()).commit();

    }
}