package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.app.Application;

import androidx.room.Room;

public class WorkitoutApp extends Application {
    AppDatabase db;
    private static WorkitoutApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "program-db").build();
        mInstance = this;
    }
    public static synchronized WorkitoutApp getInstance() {
        return mInstance;
    }
    public AppDatabase getDatabase(){
        return db;
    }
}
