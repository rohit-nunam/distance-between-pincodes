package com.distance_project.controller;

import com.distance_project.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/route")
    public ResponseEntity<Map<String, Object>> getRoute(@RequestParam String from, @RequestParam String to) {
        log.info("Received request to get route from {} to {}", from, to);
        try {
            Map<String, Object> route = routeService.getRoute(from, to);
            log.info("Successfully retrieved route: {}", route);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            log.error("Error retrieving route from {} to {}", from, to, e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
