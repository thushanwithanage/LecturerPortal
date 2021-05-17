package lk.nibm.lecturerportal.Model;

import java.util.Comparator;

public class LectureHall
{
    private String hallId;
    private String hallName;
    private String floorNo;

    public LectureHall() {
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public static Comparator<LectureHall> FloorAescending = new Comparator<LectureHall>()
    {
        @Override
        public int compare(LectureHall n1, LectureHall n2) {
            return n1.getFloorNo().compareTo( n2.getFloorNo() );
        }
    };
}
