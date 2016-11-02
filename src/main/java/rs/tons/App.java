package rs.tons;

import rs.tons.domain.Route;
import rs.tons.domain.TouristOrganization;
import rs.tons.utils.AlgorithmUtils;
import rs.tons.utils.JsonWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class App {

    public static final String TONS_HOME = System.getenv("TONS_HOME");

    private static final JsonWrapper JSON_WRAPPER = new JsonWrapper();

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("[ERROR] Please specify paths to input/output files as arguments.");
            return;
        }

        final String inputFilePath = args[0];
        final String outputFilePath = args[1];

        try {
            final Optional<Integer> optionalNumberOfRouteLegs =
                    args.length >= 3 ? Optional.of(Integer.parseInt(args[2])) : Optional.empty();

            final Optional<String> optionalDestination =
                    args.length >= 4 ? Optional.of(args[3]) : Optional.empty();

            final TouristOrganization organization = JSON_WRAPPER.loadData(inputFilePath);

            final List<Route> mostPopularRoutes = AlgorithmUtils.findMostPopularRoutes(optionalNumberOfRouteLegs, optionalDestination, organization);

            JSON_WRAPPER.writeData(outputFilePath, mostPopularRoutes);

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Specified min number of route legs must be an integer.");
        } catch (IOException e) {
            System.out.println("[ERROR] Specified file is not found: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("[ERROR]" + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Unknown error occurred." + e.getMessage());
        }
    }

}
