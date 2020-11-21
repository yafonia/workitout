package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface ExerciseSetDao {
    @Query("SELECT * FROM exercise_set")
    LiveData<List<ItemAndExercise>> getetList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Item> exercise_set);

    @Update
    void update(Item exercise_set);

}
