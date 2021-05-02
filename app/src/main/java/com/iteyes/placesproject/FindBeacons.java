package com.iteyes.placesproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class FindBeacons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacons_find);

//        final Notification notification = (Notification) getApplication();
//
//        RequirementsWizardFactory
//                .createEstimoteRequirementsWizard()
//                .fulfillRequirements(this,
//                        new Function0<Unit>() {
//                            @Override
//                            public Unit invoke() {
//                                Log.d("app", "requirements fulfilled");
//                                notification.enableBeaconNotifications();
//                                return null;
//                            }
//                        },
//                        new Function1<List<? extends Requirement>, Unit>() {
//                            @Override
//                            public Unit invoke(List<? extends Requirement> requirements) {
//                                Log.e("app", "requirements missing: " + requirements);
//                                return null;
//                            }
//                        },
//                        new Function1<Throwable, Unit>() {
//                            @Override
//                            public Unit invoke(Throwable throwable) {
//                                Log.e("app", "requirements error: " + throwable);
//                                return null;
//                            }
//                        });

    }
}
