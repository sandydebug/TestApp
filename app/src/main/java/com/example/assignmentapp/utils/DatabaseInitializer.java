package com.example.assignmentapp.utils;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;

import com.example.assignmentapp.POJO;
import com.example.assignmentapp.database.AppDatabase;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db,List<POJO> list,Context context) {
        PopulateDbAsync task = new PopulateDbAsync(db,list,context);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {

        //populateWithTestData(db);
    }

    private static List<POJO> addUser(final AppDatabase db, List<POJO> list) {
        db.userDao().insertAll(list);
        return list;
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private List<POJO> list;
        private Context context;

        PopulateDbAsync(AppDatabase db,List<POJO> list,Context context) {
            mDb = db;
            this.list = list;
            this.context = context;

        }

        @Override
        protected Void doInBackground(final Void... params) {
            //populateWithTestData(mDb);
            addUser(mDb,list);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
