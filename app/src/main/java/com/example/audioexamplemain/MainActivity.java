package com.example.audioexamplemain;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private MediaRecorder mediaRecorder;
    private String outputFile;
    EditText address, frequency, duration;
    Button start;
    public String sample;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public String date, time;
    TextView postalcode;
    String latitude,longitude;
    private LocationRequest locationRequest;
    LocationManager locationManager;
    private static int MIC_PERMISSION_CODE = 200;
    private static final int REQUEST_LOCATION = 1;
    final static String[] PERMISSIONS={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    final static int PERMISSIONS_ALL=1;
    public String lat;
    public String longi;
    String IMEINumber;
    public String deviceId;
    public int newf;
    public Button wikiwebb;
    private static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wikiwebb= (Button) findViewById(R.id.maps);
        wikiwebb.setEnabled(false);
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT>=32){
            requestPermissions(PERMISSIONS,PERMISSIONS_ALL);
        }
        requestLocation();


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }



        // on below line we are setting current
        // date and time to our text view.

        start = findViewById(R.id.start);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        if (isMicPresent()) {
            getMicPermission();
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //postalcode = (TextView) findViewById(R.id.address1);
                //String location = postalcode.getText().toString();

//                String latitude=Latitude(v);
//                String longtitude=Longtitude(v);
                //String location = location.getText().toString();
                //String imei=imei.toString();



                btnStart(v);
            }


        });
        wikiwebb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Maps.class);

                MainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //Request Location Now
            requestLocation();

        }
    }
    public void requestLocation(){

        if(locationManager==null){
            locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,this);

            }
        }
    }


    public void btnStart(View v) {

        try {

            frequency = findViewById(R.id.frequency1);
            duration = findViewById(R.id.duration1);
            int freq, dura;
            freq = Integer.parseInt(String.valueOf(frequency.getText()));
            dura = Integer.parseInt(String.valueOf(duration.getText()));
            int du = 1000 * dura;
            int fr = freq * 1000;

            Toast.makeText(this, "Recording has started", Toast.LENGTH_LONG).show();
            for (int i = 0; i < 5; i++) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    IMEINumber = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
                }
                deviceId=IMEINumber.toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                date = dateFormat.format(new Date());


                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                time = timeFormat.format(new Date());
                requestLocation();
                recordVoice(du);
                Thread.sleep(fr);
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                backgroundWorker.execute(date,time,deviceId,lat,longi,sample);

            }
            Toast.makeText(this, "Recording has stopped", Toast.LENGTH_LONG).show();
            wikiwebb.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void turnonGPS() {
//
//
//
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
//
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
//
//                } catch (ApiException e) {
//
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
//                            } catch (IntentSender.SendIntentException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            //Device does not have location
//                            break;
//                    }
//                }
//            }
//        });
//
//
//    }



    private void recordVoice(int d) throws IOException {
        int du = d;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        outputFile = getRecordingFilePath();
        mediaRecorder.setOutputFile(outputFile);
        mediaRecorder.prepare();
        mediaRecorder.start();
        sample= outputFile;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaRecorder!=null) {
                    mediaRecorder.stop();

                    // get all tracks from the MediaStream
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
            }
        }, du);
    }

    private boolean isMicPresent() {
        if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return false;
        } else {
            return true;
        }
    }

    private void getMicPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MIC_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath() {
        ContextWrapper contextWrapper;
        contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, UUID.randomUUID().toString() + ".mp3");
        System.out.println(file.getPath());
        return file.getPath();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
//        Log.d("mylog","Got Location: "+location.getLatitude()+","+location.getLongitude());
       lat= String.valueOf(location.getLatitude());
       longi=String.valueOf(location.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

//    @Override
////    public void onLocationChanged(@NonNull Location location) {
//        String latitude= String.valueOf(location.getLatitude());
//        String longtitude= String.valueOf(location.getLongitude());
//
//
//        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
////        try{
////            Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
////            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
////            String address=addresses.get(0).getAddressLine(0);
////
////
////        }
////        catch (Exception e){
////            e.printStackTrace();
////        }
//
//    }
//    public String Latitude(View v){
//        Location location=new Location(LocationManager.GPS_PROVIDER);
//        String latitude= String.valueOf(location.getLatitude());
//        return latitude;
//    }

//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        LocationListener.super.onStatusChanged(provider, status, extras);
//    }
//
//    @Override
//    public void onProviderEnabled(@NonNull String provider) {
//        LocationListener.super.onProviderEnabled(provider);
//    }
//
//    @Override
//    public void onProviderDisabled(@NonNull String provider) {
//        LocationListener.super.onProviderDisabled(provider);
//    }
}