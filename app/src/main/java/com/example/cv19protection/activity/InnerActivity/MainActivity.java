package com.example.cv19protection.activity.InnerActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.cv19protection.activity.Services.Foregroundservice;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cv19protection.R;
import com.example.cv19protection.activity.Fragments.InformationFragment;
import com.example.cv19protection.activity.Fragments.MapFragment;
import com.example.cv19protection.activity.Fragments.SelfAssessmentFragment;
import com.example.cv19protection.activity.Model.MySession;

import java.util.ArrayList;
import java.util.Date;

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
            check_daily_dialog_box();
            Log.d(TAG, "onCreate: "+mySession.get_Infected());

        }
    }

    private void check_daily_dialog_box() {

        Date date=new Date();
        CharSequence date_sequence= DateFormat.format("yyyy-MM-dd",date);

        if(!date_sequence.toString().equals(mySession.getvisitDate())){
            show_custom_dialogbox();
            mySession.setvisitDate(date_sequence.toString());
        }

    }

    private void show_custom_dialogbox() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        //Setting message manually and performing action on button click
        builder.setMessage(getString(R.string.please_take_diagnoses))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        viewPager.setCurrentItem(3,true);

                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Please take self assessment test later.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Cv19protection");
        alert.show();

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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:
                 MapFragment.handler.removeCallbacks(MapFragment.runnable);

                mySession.setid("");
                 mySession.set_Infected(false);
                 mySession.setMobileNumber("");
                mySession.setvisitDate("");

                 if(mySession.get_notify()){

                     mySession.set_notify(false);
                     stopService();
                 }



                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();

        }


    return true;
    }


    public void stopService() {

        Intent serviceIntent = new Intent(this, Foregroundservice.class);
        this.stopService(serviceIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: onstart called");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: onpause called");
        if(MapFragment.runnnable_on){
            Log.d(TAG, "onPause: runnable_stop");
            MapFragment.handler.removeCallbacks(MapFragment.runnable);
        }
        
        //MapFragment.handler.removeCallbacks(MapFragment.runnable;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: onStop method called");
       

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: onDestroy called");

    }
}



