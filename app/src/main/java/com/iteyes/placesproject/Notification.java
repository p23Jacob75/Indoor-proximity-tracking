package com.iteyes.placesproject;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

public class Notification extends Application {

    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials
            ("jacob-chan-s-app-mek", "8faa06944edafdc732e1a939f42db0a0");
    private NotificationsManager notificationsManager;

    public void enableBeaconNotifications(){
        notificationsManager = new NotificationsManager(this);
        notificationsManager.startMonitoring();
    }
}
