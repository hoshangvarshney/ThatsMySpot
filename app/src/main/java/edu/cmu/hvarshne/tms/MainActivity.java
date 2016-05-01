package edu.cmu.hvarshne.tms;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.estimote.sdk.SystemRequirementsChecker;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.cmu.hvarshne.tms.beacon.ListBeaconsActivity;
import edu.cmu.hvarshne.tms.dao.SeatEntryDAO;
import edu.cmu.hvarshne.tms.model.Booking;

/**
 * Home page of the app.
 * Andrew ID: hvarshne
 * @author Hoshang Varshney
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    protected DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private EditText dateEditText;
    private EditText fromTimeText;
    private EditText toTimeText;
    private Button findRoomButton;
    protected SeatEntryDAO seatEntryDAO;

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Database initialization
        seatEntryDAO = new SeatEntryDAO(this);

        dateEditText = (EditText) findViewById(R.id.dateText);
        fromTimeText = (EditText) findViewById(R.id.fromTime);
        toTimeText = (EditText) findViewById(R.id.toTime);
        findRoomButton = (Button)findViewById(R.id.findRoomButton);

        dateEditText.setClickable(true);dateEditText.setFocusable(false);
        fromTimeText.setClickable(true);fromTimeText.setFocusable(false);
        toTimeText.setClickable(true);toTimeText.setFocusable(false);
        dateEditText.setOnClickListener(this);
        fromTimeText.setOnClickListener(this);
        toTimeText.setOnClickListener(this);
        findRoomButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.findRooms) {
            // Handle the camera action
            intent= new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.pairRoom) {
            intent = new Intent(this, ListBeaconsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            // TODO implement later
        } else if (id == R.id.nav_send) {
            // TODO implement later
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Kind of a Rule Engine to check where the user clicked/tapped and execute appropriate logic.
     * @param v View Object
     */
    @Override
    public void onClick(View v) {
        final NumberFormat format = new DecimalFormat("00");
        if (v == dateEditText) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            dateEditText.setText(format.format((monthOfYear + 1))+ "-" + format.format(dayOfMonth)  + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == toTimeText) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            toTimeText.setText(format.format(hourOfDay) + ":" + format.format(minute));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        else if (v == fromTimeText) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            fromTimeText.setText(format.format(hourOfDay) + ":" + format.format(minute));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        else if(v == findRoomButton){
            try{
                toolbar.setSubtitle("Finding rooms...");
                seatEntryDAO.open();
                List<Booking> roomList = seatEntryDAO.findRooms(SeatBookingContract.BOOKING_AVAILABLE);
                RoomListAdapter adapter = new RoomListAdapter(this, roomList);
                // Attach the adapter to a ListView
                ListView listView = (ListView) findViewById(R.id.freeRoomView);
                listView.setAdapter(adapter);
                toolbar.setSubtitle("Found " + roomList.size() + " rooms");
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }finally {
                seatEntryDAO.close();
            }
        }
    }

    /**
     * This button books a room selected by the user.
     * @param view View Object.
     */
    public void bookButtonHandler(View view){
        LinearLayout parent = (LinearLayout) view.getParent();
        Button bookButton = (Button)parent.getChildAt(1);
        TextView bookingId = (TextView) parent.getChildAt(2);
        try{
            seatEntryDAO.open();
            int status = seatEntryDAO.updateBooking(Integer.parseInt(bookingId.getText().toString()), SeatBookingContract.BOOKING_OCCUPIED);
            if(status>0){
                bookButton.setText("Booked");
                bookButton.setClickable(false);
                bookButton.setBackgroundColor(0xffdb5555);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }finally {
            seatEntryDAO.close();
        }
    }
}
