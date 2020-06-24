package com.example.cv19protection.activity.InnerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.cv19protection.R;
import com.example.cv19protection.activity.Fragments.InformationFragment;
import com.example.cv19protection.activity.Fragments.MapFragment;
import com.example.cv19protection.activity.Fragments.SelfAssessmentFragment;
import com.example.cv19protection.activity.Model.MySession;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MySession mySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySession=new MySession(this);

        if(!mySession.getVisit()){

            Intent in=new Intent(this,IntoActivity.class);
            startActivity(in);
            finish();
        }else if(mySession.getid().equals("-1")){
            Intent in=new Intent(this,LoginActivity.class);
            startActivity(in);
            finish();
        }else{

            Toast.makeText(this, ""+mySession.get_mobile_number(), Toast.LENGTH_SHORT).show();
            init();
        }
    }

    private void init() {

        viewPager=findViewById(R.id.viewPager_main_screen);
        viewPager.setAdapter(new MainViewPager(getSupportFragmentManager()));

        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.newsletter);
        tabLayout.getTabAt(1).setIcon(R.drawable.map);
        tabLayout.getTabAt(2).setIcon(R.drawable.doctor);

    }


    public class MainViewPager extends FragmentStatePagerAdapter{

        public MainViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {


            switch (i){

                case 0:return new InformationFragment();
                case 1:return new MapFragment();
                default:return new SelfAssessmentFragment();

            }

        }


        @Override
        public int getCount() {
            return 3;
        }
    }


}



