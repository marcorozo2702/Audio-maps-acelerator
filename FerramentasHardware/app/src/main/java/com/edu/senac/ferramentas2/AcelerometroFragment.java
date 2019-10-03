package com.edu.senac.ferramentas2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcelerometroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcelerometroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcelerometroFragment extends Fragment implements SensorEventListener {

    ImageView setaEsquerda, setaDireita;


    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;
    private TextView textViewDetail;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_acelerometro, container, false);

        setaDireita = view.findViewById(R.id.sDireita);
        setaEsquerda = view.findViewById(R.id.sEsquerda);
        setaDireita.setVisibility(View.GONE);
        setaEsquerda.setVisibility(View.GONE);

//        textViewX = view.findViewById(R.id.text_view_x);
//        textViewY = view.findViewById(R.id.text_view_y);
//        textViewZ = view.findViewById(R.id.text_view_z);
        textViewDetail = view.findViewById(R.id.text_view_detail);

        mSensorManager =(SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Float x = event.values[0];
        Float y = event.values[1];
        Float z = event.values[2];

         /*
        Os valores ocilam de -10 a 10.
        Quanto maior o valor de X mais ele ta caindo para a esquerda - Positivo Esqueda
        Quanto menor o valor de X mais ele ta caindo para a direita  - Negativo Direita
        Se o valor de  X for 0 então o celular ta em pé - Nem Direita Nem Esquerda
        Se o valor de Y for 0 então o cel ta "deitado"
         Se o valor de Y for negativo então ta de cabeça pra baixo, então quanto menor y mais ele ta inclinando pra ir pra baixo
        Se o valor de Z for 0 então o dispositivo esta reto na horizontal.
        Quanto maioro o valor de Z Mais ele esta inclinado para frente
        Quanto menor o valor de Z Mais ele esta inclinado para traz.
        */
//        textViewX.setText("Posição X: " + x.intValue() + " Float: " + x);
//        textViewY.setText("Posição Y: " + y.intValue() + " Float: " + y);
//        textViewZ.setText("Posição Z: " + z.intValue() + " Float: " + z);



        if(y < 0) { // O dispositivo esta de cabeça pra baixo
                textViewDetail.setText("Virando de ponta cabeça");
            setaDireita.setVisibility(View.GONE);
            setaEsquerda.setVisibility(View.GONE);
        } else {
            if(x > 0 && z > 0) {
                textViewDetail.setText("Virando para ESQUERDA e Cima ");
                setaEsquerda.setVisibility(View.VISIBLE);
                setaDireita.setVisibility(View.GONE);
            }
            if(x > 0 && z < 0) {
                textViewDetail.setText("Virando para ESQUERDA e baixo ");
                setaEsquerda.setVisibility(View.VISIBLE);
                setaDireita.setVisibility(View.GONE);
            }

            if(x < 0){
                textViewDetail.setText("Virando para DIREITA ");
                setaDireita.setVisibility(View.VISIBLE);
                setaEsquerda.setVisibility(View.GONE);
            }
        }
    }
}
