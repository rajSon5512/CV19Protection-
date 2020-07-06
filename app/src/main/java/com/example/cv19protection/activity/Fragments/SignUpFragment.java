package com.example.cv19protection.activity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class SignUpFragment extends Fragment {

    private EditText first_name,last_name,email,phone_number,password,confirm_password;
    private MySession mySession;
    private Button create_button;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_sign_up,container,false);

        mySession=new MySession(getContext());

        init(view);

        return view;
    }

    private void init(View view) {

        first_name=view.findViewById(R.id.s_first_name);
        last_name=view.findViewById(R.id.s_last_name);
        email=view.findViewById(R.id.s_emailid);
        phone_number=view.findViewById(R.id.s_phonenumber);
        password=view.findViewById(R.id.s_password);
        confirm_password=view.findViewById(R.id.s_confirm_password);

        progressBar=view.findViewById(R.id.progressBar_sign_up);

        create_button=view.findViewById(R.id.reg_btn);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(checkEmpty(first_name) && checkEmpty(last_name) && checkEmpty(email) && checkEmpty(phone_number) && checkEmpty(password) && checkEmpty(confirm_password)){

                    Toast.makeText(getContext(), "Please All Fields Required.", Toast.LENGTH_SHORT).show();

                }
                else if(checkemail()){

                }
                else if(!(password.getText().toString().equals(confirm_password.getText().toString()))){

                    Toast.makeText(getContext(), "Repeat password incorrenct.", Toast.LENGTH_SHORT).show();
                }else{

                    Map<String,String> map=new HashMap<>();

                    map.put("first_name",first_name.getText().toString());
                    map.put("last_name",last_name.getText().toString());
                    map.put("email",email.getText().toString());
                    map.put("password",password.getText().toString());
                    map.put("mobile_number",phone_number.getText().toString());
                    send_request("registration.php",map);

                }

            }
        });

    }

    private boolean checkemail() {

        if(email.getText().toString().isEmpty()) {
            Toast.makeText(getContext(),"enter email address",Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            if (email.getText().toString().trim().matches(emailPattern)) {
                //Toast.makeText(getContext(),"valid email address",Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

    }

    public Boolean checkEmpty(EditText editText){

        String text=editText.getText().toString();

        return text.equals("");
    }


    public void send_request(String action, final Map<String,String> map){

        progressBar.setVisibility(View.VISIBLE);
        create_button.setFocusable(false);

        final RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                getString(R.string.url)+action,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Log.d("status_error", "onResponse: "+s);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            Log.d("status_error", "onResponse: "+s);

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
                            }
                            else if(jsonObject.getString("status").equals("3")){

                                Toast.makeText(getContext(), "Mobile Number and Email Id Already Registered...!!", Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(getContext(), "NetWork Error", Toast.LENGTH_SHORT).show();
                            
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("status_error", "onResponse: "+e.getMessage());
                        }
                        progressBar.setVisibility(View.GONE);
                        create_button.setFocusable(true);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error_message", "onErrorResponse: "+error.toString());
                        Toast.makeText(getContext(),""+error.toString(),Toast.LENGTH_SHORT);
                        progressBar.setVisibility(View.GONE);
                        create_button.setFocusable(true);

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
