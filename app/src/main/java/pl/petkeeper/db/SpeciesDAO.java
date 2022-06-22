package pl.petkeeper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.petkeeper.model.Animal;
import pl.petkeeper.model.Species;

@Dao
public interface SpeciesDAO
{
    @Query( "SELECT * FROM species" )
    List<Species> getAllSpecies();

    @Insert
    void insertSpecie( Species aSpecie );

    @Query("SELECT * FROM species WHERE id =:id")
    Animal getSpecie(int id );
}
