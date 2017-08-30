package ab.byteshiftserverv1.model;

/**
 * Created by arlen on 8/30/17.
 */

public class Beacon {
    private String beaconName;
    private String beaconId;
    private String beaconIdentityNumber;

    public Beacon(){}

    public String getBeaconName() {
        return beaconName;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public String getBeaconIdentityNumber() {
        return beaconIdentityNumber;
    }

    public Beacon(String beaconName, String beaconId, String beaconIdentityNumber){
        this.beaconId = beaconId;
        this.beaconIdentityNumber = beaconIdentityNumber;
        this.beaconName = beaconName;
    }
}