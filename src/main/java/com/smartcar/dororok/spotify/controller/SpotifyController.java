package com.smartcar.dororok.spotify.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class SpotifyController {

    private final RestTemplate restTemplate;

    private final String spotifyAccessToken;

    public SpotifyController(RestTemplate restTemplate, String spotifyAccessToken) {
        this.restTemplate = restTemplate;
        this.spotifyAccessToken = spotifyAccessToken;
    }

    @GetMapping("/test")
    public String test(){
        return "test 성공11";
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String query, @RequestParam String type) {
        String searchEndpoint = "https://api.spotify.com/v1/search";
        String url = searchEndpoint + "?type=" + type + "&q=" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyAccessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}

