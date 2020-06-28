package com.example.cv19protection.activity.InnerActivity;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
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