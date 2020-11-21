package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_set")
public class ExerciseSet {

    @NonNull
    @PrimaryKey
    private String id_exercise;

    @ColumnInfo(name = "id_program")
    private String id_program;

    @ColumnInfo(name = "set_list")
    private String set_list;

    public ExerciseSet(String id_exercise, String id_program, String set_list) {
        this.id_exercise = id_exercise;
        this.id_program = id_program;
        this.set_list = set_list;
    }

    public String getIdExercise() { return id_exercise; }

    public String getIdProgram() {
        return id_program;
    }

    public String getSetName() {
        return set_list;
    }


}
