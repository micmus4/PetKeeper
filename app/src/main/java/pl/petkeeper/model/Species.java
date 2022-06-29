package pl.petkeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.petkeeper.utils.Jwiki;

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

    @ColumnInfo( name = "type" )
    private String type;

    public Species() {
    }

    public Species(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.info = downloadInfo( name );
        this.type = type;
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

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return name;
    }

    public String downloadInfo(String name) {
        Jwiki jwiki;
        name = name.replaceAll(" ","_");
        try { jwiki = new Jwiki(name); }
        catch(Exception e) { return "Couldn't find info :c"; }
        return (jwiki.getExtractText());
    }
}
