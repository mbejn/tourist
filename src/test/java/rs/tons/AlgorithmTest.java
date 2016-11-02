package rs.tons;

import org.junit.Assert;
import org.junit.Test;
import rs.tons.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AlgorithmTest {

    private static final TouristSpot A = new TouristSpot("A");
    private static final TouristSpot B = new TouristSpot("B");
    private static final TouristSpot C = new TouristSpot("C");
    private static final TouristSpot D = new TouristSpot("D");
    private static final TouristSpot E = new TouristSpot("E");
    private static final TouristSpot J = new TouristSpot("J");
    private static final TouristSpot K = new TouristSpot("K");
    private static final TouristSpot O = new TouristSpot("O");
    private static final TouristSpot X = new TouristSpot("X");
    private static final TouristSpot L = new TouristSpot("L");
    private static final TouristSpot Z = new TouristSpot("Z");
    private static final TouristSpot P = new TouristSpot("P");
    private static final TouristSpot Y = new TouristSpot("Y");


    private static final TouristSurvey TOURIST_INFO_A = // A->C->D->E->B->J->K->O
            new TouristSurvey(
                    RouteLeg.of(A, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, E),
                    RouteLeg.of(E, B),
                    RouteLeg.of(B, J),
                    RouteLeg.of(J, K),
                    RouteLeg.of(K, O)
            );

    private static final TouristSurvey TOURIST_INFO_B = // A->C->D->E->X->B->J->K->O->L
            new TouristSurvey(
                    RouteLeg.of(A, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, E),
                    RouteLeg.of(E, X),
                    RouteLeg.of(X, B),
                    RouteLeg.of(B, J),
                    RouteLeg.of(J, K),
                    RouteLeg.of(K, O),
                    RouteLeg.of(O, L)
            );

    private static final TouristSurvey TOURIST_INFO_C = // X->B->J->K->O->C->D->Y->E
            new TouristSurvey(
                    RouteLeg.of(X, B),
                    RouteLeg.of(B, J),
                    RouteLeg.of(J, K),
                    RouteLeg.of(K, O),
                    RouteLeg.of(O, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, Y),
                    RouteLeg.of(Y, E)
            );

    private static final TouristSurvey TOURIST_INFO_D = // Z->X->C->D->E->P
            new TouristSurvey(
                    RouteLeg.of(Z, X),
                    RouteLeg.of(X, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, E),
                    RouteLeg.of(E, P)
            );

    private static final Organization ORGANIZATION =
            new Organization(
                    TOURIST_INFO_D,
                    TOURIST_INFO_A,
                    TOURIST_INFO_C,
                    TOURIST_INFO_B
            );

    @Test
    public void testExample() {

        final Optional<Integer> optionalNumberOfRouteLegs = Optional.of(2);

        final List<Route> routes = AlgorithmUtils.findMostPopularRoutes(optionalNumberOfRouteLegs, Optional.empty(), ORGANIZATION);

        Assert.assertEquals(2, routes.size());

        final Route routeOne = routes.stream().filter(f -> f.getRouteLegs().get(0).getFrom() == B).findFirst().get();
        final Route routeTwo = routes.stream().filter(f -> f.getRouteLegs().get(0).getFrom() == C).findFirst().get();

        Assert.assertEquals(3, routeOne.getOccurrences().intValue());
        Assert.assertEquals(new ArrayList<>(Arrays.asList(
                RouteLeg.of(B, J),
                RouteLeg.of(J, K),
                RouteLeg.of(K, O))
        ), routeOne.getRouteLegs());

        Assert.assertEquals(3, routeTwo.getOccurrences().intValue());
        Assert.assertEquals(new ArrayList<>(Arrays.asList(
                RouteLeg.of(C, D),
                RouteLeg.of(D, E))
        ), routeTwo.getRouteLegs());
    }

    @Test
    public void testExampleWithGivenDestination() {

        final Optional<Integer> optionalNumberOfRouteLegs = Optional.of(2);
        final Optional<TouristSpot> optionalDestination = Optional.of(E);

        final List<Route> routes = AlgorithmUtils.findMostPopularRoutes(optionalNumberOfRouteLegs, optionalDestination, ORGANIZATION);

        Assert.assertEquals(1, routes.size());

        final Route routeOne = routes.get(0);

        Assert.assertEquals(3, routeOne.getOccurrences().intValue());
        Assert.assertEquals(new ArrayList<>(Arrays.asList(
                RouteLeg.of(C, D),
                RouteLeg.of(D, E))
        ), routeOne.getRouteLegs());
    }

    @Test
    public void testExampleWithGivenMinRouteLegs() {

        final Optional<Integer> optionalNumberOfRouteLegs = Optional.of(3);

        final List<Route> routes = AlgorithmUtils.findMostPopularRoutes(optionalNumberOfRouteLegs, Optional.empty(), ORGANIZATION);

        Assert.assertEquals(1, routes.size());

        final Route routeOne = routes.get(0);

        Assert.assertEquals(3, routeOne.getOccurrences().intValue());
        Assert.assertEquals(new ArrayList<>(Arrays.asList(
                RouteLeg.of(B, J),
                RouteLeg.of(J, K),
                RouteLeg.of(K, O))
        ), routeOne.getRouteLegs());
    }
}
