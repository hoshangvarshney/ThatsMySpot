package edu.cmu.hvarshne.tms.beacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

import edu.cmu.hvarshne.tms.MainActivity;
import edu.cmu.hvarshne.tms.dao.SeatEntryDAO;

/**
 * This is a test class written to test pairing and un-pairing with an Estimote beacon.
 * Created by hvarshne on 4/22/2016.
 * Andrew ID: hvarshne
 * @author Hoshang Varshney
 */
public class BeaconApp extends Application {
    private BeaconManager beaconManager;
    private static SeatEntryDAO seatEntryDAO;

    @Override
    public void onCreate(){
        super.onCreate();
        seatEntryDAO = new SeatEntryDAO(this);
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        25081, 12424));
            }
        });
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification("Entered Beacon Region.", "Entered Beacon.");
            }
            @Override
            public void onExitedRegion(Region region) {
                showNotification("Exited Beacon Region.", "Exited Beacon.");
            }
        });
    }
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
