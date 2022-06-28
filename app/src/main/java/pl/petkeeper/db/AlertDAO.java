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
    Alert getAlert(int id );

    @Query("SELECT * FROM alerts WHERE animal_id =:id")
    List<Alert> getAlertsOnPet( int id );
}
