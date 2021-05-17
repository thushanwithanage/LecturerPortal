package lk.nibm.lecturerportal.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class LecHallBooking
{
    private String lechallId;
    private String lechallName;
    private String bookDate;
    private String startTime;
    private String endTime;
    private String batchName;
    private int status;
    private String lecId;

    public LecHallBooking() {
    }

    public LecHallBooking(String lechallId, String lechallName, String bookDate, String startTime, String endTime, String batchName, int status, String lecId) {
        this.lechallId = lechallId;
        this.lechallName = lechallName;
        this.bookDate = bookDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.batchName = batchName;
        this.status = status;
        this.lecId = lecId;
    }

    public String getLechallName() {
        return lechallName;
    }

    public void setLechallName(String lechallName) {
        this.lechallName = lechallName;
    }

    public int getStatus() {
        return status;
    }

    public String getLecId() {
        return lecId;
    }

    public void setLecId(String lecId) {
        this.lecId = lecId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getLechallId() {
        return lechallId;
    }

    public void setLechallId(String lechallId) {
        this.lechallId = lechallId;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static Comparator<LecHallBooking> DateAscending = new Comparator<LecHallBooking>()
    {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(LecHallBooking n1, LecHallBooking n2)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
            LocalDate date1 = LocalDate.parse(n1.getBookDate(), formatter);
            LocalDate date2 = LocalDate.parse(n2.getBookDate(), formatter);
            return date1.compareTo( date2 );
        }
    };
}
