package pl.petkeeper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.petkeeper.model.Datemark;
import pl.petkeeper.model.Species;

@Dao
public interface DatemarkDAO {
    @Query( "SELECT * FROM datemarks" )
    List<Datemark> getAllDatemarks();

    @Insert
    void insertDatemark( Datemark datemark );

    @Query("SELECT * FROM datemarks WHERE date =:date")
    Datemark getDatemark(String date );

    @Query("DELETE FROM datemarks WHERE date =:date")
    void deleteDatemark(String date );
}
