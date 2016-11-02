package rs.tons;

import org.junit.Assert;
import org.junit.Test;
import rs.tons.domain.*;
import rs.tons.utils.AlgorithmUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AlgorithmTest {

    private static final TouristDestination A = new TouristDestination("A");
    private static final TouristDestination B = new TouristDestination("B");
    private static final TouristDestination C = new TouristDestination("C");
    private static final TouristDestination D = new TouristDestination("D");
    private static final TouristDestination E = new TouristDestination("E");
    private static final TouristDestination J = new TouristDestination("J");
    private static final TouristDestination K = new TouristDestination("K");
    private static final TouristDestination O = new TouristDestination("O");
    private static final TouristDestination X = new TouristDestination("X");
    private static final TouristDestination L = new TouristDestination("L");
    private static final TouristDestination Z = new TouristDestination("Z");
    private static final TouristDestination P = new TouristDestination("P");
    private static final TouristDestination Y = new TouristDestination("Y");


    private static final Tourist TOURIST_INFO_A = // A->C->D->E->B->J->K->O
            new Tourist("A", "RS",
                    RouteLeg.of(A, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, E),
                    RouteLeg.of(E, B),
                    RouteLeg.of(B, J),
                    RouteLeg.of(J, K),
                    RouteLeg.of(K, O)
            );

    private static final Tourist TOURIST_INFO_B = // A->C->D->E->X->B->J->K->O->L
            new Tourist("B", "RS",
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

    private static final Tourist TOURIST_INFO_C = // X->B->J->K->O->C->D->Y->E
            new Tourist("C", "RS",
                    RouteLeg.of(X, B),
                    RouteLeg.of(B, J),
                    RouteLeg.of(J, K),
                    RouteLeg.of(K, O),
                    RouteLeg.of(O, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, Y),
                    RouteLeg.of(Y, E)
            );

    private static final Tourist TOURIST_INFO_D = // Z->X->C->D->E->P
            new Tourist("D", "RS",
                    RouteLeg.of(Z, X),
                    RouteLeg.of(X, C),
                    RouteLeg.of(C, D),
                    RouteLeg.of(D, E),
                    RouteLeg.of(E, P)
            );

    private static final TouristOrganization ORGANIZATION =
            new TouristOrganization(
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
        final Optional<String> optionalDestination = Optional.of("E");

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
