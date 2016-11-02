package rs.tons.domain;

import java.util.Arrays;
import java.util.List;

public class Tourist {

    private final String name;
    private final String countryOrigin;
    private final List<RouteLeg> destinations;

    public Tourist(String name, String countryOrigin, RouteLeg... destinations) {
        this.name = name;
        this.countryOrigin = countryOrigin;
        this.destinations = Arrays.asList(destinations);
    }

    public List<RouteLeg> getDestinations() {
        return destinations;
    }

    public String getName() {
        return name;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }
}
