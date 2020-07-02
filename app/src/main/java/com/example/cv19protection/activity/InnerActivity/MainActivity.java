package com.example.cv19protection.activity.InnerActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.cv19protection.R;
import com.example.cv19protection.activity.Fragments.InformationFragment;
import com.example.cv19protection.activity.Fragments.MapFragment;
import com.example.cv19protection.activity.Fragments.SelfAssessmentFragment;
import com.example.cv19protection.activity.Model.MySession;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MySession mySession;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySession=new MySession(this);



        Log.d(TAG, "onCreate: id="+mySession.getid());
        Log.d(TAG, "onCreate: visit="+mySession.getVisit());


        if(!mySession.getVisit()){

            Intent in=new Intent(this,IntoActivity.class);
            startActivity(in);
            finish();
        }else if(mySession.getid().equals("")){
            Intent in=new Intent(this,LoginActivity.class);
            startActivity(in);
            finish();
        }else{
           // Toast.makeText(this, ""+mySession.get_mobile_number(), Toast.LENGTH_SHORT).show();
            init();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {

        viewPager=findViewById(R.id.viewPager_main_screen);

        ArrayList<Fragment> fr_list = new ArrayList<Fragment>();
        fr_list.add(new InformationFragment());
        fr_list.add(new MapFragment());
        fr_list.add(new SelfAssessmentFragment());
        MainViewPager mMyAdapter = new MainViewPager(getSupportFragmentManager(),fr_list);
        viewPager.setAdapter(mMyAdapter);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* viewPager.setAdapter(new MainViewPager(getSupportFragmentManager()));*/

        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.newsletter);
        tabLayout.getTabAt(1).setIcon(R.drawable.map);
        tabLayout.getTabAt(2).setIcon(R.drawable.doctor);

    }


    public class MainViewPager extends FragmentStatePagerAdapter{

        ArrayList<Fragment> fr_list;

        public MainViewPager(FragmentManager fm,ArrayList<Fragment> fr_list) {
            super(fm);
            this.fr_list=fr_list;
        }

        @Override
        public Fragment getItem(int position)
        {
            return fr_list.get(position);
        }

        public int getCount() {

            return fr_list.size();

        }
    }


}



