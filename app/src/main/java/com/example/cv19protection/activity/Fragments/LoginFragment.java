package com.example.cv19protection.activity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.example.cv19protection.activity.InnerActivity.MainActivity;
import com.example.cv19protection.activity.Model.MySession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private TextView sign_up_textview;
    private EditText mobile_number,password;
    private Button login_button;
    public String TAG="Durga";
    private MySession mySession;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_log_in,container,false);

        mySession=new MySession(getContext());

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

                RequestQueue ja=Volley.newRequestQueue(getContext());

                StringRequest s=new StringRequest(
                        Request.Method.POST,
                        getString(R.string.url) + "login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                                Log.d(TAG, "onResponse: "+s);

                                try {
                                    JSONObject jsonObject=new JSONObject(s);

                                    if(jsonObject.getString("status").equals("1")){

                                        Intent intent=new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        Toast.makeText(getContext(), ""+jsonObject.getString("Id"), Toast.LENGTH_SHORT).show();
                                        mySession.setid(jsonObject.getString("Id"));
                                        mySession.setMobileNumber(jsonObject.getString("mobile_number"));

                                    }else{
                                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "onResponse: "+e.getMessage());
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("resssss",volleyError.toString());
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();
                        param.put("MobileNumber",mobile_number.getText().toString());
                        param.put("Password",password.getText().toString());
                        return param;
                    }
                };

                ja.add(s);

                /*Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();

                Log.d("Data_found", "onClick: "+mobile_number.getText().toString());
                Log.d("Data_found", "onClick: "+password.getText().toString());

                Map<String,String> map=new HashMap<String,String>();

                map.put("MobileNumber",mobile_number.getText().toString());
                map.put("Password",password.getText().toString());

                send_request("login.php",map);*/
            }
        });

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

                        Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                        Log.d("Error_response", "onResponse: "+s);
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error_message", "onErrorResponse: "+error.toString());
                Toast.makeText(getContext(),""+error.toString(),Toast.LENGTH_SHORT);

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