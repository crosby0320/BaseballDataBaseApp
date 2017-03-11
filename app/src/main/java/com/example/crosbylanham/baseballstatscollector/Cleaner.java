package com.example.crosbylanham.baseballstatscollector;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by crosby on 3/10/17.
 */

public class Cleaner {
    String[] names = {"Crosby","crosby","tonio"};
    ArrayList<String> list;
    public Cleaner() {
        list = new ArrayList<>();
        for (String x:names) {list.add(x);}
    }
    public void removeNonGeneratedPlayers(){
        Log.d("going","to look for players" );
        Query q = FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME)
                .orderByChild("generated").equalTo(1);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot x:dataSnapshot.getChildren()) {
                            x.getRef().setValue(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot x:dataSnapshot.getChildren()){
                            if(!list.contains(x.getValue(Player.class).getName())){
                                x.getRef().setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
