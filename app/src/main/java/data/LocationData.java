package data;

public class LocationData {

    private String name;
    private double latitude;
    private double longitude;
    private String iconDescription;

    public LocationData(String name, double latitude, double longitude, String iconDescription) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iconDescription = iconDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIconDescription() {
        return iconDescription;
    }

    public void setIconDescription(String iconDescription) {
        this.iconDescription = iconDescription;
    }

}
