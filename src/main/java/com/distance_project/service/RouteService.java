package com.distance_project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RouteService {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    private final RestTemplate restTemplate;

    public RouteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getRoute(String from, String to) throws Exception {
        log.debug("Building URI for request from {} to {}", from, to);
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/distancematrix/json")
                .queryParam("origins", from)
                .queryParam("destinations", to)
                .queryParam("key", googleMapsApiKey)
                .build()
                .toUri();

        log.info("Sending request to Google Maps API: {}", uri);
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();
            log.debug("Received response from Google Maps API: {}", body);
            if ("OK".equals(body.get("status"))) {
                Map<String, Object> result = new HashMap<>();
                Map<String, Object> element = ((List<Map<String, Object>>) ((List<Map<String, Object>>) body.get("rows")).get(0).get("elements")).get(0);
                if ("OK".equals(element.get("status"))) {
                    result.put("from", from);
                    result.put("to", to);
                    result.put("distance", ((Map<String, Object>) element.get("distance")).get("text"));
                    result.put("duration", ((Map<String, Object>) element.get("duration")).get("text"));
                    log.info("Successfully parsed route information: {}", result);
                    return result;
                } else {
                    String errorMessage = "Error: " + element.get("status");
                    log.error(errorMessage);
                    throw new Exception(errorMessage);
                }
            } else {
                String errorMessage = "Error: " + body.get("status");
                log.error(errorMessage);
                throw new Exception(errorMessage);
            }
        } else {
            String errorMessage = "Error fetching data from Google Maps API";
            log.error(errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
