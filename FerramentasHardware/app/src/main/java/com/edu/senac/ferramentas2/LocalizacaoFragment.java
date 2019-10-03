package com.edu.senac.ferramentas2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.widget.TextView;


public class LocalizacaoFragment extends FragmentActivity {

    TextView txtLongitude;
    TextView txtLatitude;

    Location location;
    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_localizacao);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){

        }else{

            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        double longitude = location.getLongitude();
        double latitude = location.getLongitude();

        txtLatitude.setText("Latitude: " + latitude);
        txtLongitude.setText("Longitude: " + longitude);
    }



}

