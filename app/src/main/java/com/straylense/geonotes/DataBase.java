package com.straylense.geonotes;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(
        entities = {NoteEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class DataBase extends RoomDatabase {

    private static final String DB_NAME = "database";
    private static DataBase instance = null;

    public synchronized static DataBase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DB_NAME)
                    .addCallback(
                            new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }

                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(() -> {
                                    });
                                }
                            })
                    .build();
        }
        return instance;
    }

    public synchronized static void forgetInstance() {
        instance = null;
    }

}
