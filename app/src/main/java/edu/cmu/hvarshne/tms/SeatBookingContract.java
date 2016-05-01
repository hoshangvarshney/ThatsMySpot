package edu.cmu.hvarshne.tms;

import android.provider.BaseColumns;

/**
 * Created by hvarshne on 4/5/2016.
 */
public final class SeatBookingContract {
    public static final String BOOKING_AVAILABLE = "A";
    public static final String BOOKING_OCCUPIED = "O";
    public SeatBookingContract(){}
    public static abstract class SeatEntry implements BaseColumns {
        public static final String TABLE_NAME = "seat_booking";
        public static final String COLUMN_NAME_BOOKING_ID = "booking_id";
        public static final String COLUMN_NAME_ANDREW_ID = "andrew_id";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_NAME_HALL = "hall";
        public static final String COLUMN_NAME_ROOM = "room";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_MAJOR = "major";
        public static final String COLUMN_NAME_MINOR = "minor";
    }
}
