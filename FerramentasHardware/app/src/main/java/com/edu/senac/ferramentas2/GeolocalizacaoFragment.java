package com.edu.senac.ferramentas2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;


public class GeolocalizacaoFragment extends Fragment {

    TextView longitude, latitude;
    Button enviar, maps , lat, lon;
    Location location;
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_geolocalizacao, container, false);

        longitude = view.findViewById(R.id.longitude);
        latitude = view.findViewById(R.id.latitude);

        enviar = view.findViewById(R.id.buttonZap);
        maps = view.findViewById(R.id.buttonMap);
        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                enviar(view);
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location currentLocation) {
                            if (currentLocation != null) {
                                location = currentLocation;

                                Log.e("lat", location.getLatitude() + "");


                                Log.e("log", location.getLongitude() + "");
                            }
                        }
                    });
        }




        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location != null) {

                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", location.getLatitude(), location.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);} else{
                    Toast.makeText(getActivity(), "Localização não encontrada",    Toast.LENGTH_SHORT)     .show();
                }
            }
        });

        return view;
    }





    public void enviar(View view) {

        PackageManager pm = getActivity().getPackageManager();
        try {
            if (location != null) {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "Longitude: " + location.getLongitude() + ", latitude:  " + location.getLatitude();

                PackageInfo info = pm.getPackageInfo("com.whatsapp",
                        PackageManager.GET_META_DATA);
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));
            }else{
                Toast.makeText(getActivity(), "Localização não encontrada",    Toast.LENGTH_SHORT)     .show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "WhatsApp not Installed",
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
