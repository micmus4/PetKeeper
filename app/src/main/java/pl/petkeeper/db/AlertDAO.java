package pl.petkeeper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.petkeeper.model.Alert;
import pl.petkeeper.model.Animal;

@Dao
public interface AlertDAO
{
    @Query( "SELECT * FROM alerts" )
    List<Alert> getAllAlerts();

    @Insert
    void insertAlert( Alert aAlert );

    @Query("SELECT * FROM alerts WHERE id =:id")
    Animal getAlert(int id );
}
