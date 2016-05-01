package edu.cmu.hvarshne.tms.beacon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import edu.cmu.hvarshne.tms.MainActivity;
import edu.cmu.hvarshne.tms.R;
import edu.cmu.hvarshne.tms.SeatBookingContract;
import edu.cmu.hvarshne.tms.beacon.BeaconListAdapter;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Region;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import edu.cmu.hvarshne.tms.R;
import edu.cmu.hvarshne.tms.model.Booking;

/**
 * Displays list of found beacons sorted by RSSI.
 * Starts new activity with selected beacon if activity was provided.
 * Modified by Hoshang Varshney. Andrew ID: hvarshne
 * Credits to author below. This code helped me a lot to focus on functionality rather than rewriting code to connect to an Estimote beacon.
 * @author wiktor.gworek@estimote.com (Wiktor Gworek)
 */
public class ListBeaconsActivity extends MainActivity{

  private static final String TAG = ListBeaconsActivity.class.getSimpleName();

  public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
  public static final String EXTRAS_BEACON = "extrasBeacon";

  private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

  private BeaconManager beaconManager;
  private BeaconListAdapter adapter;
    private int bookingId;
    private Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater inflater = (LayoutInflater) this
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View contentView = inflater.inflate(R.layout.beacon_pair_main, null, false);

      drawerLayout.removeViewAt(0);
      drawerLayout.addView(contentView, 0);
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();

      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);

    //setContentView(R.layout.main);
    // Configure device list.
    adapter = new BeaconListAdapter(this);
    final ListView list = (ListView) findViewById(R.id.device_list);
    list.setAdapter(adapter);
    list.setOnItemClickListener(createOnItemClickListener());

    // Configure BeaconManager.
    beaconManager = new BeaconManager(this);
    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
      @Override public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
        // Note that results are not delivered on UI thread.
        runOnUiThread(new Runnable() {
          @Override public void run() {
            // Note that beacons reported here are already sorted by estimated
            // distance between device and beacon.
            toolbar.setSubtitle("Found beacons: " + beacons.size());
            adapter.replaceWith(beacons);
          }
        });
      }
    });
    beaconManager.setScanStatusListener(new BeaconManager.ScanStatusListener() {
      @Override public void onScanStart() {
        list.setEnabled(true);
        list.setAlpha(1.0f);
      }

      @Override public void onScanStop() {
        list.setEnabled(false);
        list.setAlpha(0.5f);
      }
    });
  }

  @Override protected void onDestroy() {
    beaconManager.disconnect();

    super.onDestroy();
  }

  @Override protected void onResume() {
    super.onResume();

    if (SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
      startScanning();
    }
  }

  @Override protected void onStop() {
    beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);

    super.onStop();
  }

  private void startScanning() {
    toolbar.setSubtitle("Scanning...");
    adapter.replaceWith(Collections.<Beacon>emptyList());
    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
      @Override public void onServiceReady() {
        beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
      }
    });
  }

  private AdapterView.OnItemClickListener createOnItemClickListener() {
    return new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

          // Check in DB for this andrew id, current timestamp and selected beacon major and minor combo.
          // If a booking is found with above set of parameters, then proceed to connect and save booking id in a variable.
          LinearLayout outerLayout = (LinearLayout)parent.getChildAt(0);
          String majorString = ((TextView)((LinearLayout)((LinearLayout)((LinearLayout)outerLayout.getChildAt(1)).getChildAt(1)).getChildAt(0)).getChildAt(1)).getText().toString();
          String minorString = ((TextView)((LinearLayout)((LinearLayout)((LinearLayout)outerLayout.getChildAt(1)).getChildAt(1)).getChildAt(1)).getChildAt(1)).getText().toString();
          String major = majorString.split(":")[1].trim();
          String minor = minorString.split(":")[1].trim();
          try {
              seatEntryDAO.open();

              final Booking booking = seatEntryDAO.findBookingWithBeaconID("hvarshne", major, minor, SeatBookingContract.BOOKING_OCCUPIED);
              if (booking != null) {
                  bookingId = booking.getBookingId();
                  beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                      @Override
                      public void onServiceReady() {
                          beaconManager.startMonitoring(new Region(
                                  "monitored region",
                                  UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                                  Integer.parseInt(booking.getMajor()), Integer.parseInt(booking.getMinor())));
                      }
                  });
                  beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                      @Override
                      public void onEnteredRegion(Region region, List<Beacon> list) {
                        // If user enters within specified time, Show the pairing message again.
                      }

                      @Override
                      public void onExitedRegion(Region region) {
                          // Update the status of booking ID as Available (A) after a period of 15 minutes.
                          try {
                              seatEntryDAO.open();
                              seatEntryDAO.updateBooking(bookingId, SeatBookingContract.BOOKING_AVAILABLE);
                              final AlertDialog.Builder builder = new AlertDialog.Builder(ListBeaconsActivity.this);
                              builder.setMessage("You've been unpaired with room " + booking.getHall() + "::" + booking.getRoom())
                                      .setTitle("Pairing lost with the room!");
                              builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {
                                      // User clicked OK button
                                      dialog.dismiss();
                                  }
                              });
                              AlertDialog dialogPop = builder.create();
                              dialogPop.show();
                          }catch (SQLException sqle){
                              sqle.printStackTrace();
                          }finally {
                              seatEntryDAO.close();
                          }
                      }
                  });
                  // Show a dialog box saying "paired with room!"
                  AlertDialog.Builder builder = new AlertDialog.Builder(ListBeaconsActivity.this);
                  builder.setMessage("Paired with room " + booking.getHall() + "::" + booking.getRoom())
                          .setTitle("Room Booking");
                  builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          // User clicked OK button
                          dialog.dismiss();
                      }
                  });
                  AlertDialog dialogPop = builder.create();
                  dialogPop.show();

              } else {
                  //show dialog you don't have a booking.
                  AlertDialog.Builder builder = new AlertDialog.Builder(ListBeaconsActivity.this);
                  builder.setMessage("Cannot pair with this room ")
                          .setTitle("Room Booking");
                  builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          // User clicked OK button
                          dialog.dismiss();
                      }
                  });
                  AlertDialog dialogPop = builder.create();
                  dialogPop.show();
              }
          }catch (SQLException sqle){
              sqle.printStackTrace();
          }finally {
              seatEntryDAO.close();
          }
      }
    };
  }

}
