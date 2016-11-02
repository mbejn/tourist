package rs.tons.domain;

import java.util.Arrays;
import java.util.List;

public class Organization {

    private final List<Tourist> surveys;

    public Organization(Tourist... surveys) {
        this.surveys = Arrays.asList(surveys);
    }

    public List<Tourist> getSurveys() {
        return surveys;
    }
}
