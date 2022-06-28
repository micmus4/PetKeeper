package pl.petkeeper.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        NotificationCompat.Builder _builder = notificationUtils.setNotification("Test", "testuje");
        notificationUtils.getManager().notify(101, _builder.build());
    }
}
