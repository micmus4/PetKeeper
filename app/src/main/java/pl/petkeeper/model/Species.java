package pl.petkeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity( tableName = "species" )
public class Species
{
    @PrimaryKey( autoGenerate = true )
    @NonNull
    @ColumnInfo( name = "id" )
    private Integer id;

    @NonNull
    @ColumnInfo( name = "name" )
    private String name;

    @ColumnInfo( name = "info" )
    private String info;

    public Species() {
    }

    public Species(Integer id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return name;
    }
}
