package com.example.cv19protection.activity.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cv19protection.R;
import com.example.cv19protection.activity.Model.MySession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordFragment extends Fragment {

    private EditText f_mobile_number;
    private Button submit_button;
    private MySession mySession;
    private static final String TAG = "ForgetPasswordFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_forgetpassword,container,false);

        mySession=new MySession(getContext());

        init(view);

        return view;
    }

    private void init(View view) {

        f_mobile_number=view.findViewById(R.id.f_mobile_number);
        submit_button=view.findViewById(R.id.f_submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String f_m_number=f_mobile_number.getText().toString();

               if(f_m_number.isEmpty()){

                   Toast.makeText(getContext(), "Mobile Number Required...!!", Toast.LENGTH_SHORT).show();
               }else if(f_m_number.length()!=10){
                   Toast.makeText(getContext(), "Please Check Mobile Number..!!", Toast.LENGTH_SHORT).show();

               }else{
                   Map<String,String> map=new HashMap<>();
                   map.put("mobile_number",f_mobile_number.getText().toString());
                   send_request("send.php",map);
               }



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

                        Log.d(TAG, "onResponse: "+s.length());
                        //String sub_string=s.substring(3640);

                        if(s.length()>3000){

                            Toast.makeText(getContext(), "Your Password has been Sent on your mail.", Toast.LENGTH_LONG).show();
                            FragmentManager fm=getActivity().getSupportFragmentManager();

                            fm.beginTransaction().replace(R.id.login_container,new LoginFragment()).commit();

                        }
                        else{
                            try {
                                JSONObject jsonObject=new JSONObject(s);

                                String status=jsonObject.getString("status");

                                if(status.equals("0")){

                                    Toast.makeText(getContext(), "Nunber is not Register...!!", Toast.LENGTH_SHORT).show();
                                }else{

                                    Toast.makeText(getContext(), "Please Try Again..!!", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


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
