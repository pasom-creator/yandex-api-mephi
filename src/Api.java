import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

public class Api {
    private static final String HEADER_KEY_NAME = "X-Yandex-Weather-Key";
    private static final String ACCESS_KEY = "7ace308b-7e31-4d26-bc3c-605c930907f6";

    private double summaryTemp;
    private String firstDate;
    private String lastDate;


    public void getData(Double latitude, Double longitude, int limit) throws IOException, InterruptedException {
        String URL = String.format("https://api.weather.yandex.ru/v2/forecast?lat=%s&lon=%s&limit=%s", latitude, longitude, limit);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header(HEADER_KEY_NAME, ACCESS_KEY)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonArray forecastsArray = jsonObject.getAsJsonArray("forecasts");
        if (forecastsArray != null) {
            int size = forecastsArray.size();
            for (int i = 0; i < size; i++) {
                JsonElement forecastElement = forecastsArray.get(i);
                JsonObject forecastObject = forecastElement.getAsJsonObject();

                if (i == 0) {
                    firstDate = forecastObject.get("date").getAsString();
                }
                if (i == size -1) {
                    lastDate = forecastObject.get("date").getAsString();
                }

                double dayTemperature = forecastObject.getAsJsonObject("parts")
                        .getAsJsonObject("day")
                        .get("temp_avg")
                        .getAsDouble();
                summaryTemp += dayTemperature;
            }

            double averageTemperature = summaryTemp / size;

            System.out.println("Average temperature from " + firstDate + " to " + lastDate + " = " + averageTemperature + " celsius");
        } else {
            System.out.println("Forecasts array not found in JSON response.");
        }
    }

    public void getHttp(Double latitude, Double longitude, int limit) throws IOException, InterruptedException {
        String URL = String.format("https://api.weather.yandex.ru/v2/forecast?lat=%s&lon=%s&limit=%s", latitude, longitude, limit);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header(HEADER_KEY_NAME, ACCESS_KEY)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    }
