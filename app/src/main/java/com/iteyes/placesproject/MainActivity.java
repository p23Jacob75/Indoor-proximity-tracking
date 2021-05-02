package com.iteyes.placesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button rooms;
    private Button wayfinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rooms = (Button)findViewById(R.id.beaconwhere);

        rooms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, BeaconActivity.class);
                startActivity(i);
                Toast.makeText(view.getContext(), "Your nearest room", Toast.LENGTH_SHORT).show();
            }
        });
//
        wayfinding = (Button)findViewById(R.id.OutdoorNavigating);

        wayfinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OutdoorNav.class);
                startActivity(i);
                Toast.makeText(view.getContext(), "Google maps", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

/*
        final Notification notification = (Notification) getApplication();
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

        Button navigating = (Button)findViewById(R.id.navigating);

        navigating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WayFinding.class);
                startActivity(i);
                Toast.makeText(view.getContext(), "Choose navigation area", Toast.LENGTH_SHORT).show();
            }
        });

        Button OutdoorNavigatingTest = (Button)findViewById(R.id.OutdoorNavigatingTest);

        OutdoorNavigatingTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OutdoorNavTest.class);
                startActivity(i);
                Toast.makeText(view.getContext(), "Google maps", Toast.LENGTH_SHORT).show();
            }
        });
*/

