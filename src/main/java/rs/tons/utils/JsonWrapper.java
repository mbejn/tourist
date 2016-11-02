package rs.tons.utils;

import rs.tons.domain.*;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonWrapper {

    public TouristOrganization loadData(String filePath) throws FileNotFoundException {

        final InputStream inputStream = new FileInputStream(new File(filePath));
        ;

        System.out.println("Input file location: " + filePath);

        try (JsonReader jsonReader = Json.createReader(inputStream)) {

            final JsonObject jsonObject = jsonReader.readObject();
            final JsonArray trips = jsonObject.getJsonArray("trips");

            final List<Tourist> surveys =
                    trips.getValuesAs(JsonObject.class)
                            .stream()
                            .map(this::loadTouristWithSurvey)
                            .collect(Collectors.toList());

            return new TouristOrganization(surveys);

        } catch (JsonException e) {
            throw new IllegalStateException("File has no proper encoding type or is not well-formatted.");
        }
    }

    private Tourist loadTouristWithSurvey(JsonObject jsonTrip) {
        final JsonObject jsonTourist = jsonTrip.getJsonObject("tourist");

        final String touristName = jsonTourist.getString("name");
        final String countryOrigin = jsonTourist.getString("country_of_origin");

        final JsonArray jsonDestinations = jsonTrip.getJsonArray("destinations");

        final List<TouristDestination> destinations =
                jsonDestinations.getValuesAs(JsonObject.class)
                        .stream()
                        .map(jsonDestination -> {
                            final String destinationName = jsonDestination.getString("name");
                            final BigDecimal latitude = jsonDestination.getJsonNumber("latitude").bigDecimalValue();
                            final BigDecimal longitude = jsonDestination.getJsonNumber("longitude").bigDecimalValue();
                            return new TouristDestination(destinationName, latitude, longitude);
                        }).collect(Collectors.toList());

        final List<RouteLeg> route =
                IntStream.range(0, destinations.size() - 1).boxed()
                        .map(i -> RouteLeg.of(destinations.get(i), destinations.get(i + 1)))
                        .collect(Collectors.toList());

        return new Tourist(touristName, countryOrigin, route);
    }

    public void writeData(String outputFilePath, List<Route> mostPopularRoutes) throws IOException {

        final JsonArrayBuilder mostPopularRoutesBuilder = Json.createArrayBuilder();

        mostPopularRoutes.forEach(route -> {

            final JsonObjectBuilder routeBuilder = Json.createObjectBuilder();

            routeBuilder.add("popularity", route.getOccurrences());

            final RouteLeg head = route.getRouteLegs().get(0);

            final List<TouristDestination> tail = route.getRouteLegs().subList(1, route.getRouteLegs().size())
                    .stream()
                    .map(RouteLeg::getTo).collect(Collectors.toList());

            final List<TouristDestination> destinationRoute = new ArrayList<>(Arrays.asList(head.getFrom(), head.getTo()));
            destinationRoute.addAll(tail);

            final JsonArrayBuilder destinationsBuilder = Json.createArrayBuilder();

            destinationRoute.stream().forEach(destination -> {
                final JsonObjectBuilder destinationObject = Json.createObjectBuilder();

                destinationObject.add("name", destination.getName());
                destinationObject.add("latitude", destination.getLatitude());
                destinationObject.add("longitude", destination.getLongitude());

                destinationsBuilder.add(destinationObject.build());
            });

            routeBuilder.add("destinations", destinationsBuilder.build());

            mostPopularRoutesBuilder.add(routeBuilder.build());
        });

        final JsonObject outputObject = Json.createObjectBuilder()
                .add("most_popular_routes", mostPopularRoutesBuilder.build())
                .build();

        System.out.println("Output file location: " + outputFilePath);

        final File newFile = new File(outputFilePath);
        final FileWriter fileWriter = new FileWriter(newFile);

        final Map<String, Object> options = new HashMap<>();
        options.put(JsonGenerator.PRETTY_PRINTING, true);

        final JsonWriter writer = Json.createWriterFactory(options).createWriter(fileWriter);

        writer.writeObject(outputObject);
        writer.close();
    }
}

