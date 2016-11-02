package rs.tons.domain;

import java.math.BigDecimal;

public class TouristDestination {

    private final String name;

    private final BigDecimal latitude;

    private final BigDecimal longitude;

    public TouristDestination(String name, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TouristDestination(String name) {
        this.name = name;
        this.latitude = BigDecimal.ZERO;
        this.longitude = BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TouristDestination other = (TouristDestination) obj;
        if ((name == null && other.name != null) || !name.equals(other.name))
            return false;
        if ((latitude == null && other.latitude != null) || !latitude.equals(other.latitude))
            return false;
        if ((longitude == null && other.longitude != null) || !longitude.equals(other.longitude))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
}
