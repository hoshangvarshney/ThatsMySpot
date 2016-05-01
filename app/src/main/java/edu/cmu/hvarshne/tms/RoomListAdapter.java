package edu.cmu.hvarshne.tms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.cmu.hvarshne.tms.model.Booking;

/**
 * A custom ArrayAdapter which represents an available room in ListView.
 * Created by hvarshne on 4/9/2016.
 * Andrew ID: hvarshne
 * @author Hoshang Varshney
 */
public class RoomListAdapter extends ArrayAdapter<Booking> {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    public RoomListAdapter(Context context, List<Booking> bookings){
        super(context,0,bookings);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Booking booking = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_item, parent, false);
        }
        // Lookup view for data population
        TextView addr = (TextView) convertView.findViewById(R.id.free_room_item_addr);
        //TextView time = (TextView) convertView.findViewById(R.id.free_room_item_time);
        TextView bkid = (TextView) convertView.findViewById(R.id.free_room_item_bkid);
        // Populate the data into the template view using the data object
        addr.setText(booking.getHall()+" :: "+booking.getRoom());
        //time.setText(dateFormat.format(booking.getStartTime())+"-"+dateFormat.format(booking.getEndTime()));
        bkid.setText(Integer.toString(booking.getBookingId()));
        // Return the completed view to render on screen
        return convertView;
    }
}
