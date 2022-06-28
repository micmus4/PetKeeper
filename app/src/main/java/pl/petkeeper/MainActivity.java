package pl.petkeeper;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import pl.petkeeper.databinding.ActivityMainBinding;
import pl.petkeeper.utils.NotificationUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility( View.INVISIBLE );
        // Passing each menu ID as a set of Ids because each
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        updateNotifications();
    }

    private void updateNotifications() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        long currentTime = System.currentTimeMillis();
        long tenSeconds = 1000*10;
        long triggerReminder = currentTime + tenSeconds;
        notificationUtils.setReminder( triggerReminder );
    }

}