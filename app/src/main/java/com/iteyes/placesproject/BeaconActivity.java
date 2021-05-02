package com.iteyes.placesproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class BeaconActivity extends AppCompatActivity {

    //Property to hold proximity
    private ProximityObserver proximity;
    private ImageView image;
    private ProgressBar progress;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacons);
        progress = (ProgressBar)findViewById(R.id.dloading);
        image = (ImageView) findViewById(R.id.roompic);

        EstimoteCloudCredentials cloudCredentials =
                new EstimoteCloudCredentials("jacob-chan-s-app-mek", "8faa06944edafdc732e1a939f42db0a0");

        //Create proximity observer
        this.proximity =
                new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        //Create proximity zones
        //Coconut
        final ProximityZone coconutZone = new ProximityZoneBuilder()
                .forTag("Study room")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityZoneContext) {
                        String roomOwner = proximityZoneContext.getAttachments().get("Room-owner");
                        Log.d("app", "Welcome to " + roomOwner + "'s table!");
                        progress.setVisibility(View.GONE);
                        String whichRoom = "Welcome to Study room!";
                        nearestroom(whichRoom);
                        image.setImageResource(R.drawable.studyroom);
                        image.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityZoneContext) {
                        Log.d("app", "Bye");
                        progress.setVisibility(View.VISIBLE);
                        image.setVisibility(View.INVISIBLE);
                        String whichroom = "Scanning...";
                        nearestroom(whichroom);
                        return null;
                    }
                })
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> proximityZoneContexts) {
                        List<String> roomOwner = new ArrayList<>();
                        for (ProximityZoneContext proximityZoneContext : proximityZoneContexts){
                            roomOwner.add(proximityZoneContext.getAttachments().get("Room-owner"));
                        }
                        Log.d("app", "Nearest table: " + roomOwner);
                        String who = "Number of occupants" + roomOwner;
                        nearestboi(who);
                        return null;
                    }
                })
                .build();
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this, new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximity.startObserving(coconutZone);
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

        //mint
        final ProximityZone mintZone = new ProximityZoneBuilder()
                .forTag("Master room")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityZoneContext) {
                        String roomOwner = proximityZoneContext.getAttachments().get("Room-owner");
                        Log.d("app", "Welcome to Master room");
                        progress.setVisibility(View.GONE);
                        String whichRoom = "Welcome to Master room!";
                        nearestroom(whichRoom);
                        image.setImageResource(R.drawable.masterroom);
                        image.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityZoneContext) {
                        Log.d("app", "Bye");
                        progress.setVisibility(View.VISIBLE);
                        image.setVisibility(View.INVISIBLE);
                        String whichroom = "Scanning...";
                        nearestroom(whichroom);
                        return null;
                    }
                })
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> proximityZoneContexts) {
                        List<String> roomOwner = new ArrayList<>();
                        for (ProximityZoneContext proximityZoneContext : proximityZoneContexts){
                            roomOwner.add(proximityZoneContext.getAttachments().get("Room-owner"));
                        }
                        Log.d("app", "Nearest table: " + roomOwner);
                        String who = "Number of occupants" + roomOwner;
                        nearestboi(who);
                        return null;
                    }
                })
                .build();
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this, new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximity.startObserving(mintZone);
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

        //Blueberry
        final ProximityZone blueberryZone = new ProximityZoneBuilder()
                .forTag("Bed room")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityZoneContext) {
                        String roomOwner = proximityZoneContext.getAttachments().get("Room-owner");
                        Log.d("app", "Welcome to Bed room!");
                        progress.setVisibility(View.GONE);
                        String whichRoom = "Welcome to Bed room!";
                        nearestroom(whichRoom);
                        image.setImageResource(R.drawable.bedroom);
                        image.setVisibility(View.VISIBLE);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityZoneContext) {
                        Log.d("app", "Bye");
                        progress.setVisibility(View.VISIBLE);
                        image.setVisibility(View.INVISIBLE);
                        String whichroom = "Scanning...";
                        nearestroom(whichroom);
                        return null;
                    }
                })
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> proximityZoneContexts) {
                        List<String> roomOwner = new ArrayList<>();
                        for (ProximityZoneContext proximityZoneContext : proximityZoneContexts){
                            roomOwner.add(proximityZoneContext.getAttachments().get("Room-owner"));
                        }
                        Log.d("app", "Nearest table: " + roomOwner);
                        String who = "Number of occupants" + roomOwner;
                        nearestboi(who);
                        return null;
                    }
                })
                .build();
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this, new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximity.startObserving(blueberryZone);
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

    private void nearestroom(String message) {
        TextView quantityTextView = (TextView) findViewById(R.id.nearestroom);
        quantityTextView.setText(message);
    }

    private void nearestboi(String message) {
        TextView quantityTextView = (TextView) findViewById(R.id.nearestboi);
        quantityTextView.setText(message);
    }
}
