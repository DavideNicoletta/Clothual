package com.example.clothual.UI.core.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.clothual.R;
import com.example.clothual.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapFragment extends Fragment {

    private GoogleMap mMap;
    private int ProximityRadius = 10000;
    private Location position;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 101;
    private Double latitude, longitude;
    private Task<Location> task;

    private FragmentMapBinding binding;


    public MapFragment() {

    }


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }*/
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        task = client.getLastLocation();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Per utilizzare la mappa è necessario concedere i permessi sulla posizione", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    position = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getChildFragmentManager().findFragmentById(R.id.mapView);
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;
                            latitude = position.getLatitude();
                            longitude = position.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            MarkerOptions mo = new MarkerOptions().position(latLng).title("");
                            mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(mo);
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }

                    });
                    binding.shopButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                String shops = "clothing_store";
                                Object transferData[] = new Object[2];
                                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
                                if (v.getId() == R.id.shopButton) {
                                    String url = getUrl(latitude, longitude, shops);
                                    transferData[0] = mMap;
                                    transferData[1] = url;

                                    getNearbyPlaces.execute(transferData);
                                    Toast.makeText(getContext(), "Shearching clothes shops", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Showing clothes shops", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }
            }
        });
    }

    private String getUrl(Double latitude,Double longitude, String shops){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&types=" + shops);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyBFB-yRv-o6lxXh2P5Ay2nTfb-EE_OAfUk");

        return googleURL.toString();
    }

}

