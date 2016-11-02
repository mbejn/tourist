package rs.tons.domain;

import java.util.Arrays;
import java.util.List;

public class TouristSurvey {

    private final List<RouteLeg> touristsPath;

    public TouristSurvey(RouteLeg... path) {
        this.touristsPath = Arrays.asList(path);
    }

    public List<RouteLeg> getTouristsPath() {
        return touristsPath;
    }
}
