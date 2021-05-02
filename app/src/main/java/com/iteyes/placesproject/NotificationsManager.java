package com.iteyes.placesproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NotificationsManager {
    //Declare variables
    private Context context;
    private NotificationManager notificationManager;
    private Notification helloBlueIce;
    private Notification byeBlueIce;
    private Notification helloMint;
    private Notification byeMint;
    private Notification helloCoconut;
    private Notification byeCoconut;
    private int notificationId = 1;
    //To generate notification details
    public NotificationsManager(Context context){
        this.context = context;
        this.notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.helloBlueIce = buildNotification("Good day!", "Welcome to the TheHouse");
        this.byeBlueIce = buildNotification("Thank you for visiting", "See you next time");
        this.helloMint = buildNotification("MasterRoom", "Welcome to the MasterRoom");
        this.byeMint = buildNotification("Bye!", "You have left the MasterRoom");
        this.helloCoconut = buildNotification("StudyRoom", "Welcome to the StudyRoom");
        this.byeCoconut = buildNotification("Bye!", "You have left the StudyRoom");
    }
    //Generate format of notification
    private Notification buildNotification(String title, String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }
        //To fill at this.context
        return new NotificationCompat.Builder(context, "content_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, BeaconActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }
    //Scan beacon and activate notification
    public void startMonitoring() {
        ProximityObserver proximityObserver =
                new ProximityObserverBuilder(context, ((com.iteyes.placesproject.Notification) context).cloudCredentials)
                        .onError(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();
        //ice
        ProximityZone iceZone = new ProximityZoneBuilder()
                .forTag("Bed room noti")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationId, helloBlueIce);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationId, byeBlueIce);
                        return null;
                    }
                })
                .build();

        proximityObserver.startObserving(iceZone);
    }
}

//        ProximityZone mintZone = new ProximityZoneBuilder()
//                .forTag("Master room noti")
//                .inCustomRange(3.0)
//                .onEnter(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
//                        notificationManager.notify(notificationId, helloMint);
//                        return null;
//                    }
//                })
//                .onExit(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
//                        notificationManager.notify(notificationId, byeMint);
//                        return null;
//                    }
//                })
//                .build();
//
//        ProximityZone coconutZone = new ProximityZoneBuilder()
//                .forTag("Study room noti")
//                .inCustomRange(3.0)
//                .onEnter(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
//                        notificationManager.notify(notificationId, helloCoconut);
//                        return null;
//                    }
//                })
//                .onExit(new Function1<ProximityZoneContext, Unit>() {
//                    @Override
//                    public Unit invoke(ProximityZoneContext proximityContext) {
//                        notificationManager.notify(notificationId, byeCoconut);
//                        return null;
//                    }
//                })
//                .build();

//        proximityObserver.startObserving(mintZone);
//        proximityObserver.startObserving(coconutZone);

