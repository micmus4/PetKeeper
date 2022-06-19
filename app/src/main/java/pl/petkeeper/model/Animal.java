package pl.petkeeper.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Animal
{
    private Integer id;

    private String name;

    private Date dateOfBirth;

    private String photoName;

    private Species specie;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhotoName() {
        return photoName;
    }

    public Species getSpecie() {
        return specie;
    }

    public Animal(Integer id, String name, Date dateOfBirth, String photoName, Species specie) {


        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.photoName = photoName;
        this.specie = specie;
    }
}
