package pl.petkeeper.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Alert
{
    private Integer id;

    private Animal animal;

    private String title;

    private String description;

    private Date dueDate;
}
