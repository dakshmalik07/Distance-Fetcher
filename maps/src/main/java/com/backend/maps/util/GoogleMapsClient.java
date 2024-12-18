package com.backend.maps.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.backend.maps.model.RouteInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleMapsClient {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public RouteInfo fetchDistanceData(String fromPincode, String toPincode) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
                fromPincode, toPincode, apiKey
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Log the full response
        System.out.println("API Response: " + response.getBody());

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Check if "rows" is present and has data
        if (!jsonResponse.has("rows") || jsonResponse.getJSONArray("rows").length() == 0) {
            throw new JSONException("No routes found in the response.");
        }

        JSONArray rows = jsonResponse.getJSONArray("rows");
        JSONObject elements = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0);

        String distance = elements.getJSONObject("distance").getString("text");
        String duration = elements.getJSONObject("duration").getString("text");

        // Extract addresses from the API response
        JSONArray originAddresses = jsonResponse.getJSONArray("origin_addresses");
        JSONArray destinationAddresses = jsonResponse.getJSONArray("destination_addresses");

        String originAddress = originAddresses.length() > 0 ? originAddresses.getString(0) : "Unknown Origin";
        String destinationAddress = destinationAddresses.length() > 0 ? destinationAddresses.getString(0) : "Unknown Destination";

        // Storing the full route details as JSON string in routeDetails
        String routeDetails = elements.toString();

        return new RouteInfo(null, fromPincode, toPincode, distance, duration, routeDetails, originAddress, destinationAddress);
    }
}
