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

    @ColumnInfo( name = "specieId")
    private Integer specieId;

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

    public Animal(Integer id, String name, String dateOfBirth, String photoName, Integer specieId) {


        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photoName = photoName;
        this.specieId = specieId;
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

    public Integer getSpecieId() {
        return specieId;
    }

    public void setSpecieId(Integer specieId) {
        this.specieId = specieId;
    }

    public Animal() {
    }
}
