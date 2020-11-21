package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM programs")
    LiveData<List<Item>> getAll();

    @Query("SELECT * FROM programs WHERE id IN (:userIds)")
    List<Item> loadAllByIds(int[] userIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Item> programs);

    @Update
    void update(Item programs);

}
