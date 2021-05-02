package com.iteyes.placesproject;

import android.os.Bundle;
import android.widget.ListView;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class WayFinding extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_list);

        final ArrayList<Room> rooms = new ArrayList<Room>();

        rooms.add(new Room("Hall9", "Blk48"));
        rooms.add(new Room("South Spine", "S2-B2"));

        RoomAdapter adapter = new RoomAdapter(this, rooms);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}
