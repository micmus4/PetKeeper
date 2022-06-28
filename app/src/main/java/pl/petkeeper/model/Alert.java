package pl.petkeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity( tableName = "alerts" )
public class Alert
{

    @PrimaryKey( autoGenerate = true )
    @NonNull
    @ColumnInfo( name = "id" )
    private Integer id;

    @NonNull
    @ColumnInfo( name = "animal_id" )
    private Integer animalId;

    @ColumnInfo( name = "description" )
    private String description;

    @ColumnInfo( name = "due_data" )
    private String dueDate;

    @ColumnInfo( name = "due_hour" )
    private String dueHour;

    public Alert(Integer id, Integer animalId, String description, String dueDate, String dueHour) {
        this.id = id;
        this.animalId = animalId;
        this.description = description;
        this.dueDate = dueDate;
        this.dueHour = dueHour;
    }

    public Alert() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueHour() { return dueHour; }

    public void setDueHour(String dueHour) { this.dueHour = dueHour; }
}
