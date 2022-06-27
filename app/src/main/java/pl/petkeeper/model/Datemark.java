package pl.petkeeper.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "datemarks" )
public class Datemark {
    @PrimaryKey( autoGenerate = true )
    @NonNull
    @ColumnInfo( name = "id" )
    private Integer id;

    @ColumnInfo( name = "date" )
    private String date;

    @ColumnInfo( name = "text" )
    private String text;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Datemark () {}

    public Datemark(@NonNull Integer id, String date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }
}
