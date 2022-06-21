package pl.petkeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity( tableName = "animals" )
public class Animal
{
    @PrimaryKey( autoGenerate = true )
    @NonNull
    @ColumnInfo( name = "id" )
    private Integer id;

    @ColumnInfo( name = "name" )
    private String name;

    @ColumnInfo( name = "date_of_birth" )
    private String dateOfBirth;

    @ColumnInfo( name = "photo_name" )
    private String photoName;

    @Ignore
    private Species specie;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhotoName() {
        return photoName;
    }

    public Species getSpecie() {
        return specie;
    }

    public Animal(Integer id, String name, String dateOfBirth, String photoName, Species specie) {


        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photoName = photoName;
        this.specie = specie;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public void setSpecie(Species specie) {
        this.specie = specie;
    }

    public Animal() {
    }
}
