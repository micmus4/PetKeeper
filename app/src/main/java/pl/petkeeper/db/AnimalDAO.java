package pl.petkeeper.db;

import android.media.metrics.Event;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.petkeeper.model.Animal;

@Dao
public interface AnimalDAO
{
    @Query( "SELECT * FROM animals" )
    List< Animal > getAllAnimals();

    @Insert
    void insertAnimal(Animal aAnimal );

    @Query("SELECT * FROM animals WHERE id =:id")
    Animal getAnimal(int id );
}
