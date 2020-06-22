package com.example.cv19protection.activity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class LoginFragment extends Fragment {

    private TextView sign_up_textview;
    private EditText mobile_number,password;
    private Button login_button;
    public String TAG="Durga";


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

        mobile_number=view.findViewById(R.id.l_mobile_number);
        password=view.findViewById(R.id.l_password);
        login_button=view.findViewById(R.id.l_login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();

                Log.d("Data_found", "onClick: "+mobile_number.getText().toString());
                Log.d("Data_found", "onClick: "+password.getText().toString());

                Map<String,String> map=new HashMap<String,String>();

                map.put("MobileNumber",mobile_number.getText().toString());
                map.put("Password",password.getText().toString());

                send_request("login",map);
            }
        });

    }


    public void send_request(String action, final Map<String,String> map){

        final RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        Log.d(TAG, "send_request: hello");

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                "http://google.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error_message", "onErrorResponse: "+error.getMessage());
                Toast.makeText(getContext(),""+error,Toast.LENGTH_SHORT);

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