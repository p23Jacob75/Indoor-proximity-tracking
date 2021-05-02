package com.iteyes.placesproject;

public class Room {
    private String mVenue;
    private String mVenueRoom;

    public Room(String Venue, String VenueRoom){
        mVenue = Venue;
        mVenueRoom = VenueRoom;
    }

    public String getVenue(){return mVenue;}
    public String getVenueRoom(){return mVenueRoom;}
}
