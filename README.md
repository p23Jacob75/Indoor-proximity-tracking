# Indoor-proximity-tracking
This repository stores the code used for my final year project. 

**Project description**  
This project applies some concepts of indoor localization and tracking algorithms to 
develop a mobile application (Easy Navigation) with outdoor GPS location estimation, 
Indoor BLE location estimation and in-app notification capabilities. The application 
is built using Android Studio platform via Java and XML programming where the functions 
of GPS outdoor navigation and Indoor Localization and overall app layout be presented. 

**Bluetooth Low Energy**  
Bluetooth Low Energy is designed based on Bluetooth but uses of the least power for 
communications. BLE can operate on devices that uses button-cell batteries which can 
operate its iBeacon for a few months or even years. BLE achieves low power consumption 
by reducing the memory in the device and reducing the amount of over the air data rate. 
BLE iBeacons transfer small periodic data packets to mobile devices. 
BLE can be used for proximity marketing campaigns and indoor localization.
Accuracy: 1-5m
Feasibility: No extra infrastructure required
Power usage: Very Low
Cost: Low
Coverage: More than 100m range is achievable

**Estimote Proximity Beacons**  
Estimote Proximity Beacons operates on either scanning mode or advertising mode. 
When the beacon is on scanning mode, it is able to detect for BLE devices nearby 
and update on the Cloud.

The Estimote Proximity Beacons are registered with the buyer’s email and can be
used to create their own apps using Estimote Cloud. From there, tags can be assigned 
to each beacon which acts like a name or ID. This way we are able to program each 
iBeacon individually.

**Easy Navigation App overview**  
The main idea for the app is to simulate a user finding a specific room in a building. 
The starting location of the user can either be indoors or outdoors, hence location 
tracking using GPS and BLE is required. The main activity of the app needs to provide 
both functions where Wayfinding displays the outdoor location of the user and Rooms 
tell the user which room they are at.

**Architecture of app**  
![image](https://github.com/p23Jacob75/Indoor-proximity-tracking/assets/83536201/2074ffb1-929c-4c17-bb05-db925dbec965)
