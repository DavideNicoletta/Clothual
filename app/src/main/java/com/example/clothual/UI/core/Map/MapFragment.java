package com.example.clothual.UI.core.Map;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.clothual.R;
import com.example.clothual.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Map;

public class MapFragment extends Fragment {

    private GoogleMap mMap;

    private ActivityResultLauncher<String> singlePermissionLauncher;
    private ActivityResultContracts.RequestPermission singlePermissionContract;

    private Location pos = new Location("");
    private FusedLocationProviderClient fusedLocationClient;

    private FragmentMapBinding binding;

    public MapFragment() {

    }


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pos.setLatitude(38.9);
        pos.setLongitude(16.583333);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        singlePermissionContract = new ActivityResultContracts.RequestPermission();
        singlePermissionLauncher = registerForActivityResult(singlePermissionContract, isGranted -> {
            if(isGranted){
                Log.d(TAG, "single permission has been granted");
                Location tmp = getLocation();
                pos.setLatitude(tmp.getLatitude());
                pos.setLongitude(tmp.getLongitude());
            } else {
                Log.d(TAG, "single permission has not been granted");
            }
        });
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




        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapView);
        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                LatLng latLng = new LatLng(pos.getLatitude(), pos.getLongitude());
                // When clicked on map
                // Initialize marker options
                MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                markerOptions.position(latLng);
                // Set title of marker
                markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                // Remove all marker
                googleMap.clear();
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                // Add marker on map
                googleMap.addMarker(markerOptions);
            }
        });
    }


    private Location getLocation(){
        Location tmp = new Location("");
        boolean multiplePermissionsStatus =
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if(multiplePermissionsStatus) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                       if(location != null){
                           tmp.setLatitude(location.getLatitude());
                           tmp.setLongitude(location.getLongitude());
                       }
                    });
        } else {
            Log.d(TAG, "Permission has not been granted");
            singlePermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        return tmp;
    }

}

