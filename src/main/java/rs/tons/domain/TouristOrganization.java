package rs.tons.domain;

import java.util.Arrays;
import java.util.List;

public class TouristOrganization {

    private final List<Tourist> surveys;

    public TouristOrganization(Tourist... surveys) {
        this.surveys = Arrays.asList(surveys);
    }

    public TouristOrganization(List<Tourist> surveys) {
        this.surveys = surveys;
    }

    public List<Tourist> getSurveys() {
        return surveys;
    }
}
