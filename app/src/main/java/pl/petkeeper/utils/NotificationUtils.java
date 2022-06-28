package pl.petkeeper.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import pl.petkeeper.R;

public class NotificationUtils extends ContextWrapper {
    private NotificationManager notificationManager;
    private Context context;

    public NotificationUtils(Context aContext){
        super(aContext);
        context = aContext;
        createChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body)
    {
        return new NotificationCompat.Builder(this, "pets")
                .setSmallIcon(R.drawable.fajny_boar)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE)
        {
            NotificationChannel channel = new NotificationChannel("pets",
                  "pet_notis", NotificationManager.IMPORTANCE_DEFAULT);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager()
    {
        if (notificationManager == null)
        {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void setReminder(long timeInMillis)
    {
        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }
}
