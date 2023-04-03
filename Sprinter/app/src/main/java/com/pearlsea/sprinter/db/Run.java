package com.pearlsea.sprinter.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.pearlsea.sprinter.RunningFragment;

import java.util.Date;
import java.util.List;

@Entity
public class Run {
    @PrimaryKey
    @NonNull
    public String runId;

    @ColumnInfo(name="distance")
    @NonNull
    public double distance;

    @ColumnInfo(name="time")
    @NonNull
    public Date time;

    @ColumnInfo(name="date")
    @NonNull
    public Date date;

    @ColumnInfo(name="location_data")
    @NonNull
    public List<RunningFragment.RunPoint> location_data;

    public Run(String runId, double distance, Date time, Date date, List<RunningFragment.RunPoint> location_data) {
        this.runId = runId;
        this.distance = distance;
        this.time = time;
        this.date = date;
        this.location_data = location_data;
    }
}
