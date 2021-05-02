package com.iteyes.placesproject;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.skyfishjy.library.RippleBackground;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class OutdoorNav extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private Button btnFind;
    private RippleBackground ripple;

    private final float DEFAULT_ZOOM = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_map);

        final Notification notification = (Notification) getApplication();

//        materialSearchBar = findViewById(R.id.searchBar);
        btnFind = findViewById(R.id.btn_find);
        ripple = findViewById(R.id.ripple_bg);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.omap);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(OutdoorNav.this);
        Places.initialize(OutdoorNav.this, getString(R.string.google_maps_api));
        placesClient = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

//        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//                startSearch(text.toString(), true, null, true);
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//                if(buttonCode == MaterialSearchBar.BUTTON_NAVIGATION){
//                    //The 3 lines
//                }
//                else if(buttonCode == MaterialSearchBar.BUTTON_BACK){
//                    materialSearchBar.disableSearch();
//                }
//            }
//        });
//        materialSearchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                final FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
//                        .setTypeFilter(TypeFilter.ADDRESS)
//                        .setSessionToken(token)
//                        .setQuery(s.toString())
//                        .build();
//                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
//                        if(task.isSuccessful()){
//                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
//                            if(predictionsResponse != null){
//                                predictionList = predictionsResponse.getAutocompletePredictions();
//                                List<String> suggestionsList = new ArrayList<>();
//                                for(int i=0; i<predictionList.size(); i++){
//                                    AutocompletePrediction prediction = predictionList.get(i);
//                                    suggestionsList.add(prediction.getFullText(null).toString());
//                                }
//                                materialSearchBar.updateLastSuggestions(suggestionsList);
//                                if(!materialSearchBar.isSuggestionsVisible()){
//                                    materialSearchBar.showSuggestionsList();
//                                }
//                            }
//                        }
//                        else{
//                            Log.i("mytag", "prediction fetching task unsuccessful");
//                        }
//                    }
//                });
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        materialSearchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
//            @Override
//            public void OnItemClickListener(int position, View v) {
//                if(position >= predictionList.size()){
//                    return;
//                }
//                AutocompletePrediction selectedPrediction = predictionList.get(position);
//                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
//                materialSearchBar.setText(suggestion);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        materialSearchBar.clearSuggestions();
//                    }
//                }, 1000);
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                if(imm != null){
//                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
//                }
//                String placeId = selectedPrediction.getPlaceId();
//                List<Place .Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
//
//                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
//                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
//                    @Override
//                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
//                        Place place = fetchPlaceResponse.getPlace();
//                        Log.i("mytag", "Place found: " + place.getName());
//                        LatLng latLngOfPlace = place.getLatLng();
//                        if(latLngOfPlace != null){
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if(e instanceof ApiException){
//                            ApiException apiException = (ApiException) e;
//                            apiException.printStackTrace();
//                            int statusCode = apiException.getStatusCode();
//                            Log.i("mytag", "place not found: " + e.getMessage());
//                            Log.i("mytag", "status code: " + statusCode);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void OnItemDeleteListener(int position, View v) {
//
//            }
//        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(5000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

                SettingsClient settingsClient = LocationServices.getSettingsClient(OutdoorNav.this);
                Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

                task.addOnSuccessListener(OutdoorNav.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        getDeviceLocation();
                    }
                });
                task.addOnFailureListener(OutdoorNav.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof ResolvableApiException){
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            try{
                                resolvable.startResolutionForResult(OutdoorNav.this, 51);
                            }
                            catch (IntentSender.SendIntentException el){
                                el.printStackTrace();
                            }
                        }
                    }
                });
                LatLng curntLoc = mMap.getCameraPosition().target;
                ripple.startRippleAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        ripple.startRippleAnimation();
                        startActivity(new Intent(OutdoorNav.this, BeaconActivity.class));
                        ripple.stopRippleAnimation();
                    }
                }, 2000);
            }
        });

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                notification.enableBeaconNotifications();
                                return null;
                            }
                        },
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });

    }

    //The white button
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if(mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 190);
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(OutdoorNav.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(OutdoorNav.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });
        task.addOnFailureListener(OutdoorNav.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try{
                        resolvable.startResolutionForResult(OutdoorNav.this, 51);
                    }
                    catch (IntentSender.SendIntentException el){
                        el.printStackTrace();
                    }
                }
            }
        });

//        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                if(materialSearchBar.isSuggestionsVisible())
//                    materialSearchBar.clearSuggestions();
//                return false;
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 51){
            if(resultCode == RESULT_OK){
                getDeviceLocation();
            }
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            mLastKnownLocation = task.getResult();
                            if(mLastKnownLocation != null){
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                                        (new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()),
                                                DEFAULT_ZOOM));

                            }
                            else{
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback(){
                                    @Override
                                    public void onLocationResult(LocationResult locationResult){
                                        super.onLocationResult(locationResult);
                                        if(locationResult == null){
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                                                (new LatLng(mLastKnownLocation.getLatitude(),
                                                        mLastKnownLocation.getLongitude()),
                                                        DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        }
                        else{
                            Toast.makeText(OutdoorNav.this, "Unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
