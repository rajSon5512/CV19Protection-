package com.example.cv19protection.activity.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cv19protection.R;
import com.example.cv19protection.activity.InnerActivity.LoginActivity;
import com.example.cv19protection.activity.Model.MySession;
import com.example.cv19protection.activity.Services.Foregroundservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SelfAssessmentFragment extends Fragment implements View.OnClickListener {

    private MySession mySession;
    private Switch s1,s2,s3,s4,s5,s6,s7;
    private Button call_help_line_button;
    private TextView suggestion_rate;
    private static final String TAG = "SelfAssessmentFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_selfassessment,container,false);

        mySession=new MySession(getContext());

        init(view);


        if(mySession.get_Infected()){

            s1.setChecked(true);
            s1.setText("Yes");

        }


        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Map<String,String> map=new HashMap<>();
                map.put("mobile_number",mySession.get_mobile_number());
                map.put("id",mySession.getid());

                if(b){
                   map.put("infected","1");
                   s1.setText("Yes");
               }else{
                    map.put("infected","0");
                    s1.setText("No");
               }

                send_request("covid_nighty_status.php",map);

            }
        });



        return view;
    }

    private void init(View view) {

        s1=view.findViewById(R.id.covid_status_switch);
        s2=view.findViewById(R.id.switch2);
        s3=view.findViewById(R.id.switch3);
        s4=view.findViewById(R.id.switch4);
        s5=view.findViewById(R.id.switch5);
        s6=view.findViewById(R.id.switch6);
        s7=view.findViewById(R.id.switch7);

        suggestion_rate=view.findViewById(R.id.suggestion_text);

        s2.setOnClickListener(this);
        s3.setOnClickListener(this);
        s4.setOnClickListener(this);
        s5.setOnClickListener(this);
        s6.setOnClickListener(this);
        s7.setOnClickListener(this);

        call_help_line_button=view.findViewById(R.id.call_helpline);

        call_help_line_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = "1057";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);

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


        @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.switch2:
                    if(s2.isChecked()){
                        suggestion_rate.setText(getString(R.string.fill_illness));
                        s2.setText("Yes");
                    }else{
                        s2.setText("No");
                    }
                break;
            case R.id.switch3:
                if(s3.isChecked()){
                    suggestion_rate.setText(getString(R.string.fill_illness));
                    s3.setText("Yes");
                }else{
                    s3.setText("No");
                }

                break;

            case R.id.switch4:
                if(s4.isChecked()){
                    suggestion_rate.setText(getString(R.string.fill_illness));
                    s4.setText("Yes");
                }else{
                    s4.setText("No");
                }

            case R.id.switch5:

                if(s5.isChecked()){
                    suggestion_rate.setText(getString(R.string.fill_illness));
                    s5.setText("Yes");
                }else{
                    s5.setText("No");
                }

                break;
            case R.id.switch6:
                if(s6.isChecked()){
                    suggestion_rate.setText(getString(R.string.suggestion_one));
                    s6.setText("Yes");
                }else{
                    s6.setText("No");
                }
                break;

            case R.id.switch7:
                if(s6.isChecked()){
                    suggestion_rate.setText(getString(R.string.fill_illness));
                    s6.setText("Yes");
                }else{
                    s6.setText("No");
                }
                break;
        };

    }


    public void send_request(String action, final Map<String,String> map){

        final RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        Log.d(TAG, "send_request: hello");

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                getString(R.string.url)+action,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        //Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            if(jsonObject.getString("status").equals("1")){

                                if(s1.isChecked()){
                                    suggestion_rate.setText(R.string.covid_active_status);
                                }

                            }else if(jsonObject.getString("status").equals("0")){

                                if(s1.isChecked()){
                                    Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
                                    s1.setChecked(false);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error_message", "onErrorResponse: "+error.toString());
                        // Toast.makeText(getContext(),""+error.toString(),Toast.LENGTH_SHORT);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

}
