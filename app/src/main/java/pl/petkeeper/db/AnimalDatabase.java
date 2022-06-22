package pl.petkeeper.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import pl.petkeeper.model.Alert;
import pl.petkeeper.model.Animal;
import pl.petkeeper.model.Species;

@Database( entities = { Animal.class, Alert.class, Species.class }, version = 1 )
public abstract class AnimalDatabase extends RoomDatabase
{
    public abstract AnimalDAO getAnimalDAO();

    public abstract SpeciesDAO getSpeciesDAO();

    public abstract AlertDAO getAlertDAO();
}
