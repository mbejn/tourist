package rs.tons.utils;

import rs.tons.domain.Route;
import rs.tons.domain.RouteLeg;
import rs.tons.domain.TouristOrganization;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AlgorithmUtils {

    private static final int DEFAULT_MIN_NUMBER_OF_ROUTE_LEGS = 2;

    public static List<Route> findMostPopularRoutes(Optional<Integer> optionalNumberOfRouteLegs, Optional<String> destination, TouristOrganization organization) {

        final List<List<RouteLeg>> collectedSubroutes = mapSurveysToAllSubroutes(optionalNumberOfRouteLegs, destination, organization);

        final List<Route> groupedSubroutes = groupAndCountSameRoutes(collectedSubroutes);

        return groupAndOrderByOccurrences(groupedSubroutes);
    }

    /**
     * Given the user route, it traverses and finds all possible subroutes.
     *      It will include the target destination if it exists.
     */
    private static List<List<RouteLeg>> mapSurveysToAllSubroutes(Optional<Integer> optionalNumberOfRouteLegs, Optional<String> targetDestination, TouristOrganization organization) {

        final int minRouteLegs = optionalNumberOfRouteLegs.orElse(DEFAULT_MIN_NUMBER_OF_ROUTE_LEGS);

        final Stream<List<RouteLeg>> subroutes = organization.getSurveys()
                .stream()
//                .parallel()
                .flatMap(survey -> {
                    final List<RouteLeg> touristRoute = survey.getDestinations();
                    final int routeLength = touristRoute.size();

                    final Stream<Integer> routeNumbers = IntStream.range(0, routeLength).boxed();

                    final Stream<List<RouteLeg>> allPossibleSubroutes = routeNumbers
//                            .parallel()
                            .flatMap(routeNumber -> produceAllSubroutes(minRouteLegs, routeNumber, touristRoute));

                    return allPossibleSubroutes;

                });

        if (targetDestination.isPresent()) {
            // filter out if destination is set
            return subroutes
                    .filter(route -> route.get(route.size() - 1).getTo().getName().equals(targetDestination.get()))
                    .collect(Collectors.toList());
        }

        return subroutes.collect(Collectors.toList());
    }


    private static Stream<List<RouteLeg>> produceAllSubroutes(Integer minRouteLegs, Integer currentRouteNumber, List<RouteLeg> passengerRoute) {

        final RouteLeg firstRouteLeg = passengerRoute.get(currentRouteNumber);

        final List<RouteLeg> newRoute = Collections.singletonList(firstRouteLeg);
        final List<RouteLeg> otherRoutes = passengerRoute.subList(currentRouteNumber + 1, passengerRoute.size());

        final List<List<RouteLeg>> accumulator = new ArrayList<>();

        if (minRouteLegs < 2) {
            accumulator.add(new ArrayList<>(newRoute));
        }

        return produceOtherRoutes(minRouteLegs, newRoute, otherRoutes, accumulator).stream();
    }

    private static List<List<RouteLeg>> produceOtherRoutes(Integer minRouteLegs, List<RouteLeg> currentPathList, List<RouteLeg> toBeTraversed, List<List<RouteLeg>> accumulator) {

        if (toBeTraversed.isEmpty()) {
            return accumulator;
        }

        final List<RouteLeg> newList = new ArrayList<>(currentPathList);

        // add new route leg
        newList.add(toBeTraversed.get(0));

        if (newList.size() >= minRouteLegs) {
            // add to accumulator as possible max visited subroute
            accumulator.add(newList);
        }
        return produceOtherRoutes(minRouteLegs, newList, toBeTraversed.subList(1, toBeTraversed.size()), accumulator);
    }

    /**
     * Groups the same routes by the list of route legs.
     *      Filters out the empty ones, and creates an explicit Route class as result foreach subroute.
     */
    private static List<Route> groupAndCountSameRoutes(List<List<RouteLeg>> collectedSubroutes) {
        return collectedSubroutes
                .stream()
                .collect(Collectors.groupingBy(route -> route))
                .values()
                .stream()
//                .parallel()
                .filter(g -> !g.isEmpty())
                .map(sameRoutes -> Route.create(sameRoutes.size(), sameRoutes.get(0)))
                .collect(Collectors.toList());
    }

    /**
     *  Groups by number of occurrences first,
     *      sorts by the occurrences afterwards,
     *          for same occurrences in the groups, will kick out the smaller subroutes.
     *  Takes the first group as result with most occurrences.
     * */
    private static List<Route> groupAndOrderByOccurrences(List<Route> routes) {
        return routes
                .stream()
                .collect(Collectors.groupingBy(Route::getOccurrences))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, List<Route>>comparingByKey().reversed())
//                .parallel()
                .map((entry) -> {
                    final List<Route> sameByOccurrences = entry.getValue();
                    final List<Route> sortedRoutes =
                            sameByOccurrences
                                    .stream()
                                    .sorted((a, b) -> b.getRouteLegs().size() - a.getRouteLegs().size())
                                    .collect(Collectors.toList());

                    return removeGroupedSubroutes(sortedRoutes, new ArrayList<>());
                })
                .collect(Collectors.toList())
                .stream()
                .findFirst()
                .orElse(Collections.emptyList());
    }

    private static List<Route> removeGroupedSubroutes(List<Route> sortedRoutes, List<Route> accumulator) {
        if (sortedRoutes.isEmpty()) {
            return accumulator;
        }

        final Route head = sortedRoutes.get(0);
        final List<RouteLeg> headRoute = head.getRouteLegs();

        final boolean isNotSublist = accumulator.stream().allMatch(t -> Collections.indexOfSubList(t.getRouteLegs(), headRoute) == -1);

        if (isNotSublist) {
            accumulator.add(head);
        }
        return removeGroupedSubroutes(sortedRoutes.subList(1, sortedRoutes.size()), accumulator);
    }
}
