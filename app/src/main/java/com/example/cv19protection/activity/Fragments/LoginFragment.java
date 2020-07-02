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
import android.widget.ProgressBar;
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
    private static final String TAG = "LoginFragment";
    private MySession mySession;
    private TextView forget_password;
    private ProgressBar progressBar;

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

        progressBar=view.findViewById(R.id.progressBar_login_screen);

        sign_up_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.login_container,new SignUpFragment()).addToBackStack("").commit();

            }
        });

        forget_password=view.findViewById(R.id.forget_password);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget_password();
            }
        });


        mobile_number=view.findViewById(R.id.l_mobile_number);
        password=view.findViewById(R.id.l_password);
        login_button=view.findViewById(R.id.l_login_button);



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                login_button.setFocusable(false);

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

                                        String id=jsonObject.getString("id");
                                        String mobile=jsonObject.getString("mobile_number");
                                        String infected=jsonObject.getString("infected");
                                        mySession.setid(id);
                                        mySession.setMobileNumber(mobile);

                                        if(infected.equals("1")){
                                            mySession.set_Infected(true);
                                        }else{
                                            mySession.set_Infected(false);
                                        }

                                        Intent intent=new Intent(getActivity(),MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }else{

                                        Toast.makeText(getContext(), "mobile Number and password wrong..!!", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                progressBar.setVisibility(View.GONE);
                                login_button.setFocusable(true);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("resssss",volleyError.toString());
                                progressBar.setVisibility(View.GONE);
                                login_button.setFocusable(true);
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String, String>();
                        param.put("mobile_number",mobile_number.getText().toString());
                        param.put("password",password.getText().toString());
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


    public void forget_password(){

            FragmentManager fm=getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.login_container,new ForgetPasswordFragment()).addToBackStack("").commit();
    }





}