package edu.cmu.hvarshne.tms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hvarshne on 4/5/2016.
 */
public class SeatBookingDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myspot.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_BOOKING_TABLE =
            "CREATE TABLE " + SeatBookingContract.SeatEntry.TABLE_NAME + " (" +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID + TEXT_TYPE + COMMA_SEP +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_START_TIME + TEXT_TYPE + COMMA_SEP +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_END_TIME + TEXT_TYPE + COMMA_SEP+
                    SeatBookingContract.SeatEntry.COLUMN_NAME_HALL + TEXT_TYPE + COMMA_SEP +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_ROOM + TEXT_TYPE + COMMA_SEP +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR + TEXT_TYPE + COMMA_SEP +
                    SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_BOOKING_TABLE =
            "DROP TABLE IF EXISTS " + SeatBookingContract.SeatEntry.TABLE_NAME;

    public SeatBookingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("App started");
        db.execSQL(SQL_DELETE_BOOKING_TABLE);
        // db.execSQL(SQL_DELETE_HALL_TABLE);
        db.execSQL(SQL_CREATE_BOOKING_TABLE);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ContentValues bookingValues = new ContentValues();
        //bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_BOOKING_ID, 1);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID, "hvarshne");

        Calendar start = Calendar.getInstance();
        start.set(2016, 4, 6, 17, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_START_TIME, dateFormat.format(start.getTime()));
        Calendar end = Calendar.getInstance();
        end.set(2016, 4, 6, 18, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_END_TIME, dateFormat.format(end.getTime()));
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_HALL, "HBH");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ROOM, "A001A");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS, SeatBookingContract.BOOKING_AVAILABLE);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR, "25081");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR, "12424");

        db.insert(SeatBookingContract.SeatEntry.TABLE_NAME, null, bookingValues);
        bookingValues.clear();

        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID, "hvarshne");

        start.set(2016, 4, 6, 17, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_START_TIME, dateFormat.format(start.getTime()));
        end.set(2016, 4, 6, 18, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_END_TIME, dateFormat.format(end.getTime()));
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_HALL, "HBH");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ROOM, "A001B");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS, SeatBookingContract.BOOKING_AVAILABLE);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR, "25081");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR, "12424");

        db.insert(SeatBookingContract.SeatEntry.TABLE_NAME, null, bookingValues);
        bookingValues.clear();

        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID, "hvarshne");

        start.set(2016, 4, 6, 17, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_START_TIME, dateFormat.format(start.getTime()));
        end.set(2016, 4, 6, 18, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_END_TIME, dateFormat.format(end.getTime()));
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_HALL, "HBH");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ROOM, "A001C");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS, SeatBookingContract.BOOKING_AVAILABLE);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR, "25081");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR, "12424");

        db.insert(SeatBookingContract.SeatEntry.TABLE_NAME, null, bookingValues);
        bookingValues.clear();

        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ANDREW_ID, "hvarshne");

        start.set(2016, 4, 6, 17, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_START_TIME, dateFormat.format(start.getTime()));
        end.set(2016, 4, 6, 18, 0);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_END_TIME, dateFormat.format(end.getTime()));
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_HALL, "HBH");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_ROOM, "A001D");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_STATUS, SeatBookingContract.BOOKING_AVAILABLE);
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MAJOR, "25081");
        bookingValues.put(SeatBookingContract.SeatEntry.COLUMN_NAME_MINOR, "12424");

        db.insert(SeatBookingContract.SeatEntry.TABLE_NAME, null, bookingValues);
        bookingValues.clear();

        //db.execSQL(SQL_CREATE_HALL_TABLE);
        System.out.println("DB started");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_BOOKING_TABLE);
        //db.execSQL(SQL_DELETE_HALL_TABLE);
        onCreate(db);
    }
}
