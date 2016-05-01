package edu.cmu.hvarshne.tms.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.cmu.hvarshne.tms.SeatBookingContract;
import edu.cmu.hvarshne.tms.SeatBookingDBHelper;
import edu.cmu.hvarshne.tms.model.Booking;

/**
 * An interface to interact with the local database (SQLite)
 * Created by hvarshne on 4/5/2016.
 * Andrew ID: hvarshne
 * @author Hoshang Varshney
 */
public class SeatEntryDAO {
    private SQLiteDatabase database;
    private SeatBookingDBHelper dbHelper;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String[] allCols = new String[]{SeatBookingContract.SeatEntry.COLUMN_NAME_BOOKING_ID,
            SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID,
            SeatBookingContract.SeatEntry.COLUMN_NAME_START_TIME,
            SeatBookingContract.SeatEntry.COLUMN_NAME_END_TIME,
            SeatBookingContract.SeatEntry.COLUMN_NAME_HALL,
            SeatBookingContract.SeatEntry.COLUMN_NAME_ROOM,
            SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS,
            SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR,
            SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR};

    public SeatEntryDAO(Context context) {
        dbHelper = new SeatBookingDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Booking> findRooms(String status){
        List<Booking> bookingList = new ArrayList<Booking>();

        Cursor cur = database.query(SeatBookingContract.SeatEntry.TABLE_NAME, allCols,
                SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS + " = ?", new String[]{status}, null, null, null);
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            Booking booking = cursorToBooking(cur);
            bookingList.add(booking);
            cur.moveToNext();
        }
        cur.close();
        return bookingList;
    }

    private Booking cursorToBooking(Cursor cur) {
        Booking booking = new Booking();
        try {
            booking.setBookingId(cur.getInt(0));
            booking.setAndrewId(cur.getString(1));
            booking.setStartTime(dateFormat.parse(cur.getString(2)));
            booking.setEndTime(dateFormat.parse(cur.getString(3)));
            booking.setHall(cur.getString(4));
            booking.setRoom(cur.getString(5));
            booking.setStatus(cur.getString(6));
            booking.setMajor(cur.getString(7));
            booking.setMinor(cur.getString(8));
            return booking;
        }
        catch (ParseException pe){
            pe.printStackTrace();
        }
        return null;
    }
    public int updateBooking(int bookingId, String status){
        int i = -1;

        ContentValues values = new ContentValues();
        values.put(SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS, status);
        i = database.update(SeatBookingContract.SeatEntry.TABLE_NAME, //table
                values, // column value
                SeatBookingContract.SeatEntry.COLUMN_NAME_BOOKING_ID + " = ?", // selections
                new String[]{String.valueOf(bookingId)}); //selection args
        return i;
    }

    public Booking findBookingWithBeaconID(String andrewID, String major, String minor, String status){
        List<Booking> bookingList = new ArrayList<Booking>();

        Cursor cur = database.query(SeatBookingContract.SeatEntry.TABLE_NAME, allCols,
                SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID + " = ? and " +
                        SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR + " = ? and " +
                        SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR + " = ? and " +
                        SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS + " = ?",
                new String[]{andrewID, major, minor, status}, null, null, null);
        cur.moveToFirst();
        System.out.println(cur.getCount());
        while (!cur.isAfterLast()) {
            Booking booking = cursorToBooking(cur);
            bookingList.add(booking);
            cur.moveToNext();
        }
        cur.close();
        if(bookingList.size() > 0) {
            return bookingList.get(0);
        }else{
            return null;
        }
    }
}
