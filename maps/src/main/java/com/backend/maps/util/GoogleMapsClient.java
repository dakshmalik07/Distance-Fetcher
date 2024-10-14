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

      
        System.out.println("API Response: " + response.getBody());

        
        JSONObject jsonResponse = new JSONObject(response.getBody());

      
        if (!jsonResponse.has("rows") || jsonResponse.getJSONArray("rows").length() == 0) {
            throw new JSONException("No routes found in the response.");
        }

        JSONArray rows = jsonResponse.getJSONArray("rows");
        JSONObject elements = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0);

        String distance = elements.getJSONObject("distance").getString("text");
        String duration = elements.getJSONObject("duration").getString("text");

       
        String routeDetails = elements.toString();

        return new RouteInfo(null, fromPincode, toPincode, distance, duration, routeDetails);
    }

}
