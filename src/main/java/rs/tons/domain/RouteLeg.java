package rs.tons.domain;

public class RouteLeg {

    private final TouristSpot from;

    private final TouristSpot to;

    public RouteLeg(TouristSpot from, TouristSpot to) {
        this.from = from;
        this.to = to;
    }

    public static RouteLeg of(TouristSpot from, TouristSpot to) {
        return new RouteLeg(from, to);
    }

    @Override
    public String toString() {
        return from.toString() + to.toString();
    }

    public TouristSpot getTo() {
        return to;
    }

    public TouristSpot getFrom() {
        return from;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RouteLeg other = (RouteLeg) obj;
        if ((from == null && other.from != null) || !from.equals(other.from))
            return false;
        if ((to == null && other.to != null) || !to.equals(other.to))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

}
