package lk.nibm.lecturerportal.Model;

public class AtdAttendance
{
    private String batchName;
    private String stId;

    public AtdAttendance() {
    }

    public AtdAttendance(String batchName, String stId) {
        this.batchName = batchName;
        this.stId = stId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }
}
