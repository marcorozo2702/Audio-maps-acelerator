package com.edu.senac.ferramentas2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*/
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioFragment extends Fragment {


    private static final int REQUEST_RECORD_AUDIO_PERMISSION=200;

    private static String fileName=null;

    private MediaRecorder recorder=null;

    private MediaPlayer player=null;

    private boolean permissionRecord=false;
    private String [] permissions={Manifest.permission.RECORD_AUDIO};

    boolean mStartPlaying=true;
    boolean mStartRecording=true;

    Button  gravar,escutar;
    TextView txtGravar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        //Solicita as PERMISSOES PARA O USUARIO
        ActivityCompat.requestPermissions(getActivity(),permissions,REQUEST_RECORD_AUDIO_PERMISSION);

        fileName=getActivity().getExternalCacheDir().getAbsolutePath()+"/audioSenac.3gp";

        gravar = view.findViewById(R.id.gravar);

        gravar.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          gravar();
                                      }
                                  });


        escutar = view.findViewById(R.id.escutar);
        escutar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                escutar();
            }
        });

        return view;
    }


    public void setRequestRecordAudioPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }if(!permissionRecord){
            Toast.makeText(getActivity(),
                    "Aceite as permissões",Toast.LENGTH_SHORT).show();
        }
    }


    private void gravar() {
        onRecord(mStartRecording);
        if(mStartRecording){
            gravar.setText("Gravando...");
        }else{
            gravar.setText("Gravar");
        }
        mStartRecording=!mStartRecording;
    }

    private void escutar() {
        onPlay(mStartPlaying);
        if(mStartPlaying){
            escutar.setText("Reproduzindo...");
        }else{
            escutar.setText("Reproduzir");
        }
        mStartPlaying=!mStartPlaying;
    }

    private void startPlaying(){
        player= new MediaPlayer();
        try{
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        }catch (Exception e){
            Log.e("audio","erro => startPlaying");
        }

    }

    private void stopPlaying(){
        player.release();
        player=null;
    }

    private void startRecording(){
        recorder=new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            recorder.prepare();
        }catch (Exception e){
            Log.e("audio","erro => startRecording");
        }
        recorder.start();
    }

    private void stopRecording(){
        recorder.stop();
        recorder.release();
        recorder=null;
    }

    private void onRecord(boolean start){
        if(start){
            startRecording();
        }else{
            stopRecording();
        }
    }

    private void onPlay(boolean start){
        if(start){
            startPlaying();
        }else{
            stopPlaying();
        }
    }

}
