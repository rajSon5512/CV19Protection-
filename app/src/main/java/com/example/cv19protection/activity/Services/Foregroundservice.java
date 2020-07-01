package com.example.cv19protection.activity.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Foregroundservice extends Service {

    private static final String TAG = "Foregroundservice";
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static Handler handler;
    public static Runnable runnable;
    public FusedLocationProviderClient fusedLocationProviderClient;
    public MySession mySession;
    public Boolean found=false;

    @Override
    public boolean stopService(Intent name) {
        handler.removeCallbacks(runnable);
        return super.stopService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mySession=new MySession(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Forground Activity Start..!!")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();

        handler=new Handler();

        final int[] i = {0};

         runnable = new Runnable() {
            public void run() {
                i[0] = i[0] +1;
                Log.d(TAG, "run: hello world ="+ i[0]);

                if(!found){
                    handler.postDelayed(this,10000);
                }

                getDeviceLocation();

            }
        };

        handler.postDelayed(runnable, 5000);
        return START_NOT_STICKY;
    }



    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getDeviceLocation() {

        Log.d(TAG, "getDeviceLocation: getting the location of device.");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        try {

            Task task = fusedLocationProviderClient.getLastLocation();
            task.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isComplete()){
                        Log.d(TAG, "onComplete: Location Found...!!");

                        Location location= (Location) task.getResult();

                        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                        //  moveCameraWithMarker(latLng,"My Location");

                        Map<String,String> map=new HashMap<>();
                        map.put("lat",String.valueOf(location.getLatitude()));
                        map.put("long",String.valueOf(location.getLongitude()));
                        map.put("id",mySession.getid());
                        send_request("fetch_infected_people.php",map);

                    }else {
                        Log.d(TAG, "onComplete: Location not found !!");
                    }

                }
            });

        }catch (Exception e){
            Log.d(TAG, "getDeviceLocation: "+e.getMessage());
        }

    }

    public void send_request(String action, final Map<String,String> map)
    {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        Log.d(TAG, "service_method: hello");

        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                getString(R.string.url)+action,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Log.d(TAG, "onResponse: got response ="+s);

                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            String success=jsonObject.getString("success");

                            if(success.equals("true")){

                                String array=jsonObject.getString("data");

                                JSONArray jsonArray=new JSONArray(array);

                                if(jsonArray.length()==0){

                                    Log.d(TAG, "onResponse: User Safe..!!");
                                    found=false;

                                }else{

                                    if(jsonArray.length()>0){

                                        Log.d(TAG, "onResponse: found infected person");
                                        found=true;
                                        create_alert_notification();

                                    }

                                   /* for(int i=0;i<jsonArray.length();i++){

                                        JSONObject object=jsonArray.getJSONObject(i);
                                        double lat=Double.parseDouble(object.getString("latitude"));
                                        double lan=Double.parseDouble(object.getString("longitude"));

                                        Log.d(TAG, "Found onResponse: lat "+lat+"and long="+lan);
                                        LatLng latLng=new LatLng(lat,lan);
                                    }*/

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d(TAG, "onResponse: "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error_message", "onErrorResponse: "+error.toString());
                        Toast.makeText(getApplicationContext(),""+error.toString(),Toast.LENGTH_SHORT);

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

    private void create_alert_notification() {

        Log.d(TAG, "create_alert_notification: ");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("CV19 Infected Detected..!!")
                .setContentText("Please take required precautions..!!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }


}
