package ab.byteshiftserverv1.model;

/**
 * Created by arlen on 8/30/17.
 */

public class Visit {
    private String day;
    private String enterTime;
    private String exitTime;
    private String userId;
    private String beaconId;

    public Visit(){}

    public String getDay() {
        return day;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public Visit(String day, String enterTime, String exitTime, String userId, String beaconId){
        this.day = day;
        this.enterTime = enterTime;
        this.exitTime = exitTime;
        this.userId = userId;
        this.beaconId = beaconId;
    }
}
