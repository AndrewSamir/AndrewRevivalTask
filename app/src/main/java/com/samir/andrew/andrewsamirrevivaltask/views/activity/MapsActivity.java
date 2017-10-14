package com.samir.andrew.andrewsamirrevivaltask.views.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samir.andrew.andrewsamirrevivaltask.R;
import com.samir.andrew.andrewsamirrevivaltask.adapter.ViewPagerAdapter;
import com.samir.andrew.andrewsamirrevivaltask.googlePlacesApis.ModelGooglePlacesApis;
import com.samir.andrew.andrewsamirrevivaltask.googlePlacesApis.Results;
import com.samir.andrew.andrewsamirrevivaltask.interfaces.HandleRetrofitResp;
import com.samir.andrew.andrewsamirrevivaltask.retorfitconfig.HandleCalls;
import com.samir.andrew.andrewsamirrevivaltask.utilities.DataEnum;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, HandleRetrofitResp, LocationListener {


    protected LocationManager locationManager;

    @Bind(R.id.vpContent)
    ViewPager vpContent;

    private GoogleMap mMap;

    List<Marker> markers;
    ViewPagerAdapter adapter;
    ModelGooglePlacesApis modelGooglePlacesApis;

    //google maps
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);
        initCurrentLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        markers = new ArrayList<>();

        for (Results result : modelGooglePlacesApis.getResults()) {

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(result.getName()));

            marker.setTag(result);
            marker.setSnippet(result.getId());
            markers.add(marker);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Marker m : markers) {
                    if (m.getSnippet().equals(marker.getSnippet())) {
                        m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        vpContent.setCurrentItem(markers.indexOf(m));
                    } else
                        m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                return true;
            }
        });


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (height * 0.20); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.animateCamera(cu);

        initViewPager();

    }

    private void initCurrentLocation() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
//============================//
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            //Log.e("expt", e.getMessage());
            e.printStackTrace();
        }
        //=============================//

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLoc();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void initLoc() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        TastyToast.makeText(this, mCurrentLocation.getLatitude() + "", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

        if (mCurrentLocation == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            HandleCalls.getInstance(this).setonRespnseSucess(this);
            HandleCalls.getInstance(this).callGetGooglePlaces(DataEnum.getPlacesFlag.name(), mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude(), "100");
        }
    }

    @Override
    public void onResponseSuccess(String flag, Object o) {

        if (flag.equals(DataEnum.getPlacesFlag.name())) {
            modelGooglePlacesApis = (ModelGooglePlacesApis) o;
            //  Log.d("statusTest", modelGooglePlacesApis.getStatus());
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    private void initViewPager() {

        ArrayList<Results> mItems = (ArrayList<Results>) modelGooglePlacesApis.getResults();

        adapter = new ViewPagerAdapter(MapsActivity.this, mItems, vpContent);
        vpContent.setAdapter(adapter);
        markers.get(0).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < markers.size(); i++) {
                    if (i == position) {
                        markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    } else
                        markers.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Latitude", location.getLatitude() + "");

        HandleCalls.getInstance(this).setonRespnseSucess(this);
        HandleCalls.getInstance(this).callGetGooglePlaces(DataEnum.getPlacesFlag.name(), location.getLatitude() + "," + location.getLongitude(), "100");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status " + status);

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");

    }
}
