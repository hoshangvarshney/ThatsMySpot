package edu.cmu.hvarshne.tms.model;

import java.util.Date;

/**
 * This POJO class represents the booking made by user.
 * Created by hvarshne on 4/5/2016.
 * Andrew ID: hvarshne
 * @author Hoshang Varshney
 */
public class Booking {
    private int bookingId;
    private String andrewId;
    private Date startTime;
    private Date endTime;
    private String hall;
    private String room;
    private String status;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    private String major;
    private String minor;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getAndrewId() {
        return andrewId;
    }

    public void setAndrewId(String andrewId) {
        this.andrewId = andrewId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;}

    public void setStatus(String status) {
        this.status = status;
    }
}
