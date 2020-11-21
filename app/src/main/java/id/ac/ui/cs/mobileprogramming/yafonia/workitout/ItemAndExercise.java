package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

public class ItemAndExercise {

    @Embedded
    @ColumnInfo(name = "item")
    private Item program;
    @Relation(
            parentColumn = "itemId",
            entityColumn = "programSetListId"
    )

    @ColumnInfo(name = "set_list")
    private ExerciseSet set_list;


}
