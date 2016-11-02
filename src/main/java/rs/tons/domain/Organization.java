package rs.tons.domain;

import java.util.Arrays;
import java.util.List;

public class Organization {

    private final List<TouristSurvey> surveys;

    public Organization(TouristSurvey... surveys) {
        this.surveys = Arrays.asList(surveys);
    }

    public List<TouristSurvey> getSurveys() {
        return surveys;
    }
}
