package com.example.parasdhanta.stressmanagement.firebase_database;

import com.example.parasdhanta.stressmanagement.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Paras Dhanta on 11/15/2016.
 */

public class FirebaseDbUtils  {
    private final String TAG = Utils.class.getSimpleName();
    private static FirebaseDbUtils instance = null;

    public DatabaseReference getmDatabase() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        return mDatabase;
    }

    private DatabaseReference mDatabase;

    private FirebaseDbUtils(){
         //no instantiation singleton
    }

    public static FirebaseDbUtils getInstance() {
        if(instance == null){
            instance = new FirebaseDbUtils();
        }
        return instance;
    }

    public void setFirebaseDiskPersistence(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
