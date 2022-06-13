package pl.petkeeper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Species
{
    private Integer id;

    private String name;

    private String info;
}
