package com.example.cv19protection.activity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.cv19protection.activity.Model.SubCases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class InformationFragment extends Fragment {


    private ArrayList<SubCases> subCases;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private ArrayAdapter counties_adapter;
    private CharSequence date_sequence;
    private String[] counties=new String[]{
            "India",
            "Afghanistan",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "British Indian Ocean Territory",
            "British Virgin",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cambodia",
            "Cameroon ",
            "Canada",
            "Cape Verde",
            "Cayman Islands",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos Islands",
            "Colombia",
            "Comoros",
            "Cook Islands",
            "Costa Rica",
            "Croatia",
            "Cuba ",
            "Curacao",
            "Cyprus ",
            "Czech Republic",
            "Democratic Republic of the Congo",
            "Denmark ",
            "Djibouti ",
            "Dominica ",
            "Dominican Republic",
            "East Timor",
            "Ecuador",
            "Egypt ",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea ",
            "Estonia ",
            "Ethiopia ",
            "Falkland Islands ",
            "Faroe Islands ",
            "Fiji ",
            "Finland ",
            "France",
            "French Polynesia",
            "Gabon ",
            "Gambia",
            "Georgia ",
            "Germany ",
            "Ghana ",
            "Gibraltar",
            "Greece ",
            "Greenland",
            "Grenada ",
            "Guam ",
            "Guatemala",
            "Guernsey ",
            "Guinea ",
            "Guinea-Bissau",
            "Guyana",
            "Haiti ",
            "Honduras ",
            "Hong Kong ",
            "Hungary ",
            "Iceland ",
            "India ",
            "Indonesia ",
            "Iran ",
            "Iraq ",
            "Ireland ",
            "Isle of Man ",
            "Israel",
            "Italy ",
            "Ivory Coast",
            "Jamaica ",
            "Japan ",
            "Jersey ",
            "Jordan ",
            "Kazakhstan",
            "Kenya ",
            "Kiribati",
            "Kosovo",
            "Kuwait ",
            "Kyrgyzstan",
            "Laos ",
            "Latvia",
            "Lebanon ",
            "Lesotho ",
            "Liberia ",
            "Libya ",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macau ",
            "Macedonia",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives ",
            "Mali ",
            "Malta ",
            "Marshall Islands ",
            "Mauritania",
            "Mauritius ",
            "Mayotte ",
            "Mexico ",
            "Micronesia",
            "Moldova ",
            "Monaco ",
            "Mongolia",
            "Montenegro",
            "Montserrat",
            "Morocco ",
            "Mozambique",
            "Myanmar ",
            "Namibia ",
            "Nauru ",
            "Nepal ",
            "Netherlands",
            "Netherlands Antilles",
            "New Caledonia ",
            "New Zealand ",
            "Nicaragua ",
            "Niger ",
            "Nigeria",
            "Niue ",
            "North Korea",
            "Northern Mariana Islands",
            "Norway ",
            "Oman ",
            "Pakistan",
            "Palau ",
            "Palestine",
            "Panama ",
            "Papua New Guinea",
            "Paraguay ",
            "Peru ",
            "Philippines ",
            "Pitcairn ",
            "Poland ",
            "Portugal",
            "Puerto Rico",
            "Qatar ",
            "Republic of the Congo",
            "Reunion ",
            "Romania ",
            "Russia",
            "Rwanda ",
            "Saint Barthelemy",
            "Saint Helena",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Martin",
            "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines",
            "Samoa ",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia ",
            "Senegal ",
            "Serbia ",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Sint Maarten",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia ",
            "South Africa",
            "South Korea ",
            "South Sudan ",
            "Spain ",
            "Sri Lanka",
            "Sudan ",
            "Suriname",
            "Svalbard and Jan Mayen ",
            "Swaziland ",
            "Sweden ",
            "Switzerland",
            "Syria ",
            "Taiwan",
            "Tajikistan",
            "Tanzania ",
            "Thailand ",
            "Togo ",
            "Tokelau ",
            "Tonga ",
            "Trinidad and Tobago ",
            "Tunisia ",
            "Turkey ",
            "Turkmenistan",
            "Turks and Caicos Islands",
            "Tuvalu ",
            "U.S. Virgin Islands",
            "Uganda ",
            "Ukraine ",
            "United Arab Emirates",
            "United Kingdom ",
            "United States ",
            "Uruguay ",
            "Uzbekistan ",
            "Vanuatu ",
            "Vatican ",
            "Venezuela ",
            "Vietnam ",
            "Wallis and Futuna",
            "Western Sahara",
            "Yemen ",
            "Zambia",
            "Zimbabwe"};

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
                send_request(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false));

        return view;
    }

    private void init(View view) {
        spinner=view.findViewById(R.id.spinner_search);
        recyclerView=view.findViewById(R.id.recyclerview_for_information);

    }

    public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder>{

        private Context context;

        public InfoAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view=LayoutInflater.from(context).inflate(R.layout.recyclerview_small_entry,viewGroup,false);

            return new InfoViewHolder(view,context);
        }

        @Override
        public void onBindViewHolder(@NonNull InfoViewHolder infoViewHolder, int i) {

            SubCases temp_sub=subCases.get(i);

            infoViewHolder.active_cases.setText("Active Cases:"+temp_sub.getS_tc());
            infoViewHolder.today_confirm.setText("Today New Confirmed Cases:"+temp_sub.getTnc());
            infoViewHolder.today_death.setText("Today Deaths:"+temp_sub.getS_td());
            infoViewHolder.yester_day_cases.setText("Yesterday Open Cases:"+temp_sub.getS_yoc());
            infoViewHolder.today_recover.setText("Yesterday Open Cases:"+temp_sub.getS_tr());


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
            return subCases.size();
        }
    }

    private class InfoViewHolder extends RecyclerView.ViewHolder {

        private ConstraintSet constraintSetold=new ConstraintSet();
        private ConstraintSet constraintSetnew=new ConstraintSet();
        private ConstraintLayout constraintLayout;
        private Boolean show=false;
        private Context context;
        private TextView state_textview,active_cases,today_confirm,today_death,yester_day_cases,today_recover;

        public InfoViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            this.context=context;
            constraintLayout=itemView.findViewById(R.id.recylerview_entry_small);
            constraintSetold.clone(constraintLayout);
            constraintSetnew.clone(context,R.layout.recyclerview_wider_effect);

            state_textview=itemView.findViewById(R.id.place_cases);
            active_cases=itemView.findViewById(R.id.active_cases);
            today_confirm=itemView.findViewById(R.id.today_confirm_cases);
            today_death=itemView.findViewById(R.id.today_dealths);
            yester_day_cases=itemView.findViewById(R.id.yesterday_open_cases);
            today_recover=itemView.findViewById(R.id.today_recovered);

        }
    }


    public void send_request(String action){


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

                            subCases=new ArrayList<SubCases>();

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
                            recyclerView.setAdapter(new InfoAdapter(getContext()));
                            recyclerView.getAdapter().notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error_load", "onResponse: "+e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error_message", "onErrorResponse: "+error.toString());
                        Toast.makeText(getContext(),""+error.toString(),Toast.LENGTH_SHORT);

                    }
                }
        );
        requestQueue.add(stringRequest);

    }



}
