package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "programs")
public class Item {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "program_name")
    private String name;

    @ColumnInfo(name = "program_set")
    private String set;

    @Nullable
    @ColumnInfo(name = "program_date")
    private String date;

    @ColumnInfo(name = "program_picture")
    private int picture;


    public Item(String id, String name, String set, String date, int picture) {
        this.id = id;
        this.name = name;
        this.set = set;
        this.date = date;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSet() {
        return set;
    }

    public String getDate() {
        return date;
    }

    public int getPicture() {
        return picture;
    }
}

