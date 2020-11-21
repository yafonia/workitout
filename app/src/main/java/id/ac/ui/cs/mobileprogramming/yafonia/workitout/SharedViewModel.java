package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Item> selected = new MutableLiveData<>();
    List<Item> program_list = new ArrayList<>();

    public void insertProgram(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class,"programs").allowMainThreadQueries().build();

        AsyncCaller asyncCaller = new AsyncCaller();
        asyncCaller.doInBackground(db);
    }

    public void setSelected(Item item) {
        selected.setValue(item);
    }

    public LiveData<List<Item>> getAllPrograms(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class,"programs").allowMainThreadQueries().build();
        LiveData<List<Item>> programs = db.itemDao().getAll();
        return programs;
    }

    public MutableLiveData<Item> getSelected() {
        return selected;
    }

    private class AsyncCaller extends AsyncTask<AppDatabase, Void, Void> {

        @Override
        protected Void doInBackground(AppDatabase... db) {

            program_list.add(new Item("0", "Full Body Burn", "Sit Up, Back Up, Push Up", "", R.drawable.full_body_burn_pic));
            program_list.add(new Item("1", "Small Waist", "Plank, In & Out Squat, Clap Jacks", "", R.drawable.cork_screw_pic));
            program_list.add(new Item("2", "Lower Abs", "Lateral Lunge Hop, Curtsy Lunges, Jumping Jacks", "", R.drawable.small_waist_pic));
            program_list.add(new Item("3", "Upper Body", "Sit Up, Back Up, Push Up", "", R.drawable.full_body_burn_pic));
            program_list.add(new Item("4", "Flat Belly", "Plank, In & Out Squat, Clap Jacks", "", R.drawable.cork_screw_pic));
            program_list.add(new Item("5", "Slim Legs", "Lateral Lunge Hop, Curtsy Lunges, Jumping Jacks", "", R.drawable.small_waist_pic));
            db[0].itemDao().insert(program_list);

            return null;
        }
    }
}
