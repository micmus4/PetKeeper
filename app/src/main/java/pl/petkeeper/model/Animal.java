package pl.petkeeper.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Animal
{
    private Integer id;

    private String name;

    private Date dateOfBirth;

    private String photoName;

    private Species specie;
}
