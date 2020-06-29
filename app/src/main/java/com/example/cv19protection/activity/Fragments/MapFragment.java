package com.example.cv19protection.activity.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cv19protection.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private final static int REQUEST_FOR_PERMISSION = 1;
    private final static String REQUEST_FOR_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private final static String REQUEST_FOR_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean permission_allowed = false;
    private final float DEFUALT_ZOOM = 15f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        getPermission();

        return view;
    }

    private void getPermission() {

        String[] permissions = {REQUEST_FOR_COARSE, REQUEST_FOR_FINE};

        if (ContextCompat.checkSelfPermission(getContext(), REQUEST_FOR_FINE) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(getContext(), REQUEST_FOR_COARSE) == PackageManager.PERMISSION_GRANTED) {
                permission_allowed = true;
                Log.d("Location_granted", "getPermission: permission_granted");
                initMap();

            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_FOR_PERMISSION);
            }

        } else {

            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_FOR_PERMISSION);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++) {

            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                permission_allowed = true;
                Log.d("Location", "onRequestPermissionsResult:permission_granted ");
                initMap();
            } else {
                permission_allowed = false;
                break;
            }
        }


    }

    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (permission_allowed) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext()
                    , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            getLocate();


            //mMap.getUiSettings().setMyLocationButtonEnabled(false);
            //mMap.getUiSettings().setCompassEnabled(true);
            //mMap.getUiSettings().setMapToolbarEnabled(true);
        }

    }



    public void getDeviceLocation() {

        Log.d(TAG, "getDeviceLocation: getting the location of device.");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (permission_allowed) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Task task = mFusedLocationClient.getLastLocation();
                task.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if(task.isComplete()){
                            Log.d(TAG, "onComplete: Location Found...!!");

                            Location location= (Location) task.getResult();

                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());

                            moveCameraWithMarker(latLng,"Home");
                            moveCamera(latLng,DEFUALT_ZOOM);


                        }else {
                            Log.d(TAG, "onComplete: Location not found !!");
                        }

                    }
                });
            }

        }catch (Exception e){
            Log.d(TAG, "getDeviceLocation: "+e.getMessage());
        }


    }

    public void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving camera : Lat:"+latLng.latitude+"and lng:"+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    // put marker on require location

    public void moveCameraWithMarker(LatLng latLng,String title){
        Log.d(TAG, "setmarker: moving camera : Lat:"+latLng.latitude+"and lng:"+latLng.longitude);
        /*mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));*/
        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title(title);

        mMap.addMarker(markerOptions);
    }



    // find location using name

    private void getLocate(){

        Log.d(TAG, "getLocate: geoLocating");

        String searchString = "California";

        Geocoder geocoder=new Geocoder(getActivity());
        List<Address> list_address=new ArrayList<>();

        try{

            list_address=geocoder.getFromLocationName(searchString,1);

            Address address=list_address.get(0);

            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());

           // moveCamera(latLng,DEFUALT_ZOOM);

            Log.d(TAG, "getLocate: "+list_address);

        }catch(Exception e){

            Log.d(TAG, "getLocate: geoLocationError "+e.getMessage());
        }


    }


}



