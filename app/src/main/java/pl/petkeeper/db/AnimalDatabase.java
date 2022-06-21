package pl.petkeeper.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import pl.petkeeper.model.Animal;

@Database( entities = { Animal.class }, version = 1 )
public abstract class AnimalDatabase extends RoomDatabase
{
    public abstract AnimalDAO getAnimalDAO();
}
