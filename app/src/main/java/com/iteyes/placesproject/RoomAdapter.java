package com.iteyes.placesproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomAdapter extends ArrayAdapter<Room> {

    public RoomAdapter(Activity context, ArrayList<Room> rooms){
        super(context, 0, rooms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Room currentRoom = getItem(position);

        TextView venueTextView = (TextView) listItemView.findViewById(R.id.venue);
        venueTextView.setText(currentRoom.getVenue());

        TextView venueRoomTextView = (TextView) listItemView.findViewById(R.id.venueroom);
        venueRoomTextView.setText(currentRoom.getVenueRoom());

        View textContainer = listItemView.findViewById(R.id.text_container);

        return listItemView;
    }
}
