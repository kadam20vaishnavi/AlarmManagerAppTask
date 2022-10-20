package in.ksystem.practicel;

import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button setalarm;
    int i=5;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setalarm=findViewById(R.id.alarmbutton);
        setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    setalarm.setText("Alarm after:"+i+"min");
                    setAlarm(getNotification(i + " min delay"), 60 * i);
                    i = i + 5;
            }
        });
    }

    private void setAlarm(Notification notification,int delay)
    {
        Intent notificationIntent = new Intent( getApplicationContext(), AlarmReceiver. class ) ;
        notificationIntent.putExtra(AlarmReceiver. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(AlarmReceiver. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( getApplicationContext(), 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay*1000 ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis ,1000 * 60 * 5 , pendingIntent); ;

    }

    private Notification getNotification (String content)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
}