package pl.petkeeper;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pl.petkeeper.databinding.ActivityMainBinding;
import pl.petkeeper.db.AnimalDatabase;
import pl.petkeeper.model.Alert;
import pl.petkeeper.utils.NotificationUtils;
import pl.petkeeper.utils.UpdateNotificationsIterface;

public class MainActivity extends AppCompatActivity implements UpdateNotificationsIterface {

    private ActivityMainBinding binding;
    private AnimalDatabase animalDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animalDatabase = Room.databaseBuilder( this, AnimalDatabase.class, "animalDatabase" )
                .allowMainThreadQueries().build();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility( View.INVISIBLE );
        // Passing each menu ID as a set of Ids because each
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        updateNotifications();
    }

    @Override
    public void updateNotifications() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        long currentTime = System.currentTimeMillis();

        List<Alert> alerts = animalDatabase.getAlertDAO().getAllAlerts();
        for (Alert alert: alerts){
            String hour = alert.getDueHour();
            String date = alert.getDueDate();
            String description = alert.getDescription();

            DateTimeFormatter hourToTime = DateTimeFormatter.ofPattern("H:m");
            LocalTime time = LocalTime.parse(hour, hourToTime);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate localDate = LocalDate.parse(date, dateFormatter);

            LocalDateTime localDateTime = localDate.atTime(time);

            long triggerNotification;

            ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
            triggerNotification = zdt.toInstant().toEpochMilli();

            if (triggerNotification < currentTime)
            {
                animalDatabase.getAlertDAO().deleteAlert( alert.getId() );
            } else {
                notificationUtils.setReminder(triggerNotification);
            }
        }

/*        long tenSeconds = 1000*10;
        long triggerReminder = currentTime + tenSeconds;
        notificationUtils.setReminder( triggerReminder );*/
    }

}