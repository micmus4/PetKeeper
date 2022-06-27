package pl.petkeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity( tableName = "species" )
public class Species
{
    @PrimaryKey( autoGenerate = true )
    @NonNull
    @ColumnInfo( name = "id" )
    private Integer id;

    @NonNull
    @ColumnInfo( name = "name" )
    private String name;

    @ColumnInfo( name = "info" )
    private String info;

    public Species() {
    }

    public Species(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.info = downloadInfo( name );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return name;
    }

    public String downloadInfo(String name){
        URL url;
        try {
            url = new URL("https://en.wikipedia.org/w/index.php?action=raw&title=" + name.replace(" ", "_"));
        } catch (MalformedURLException e) {
            return "Couldn't find any info";
        }
        String text = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String line = null;
            while (null != (line = br.readLine())) {
                line = line.trim();
                if (!line.startsWith("|")
                        && !line.startsWith("{")
                        && !line.startsWith("}")
                        && !line.startsWith("<center>")
                        && !line.startsWith("---")) {
                    text += line;
                }
                if (text.length() > 200) {
                    break;
                }
            }
            return text;
        } catch (IOException e) {
            return "Couldn't find any info";
        }
    }
}
