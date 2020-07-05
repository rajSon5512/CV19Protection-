package com.example.cv19protection.activity.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cv19protection.R;
import com.example.cv19protection.activity.Model.SubCases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class InformationFragment extends Fragment {


    private ArrayList<SubCases> subCases;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private ArrayAdapter counties_adapter;
    private CharSequence date_sequence;
    private String[] counties=new String[]{
            "Select Your Country",
            "India",
            "Germany",
            "Canada",
            "Brazil",
            "Russia"

    };

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        init(view);


        counties_adapter=new ArrayAdapter(getActivity(),R.layout.spinner_textview,counties);
        counties_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(counties_adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Date date=new Date();
                date_sequence= DateFormat.format("yyyy-MM-dd",date);
                String url="https://api.covid19tracking.narrativa.com/api/"+date_sequence+"/country/"+counties[i];

                Log.d("Response_Data", "onItemSelected: "+url);

                if(!counties[i].equals("Select Your Country")){

                    progressBar.setVisibility(View.VISIBLE);
                    send_request(url);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        return view;
    }

    private void init(View view) {
        spinner=view.findViewById(R.id.spinner_search);
        recyclerView=view.findViewById(R.id.recyclerview_for_information);
        progressBar=view.findViewById(R.id.progressBar_intro);

    }

    public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder>{

        private Context context;
        private ArrayList<SubCases> subCasesArrayList;

        public InfoAdapter(Context context,ArrayList subCasesArrayList) {
            this.context = context;
            this.subCasesArrayList=subCasesArrayList;
            setHasStableIds(true);
        }

        @NonNull
        @Override
        public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view=LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_small_entry,viewGroup,false);

            return new InfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull InfoViewHolder infoViewHolder, int i) {

            SubCases temp_sub=subCases.get(i);

            infoViewHolder.active_cases.setText("Total Cases:"+temp_sub.getS_tc());
            infoViewHolder.today_confirm.setText("Today New Confirmed Cases:"+temp_sub.getTnc());
            infoViewHolder.today_death.setText("Deaths Number:"+temp_sub.getS_td());
            infoViewHolder.yester_day_cases.setText("Yesterday Open Cases:"+temp_sub.getS_yoc());
            infoViewHolder.today_recover.setText("Today Recover:"+temp_sub.getS_tr());


            if(temp_sub.getS_name().equals(counties[spinner.getSelectedItemPosition()])){

                infoViewHolder.itemView.setBackground(getResources().getDrawable(R.drawable.background_of_countires));
                infoViewHolder.state_textview.setText("Country:"+temp_sub.getS_name());

            }else{
                infoViewHolder.state_textview.setText("State:"+temp_sub.getS_name());
            }

            infoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Transition changeBound=new ChangeBounds();
                    changeBound.setInterpolator(new OvershootInterpolator());
                    changeBound.setDuration(10000);

                    if(!infoViewHolder.show){

                        infoViewHolder.constraintSetnew.applyTo(infoViewHolder.constraintLayout);
                        infoViewHolder.show=true;
                    }else{
                        infoViewHolder.constraintSetold.applyTo(infoViewHolder.constraintLayout);
                        infoViewHolder.show=false;
                    }


                }
            });


        }

        @Override
        public int getItemCount() {
            return subCasesArrayList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    private class InfoViewHolder extends RecyclerView.ViewHolder {

        private ConstraintSet constraintSetold=new ConstraintSet();
        private ConstraintSet constraintSetnew=new ConstraintSet();
        private ConstraintLayout constraintLayout;
        private Boolean show=false;
        private TextView state_textview,active_cases,today_confirm,today_death,yester_day_cases,today_recover;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.recylerview_entry_small);
            constraintSetold.clone(constraintLayout);
            constraintSetnew.clone(getContext(),R.layout.recyclerview_wider_effect);

            state_textview=itemView.findViewById(R.id.place_cases);
            active_cases=itemView.findViewById(R.id.active_cases);
            today_confirm=itemView.findViewById(R.id.today_confirm_cases);
            today_death=itemView.findViewById(R.id.today_dealths);
            yester_day_cases=itemView.findViewById(R.id.yesterday_open_cases);
            today_recover=itemView.findViewById(R.id.today_recovered);

        }
    }


    public void send_request(String action){

        recyclerView.setVisibility(View.GONE);

        subCases=new ArrayList<>();

        if(subCases.size()!=0){

            subCases.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
        }



        final RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        StringRequest stringRequest=new StringRequest(
                Request.Method.GET,
                action,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            JSONObject date_object=jsonObject.getJSONObject("dates");

                            //Log.d("my_nation", "onResponse: "+date_object);

                            JSONObject counties_json=date_object.getJSONObject(date_sequence.toString());

                            JSONObject c_json=counties_json.getJSONObject("countries");

                            JSONObject selected_spinner=c_json.getJSONObject(counties[spinner.getSelectedItemPosition()]);

                            Log.d("Nation", "onResponse: "+selected_spinner);


                            SubCases s_temp=new SubCases(
                                    selected_spinner.getString(SubCases.name),
                                    selected_spinner.getString(SubCases.today_confirmed),
                                    selected_spinner.getString(SubCases.today_deaths),
                                    selected_spinner.getString(SubCases.today_recovered),
                                    selected_spinner.getString(SubCases.yesterday_open_cases),
                                    selected_spinner.getString(SubCases.yesterday_recovered),
                                    selected_spinner.getString(SubCases.today_new_confirmed)
                            );

                            subCases.add(s_temp);

                            String regions=selected_spinner.getString("regions");

                            Log.d("regional_degree", "onResponse: "+regions);

                            JSONArray jsonArray=new JSONArray(regions);

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject temp=jsonArray.getJSONObject(i);

                                Log.d("today_confirmed", "onResponse: "+temp.getString(SubCases.name));

                                SubCases subCases_temp=new SubCases(
                                        temp.getString(SubCases.name),
                                        temp.getString(SubCases.today_confirmed),
                                        temp.getString(SubCases.today_deaths),
                                        temp.getString(SubCases.today_recovered),
                                        temp.getString(SubCases.yesterday_open_cases),
                                        temp.getString(SubCases.yesterday_recovered),
                                        selected_spinner.getString(SubCases.today_new_confirmed)
                                );
                                subCases.add(subCases_temp);
                            }
                            recyclerView.setAdapter(new InfoAdapter(getContext(),subCases));

                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error_load", "onResponse: "+e.getMessage());

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error_message", "onErrorResponse: "+error.toString());
                        Toast.makeText(getContext(),""+error.toString(),Toast.LENGTH_SHORT);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
        requestQueue.add(stringRequest);

    }



}
