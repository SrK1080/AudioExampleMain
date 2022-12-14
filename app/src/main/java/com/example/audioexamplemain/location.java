//package com.example.audioexamplemain;
//
//import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;
//
//import android.content.Context;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//public class location implements LocationListener {
//    private TextView latituteField;
//    private TextView longitudeField;
//    private LocationManager locationManager;
//    private String provider;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//
//        // Get the location manager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        }
//        // Define the criteria how to select the locatioin provider -> use
//        // default
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Location location = locationManager.getLastKnownLocation(provider);
//            if (location != null) {
//                System.out.println("Provider " + provider + " has been selected.");
//                onLocationChanged(location);
//            } else {
//                latituteField.setText("Location not available");
//                longitudeField.setText("Location not available");
//            }
//            return;
//        }
//
//
//
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            locationManager.requestLocationUpdates(provider, 400, 1, this);
//            return;
//        }
//
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        locationManager.removeUpdates(this);
//    }
//    @Override
//    public void onLocationChanged(Location location) {
//        double lat = (double) (location.getLatitude());
//        double lng = (double) (location.getLongitude());
//        latituteField.setText(String.valueOf(lat));
//        longitudeField.setText(String.valueOf(lng));
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        Toast.makeText(this, "Enabled new provider " + provider,
//                Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        Toast.makeText(this, "Disabled provider " + provider,
//                Toast.LENGTH_SHORT).show();
//    }}